package co.ke.tech.Savings_System.TransactionComponent;

import co.ke.tech.Savings_System.ProductsComponent.Product;
import co.ke.tech.Savings_System.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public ApiResponse<Transaction> enterTransaction(Transaction transaction) {
        try {
            ApiResponse response = new ApiResponse();
            transaction.setPaymentMethod(transaction.getPaymentMethod());
            transaction.setTranDate(new Date());
            transaction.setProductType(transaction.getProductType());
//            transaction.setPattranList(transaction.getPattranList());
            Transaction savedTransaction = transactionRepository.save(transaction);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setMessage("TRANSACTION SUCCESSFULLY");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(savedTransaction);

            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
}
