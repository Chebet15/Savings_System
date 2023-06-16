package co.ke.tech.Savings_System.TransactionComponent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String transactionCode;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date transactionDate;
    private Double amount;//22.00
    private String transactionType;//debit or credit
    private String paymentMethod;//Mpesa or account number
    private String description;
    private String productCode;
    private String memberNumber;
    private String postedBy;//eg teller
}
