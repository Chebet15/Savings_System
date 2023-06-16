package co.ke.tech.Savings_System.TransactionComponent;

import co.ke.tech.Savings_System.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<?> newTransaction(@RequestBody Transaction transaction) {
        try {
            ApiResponse response = transactionService.enterTransaction(transaction);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = transactionService.getAllTransactions();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/id/{transactionId}")
    public ResponseEntity<?> fetchTransactionById(@PathVariable("transactionId") Long transactionId) {
        try{
            ApiResponse response = transactionService.getTransactionById(transactionId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/memberNumber/{memberNumber}")
    public ResponseEntity<?> fetchTransactionByCustomer(@PathVariable("memberNumber") String memberNumber) {
        try{
            ApiResponse response = transactionService.findSumByCustomer(memberNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/postedBy/{postedBy}")
    public ResponseEntity<?> fetchTransactionByPostedBy(@PathVariable("postedBy") String postedBy) {
        try{
            ApiResponse response = transactionService.findSumByPostedBy(postedBy);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
