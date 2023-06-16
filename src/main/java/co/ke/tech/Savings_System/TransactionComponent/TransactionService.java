package co.ke.tech.Savings_System.TransactionComponent;

import co.ke.tech.Savings_System.CustomerComponent.Customer;
import co.ke.tech.Savings_System.CustomerComponent.CustomerRepository;
import co.ke.tech.Savings_System.ProductsComponent.Product;
import co.ke.tech.Savings_System.ProductsComponent.ProductRepository;
import co.ke.tech.Savings_System.Response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    public String initializeTransactionCode() {
        String timestamp = new SimpleDateFormat("yymmssSSS").format(new Date()); // 9 character timestamp

        char[] characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random random = new SecureRandom();
        char[] result = new char[5];

        for (int i = 0; i < result.length; i++) {
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result).concat(timestamp); // 14 character length
    }
    public ApiResponse<Transaction> enterTransaction(Transaction transaction) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<Customer> checkCustomer=customerRepository.findByMemberNumber(transaction.getMemberNumber());
            if (checkCustomer.isPresent()) {
                Optional<Product> checkProduct = productRepository.findByProductCode(transaction.getProductCode());
                if (checkProduct.isPresent()) {
                    transaction.setTransactionDate(new Date());
                    transaction.setTransactionCode(initializeTransactionCode());

                    Transaction savedTransaction = transactionRepository.save(transaction);
                    response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                    response.setMessage("TRANSACTION SUCCESSFULLY");
                    response.setStatusCode(HttpStatus.CREATED.value());
                    response.setEntity(savedTransaction);
                } else {
                    response.setMessage("Product code not present");
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
            }else {
                    response.setMessage("Product code not present");
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    public ApiResponse<?> getAllTransactions() {
        try {
            ApiResponse response = new ApiResponse();
            List<Transaction> transactionList = transactionRepository.findAll();
            if (transactionList.size() > 0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(transactionList);
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

    public ApiResponse<?> getTransactionById(Long id) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<Transaction> transaction = transactionRepository.findById(id);
            if (transaction.isPresent()) {
                Transaction transaction1 = transaction.get();
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(transaction1);
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

    public ApiResponse<?> findSumByCustomer(String memberNumber) {
        try {
            ApiResponse response = new ApiResponse();
            Double transaction=transactionRepository.getTotalAmountByMemberNumber(memberNumber);
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(transaction);
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    public ApiResponse<?> findSumByPostedBy(String postedBy) {
        try {
            ApiResponse response = new ApiResponse();
            Double transaction=transactionRepository.getTotalAmountByPostedBy(postedBy);
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(transaction);
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

}
