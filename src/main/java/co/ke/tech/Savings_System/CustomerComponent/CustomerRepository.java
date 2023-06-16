package co.ke.tech.Savings_System.CustomerComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByIdNumber(String idNumber);
    Optional<Customer> findByMemberNumber(String memberNumber);

    Customer findFirstByOrderByMemberNumberDesc();
}
