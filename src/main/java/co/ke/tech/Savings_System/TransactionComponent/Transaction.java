package co.ke.tech.Savings_System.TransactionComponent;

import co.ke.tech.Savings_System.TransactionComponent.Parttrans.Parttran;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private String tranID;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date tranDate;
    private String paymentMethod;
    private String productType;
    //    one transaction to many parttrans
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<Parttran> pattranList;
}
