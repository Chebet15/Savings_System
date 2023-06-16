package co.ke.tech.Savings_System.ProductsComponent;

import co.ke.tech.Savings_System.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<?> newProduct(@RequestBody Product product) {
        try{
            ApiResponse response = productService.addProduct(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = productService.getAllProducts();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/id/{productId}")
    public ResponseEntity<?> fetchProductById(@PathVariable("productId") Long productId) {
        try{
            ApiResponse response = productService.getProductById(productId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        try{
            ApiResponse response =productService.updateProduct(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PutMapping("/delete/temp/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        try{
            ApiResponse response = productService.tempDeleteProduct(productId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
