package co.ke.tech.Savings_System.TransactionComponent;

import co.ke.tech.Savings_System.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
