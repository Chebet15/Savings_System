package co.ke.tech.Savings_System.ProductsComponent;

import co.ke.tech.Savings_System.CustomerComponent.Customer;
import co.ke.tech.Savings_System.CustomerComponent.CustomerRepository;
import co.ke.tech.Savings_System.Response.ApiResponse;
import co.ke.tech.Savings_System.Utils.CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;

    private String generateProductCode() {
        // Retrieve the last product from the database
        Product lastProduct = productRepository.findTopByOrderByProductCodeDesc();

        // Extract the product code and increment it
        String lastProductCode = lastProduct != null ? lastProduct.getProductCode() : "PRCODE000";
        int productNumber = Integer.parseInt(lastProductCode.substring(6)) + 1;
        String productCode = "PRCODE" + String.format("%03d", productNumber);

        return productCode;
    }

    public ApiResponse<Product> addProduct(Product product) {
        try {
            ApiResponse response = new ApiResponse();
                if (productRepository.existsByProductName(product.getProductName())) {
                    response.setMessage("Two Products can not share same names");
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                } else {
                    product.setProductName(product.getProductName());
                    product.setProductCode(generateProductCode());
                    Product savedProduct = productRepository.save(product);
                    response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                    response.setMessage("PRODUCT NAME " + product.getProductName() + " CREATED SUCCESSFULLY AT ");
                    response.setStatusCode(HttpStatus.CREATED.value());
                    response.setEntity(savedProduct);
                }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<?> getAllProducts() {
        try {
            ApiResponse response = new ApiResponse();
            List<Product> productList = productRepository.findAll();
            if (productList.size() > 0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(productList);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<?> getProductById(Long id) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                Product product1 = product.get();
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(product1);
                return response;
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                return response;
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<Product> updateProduct(Product product) {
        ApiResponse response = new ApiResponse();
        try {
            Optional<Product> product1 = productRepository.findById(product.getId());
            if (product1.isPresent()) {
                Product existingProduct = product1.get();
                existingProduct.setProductName(product.getProductName());
                Product savedProduct = productRepository.save(existingProduct);
                response.setMessage("Product with Id " + product.getId() + " updated successfully");
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(savedProduct);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<Product> tempDeleteProduct(Long id) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                Product product1 = product.get();
                Product deletedProduct = productRepository.save(product1);
                response.setMessage("Product deleted successfully");
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity("");
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        } catch (Exception e) {
            log.info("Caught Error: {}", e);
            return null;
        }
    }


}
