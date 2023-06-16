package co.ke.tech.Savings_System.TransactionComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT SUM(amount) AS total_amount FROM transaction WHERE member_number = :memberNumber", nativeQuery = true)
    double getTotalAmountByMemberNumber(@Param("memberNumber") String memberNumber);
    @Query(value = "SELECT SUM(amount) AS total_amount FROM transaction WHERE posted_by = :postedBy", nativeQuery = true)
    double getTotalAmountByPostedBy(@Param("postedBy") String postedBy);

}
