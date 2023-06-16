package co.ke.tech.Savings_System.CustomerComponent;

import co.ke.tech.Savings_System.Response.ApiResponse;
import co.ke.tech.Savings_System.Utils.CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@Slf4j
public class CustomerService {
    private CustomerRepository customerRepository;
    private AtomicInteger counter;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.counter = new AtomicInteger(getLastMemberNumber());
    }


    private int getLastMemberNumber() {
        Customer lastCustomer = customerRepository.findFirstByOrderByMemberNumberDesc();
        if (lastCustomer != null) {
            String lastMemberNumber = lastCustomer.getMemberNumber();
            if (lastMemberNumber != null && lastMemberNumber.length() >= 8) {
                return Integer.parseInt(lastMemberNumber.substring(8));
            }
        }
        return 0;
    }


    public ApiResponse<Customer> addCustomer(Customer customer) {
        try {
            ApiResponse response = new ApiResponse<>();
            if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
                response.setMessage("Phone Number already registered!");
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setEntity("");
                return response;
            } else if (customerRepository.existsByEmail(customer.getEmail())) {
                response.setMessage("Email is already in use");
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setEntity("");
                return response;
            } else if (customerRepository.existsByIdNumber(customer.getIdNumber())) {
                response.setMessage("The Id number is already registered !");
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setEntity("");
                return response;
            } else {
                int nextMemberNumber = counter.incrementAndGet();
                String memberNumber = "memberNo" + String.format("%03d", nextMemberNumber);
                customer.setMemberNumber(memberNumber);
                customer.setEmail(customer.getEmail());
                customer.setIdNumber(customer.getIdNumber());
                customer.setFirstName(customer.getFirstName());
                customer.setLastName(customer.getFirstName());
                customer.setPhoneNumber(customer.getPhoneNumber());
                Customer savedCustomer = customerRepository.save(customer);
                response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                response.setMessage("CUSTOMER NAME " + customer.getFirstName() + " CREATED SUCCESSFULLY AT ");
                response.setStatusCode(HttpStatus.CREATED.value());
                response.setEntity(savedCustomer);
            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<?> getAllCustomers() {
        try {
            ApiResponse response = new ApiResponse();
            List<Customer> customerList = customerRepository.findAll();
            if (customerList.size() > 0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(customerList);
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

    public ApiResponse<?> getCustomerById(Long id) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<Customer> customer = customerRepository.findById(id);
            if (customer.isPresent()) {
                Customer customer1 = customer.get();
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(customer1);
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

    public ApiResponse<Customer> updateCustomer(Customer customer) {
        ApiResponse response = new ApiResponse();
        try {
            Optional<Customer> customer1 = customerRepository.findById(customer.getId());
            if (customer1.isPresent()) {
                Customer existingCustomer = customer1.get();
                existingCustomer.setFirstName(customer.getFirstName());
                existingCustomer.setLastName(customer.getLastName());
                existingCustomer.setEmail(customer.getEmail());
                existingCustomer.setPhoneNumber(customer.getPhoneNumber());
                existingCustomer.setIdNumber(customer.getIdNumber());
                Customer savedCustomer = customerRepository.save(existingCustomer);
                response.setMessage("Customer with Id " + customer.getId() + " updated successfully");
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(savedCustomer);
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
public ApiResponse<Customer> tempDeleteCustomer(Long id) {
    try {
        ApiResponse response = new ApiResponse();
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer customer1 = customer.get();
            Customer deletedCustomer = customerRepository.save(customer1);
            response.setMessage("Customer deleted successfully");
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
