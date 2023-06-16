package co.ke.tech.Savings_System.CustomerComponent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "firstname", length = 50, nullable = false)
    private String firstName;
    @Column(name = "lastname", length = 50, nullable = false)
    private String lastName;
    @Column(name = "nationalIdCard", length = 50, unique = true, nullable = true)
    private String idNumber;
    @Column(name = "phone", length = 50, nullable = false, unique = true)
    private String phoneNumber;
    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;
    @Column(length = 100, unique = true)
    private String memberNumber;
    private String accountNo;
}
