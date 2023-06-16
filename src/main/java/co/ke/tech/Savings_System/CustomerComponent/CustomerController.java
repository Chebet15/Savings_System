package co.ke.tech.Savings_System.CustomerComponent;

import co.ke.tech.Savings_System.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @PostMapping("/add")
    public ResponseEntity<?> newCustomer(@RequestBody Customer customer) {
        try{
            ApiResponse response = customerService.addCustomer(customer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = customerService.getAllCustomers();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/id/{customerId}")
    public ResponseEntity<?> fetchCustomerById(@PathVariable("customerId") Long customerId) {
        try{
            ApiResponse response = customerService.getCustomerById(customerId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        try{
            ApiResponse response =customerService.updateCustomer(customer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PutMapping("/delete/temp/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") Long customerId) {
        try{
            ApiResponse response = customerService.tempDeleteCustomer(customerId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
