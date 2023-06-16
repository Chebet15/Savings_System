package co.ke.tech.Savings_System.ProductsComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByProductName(String productName);
    Product findTopByOrderByProductCodeDesc();
    Optional<Product> findByProductCode(String productCode);
}
