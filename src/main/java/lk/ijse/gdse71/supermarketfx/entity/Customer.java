package lk.ijse.gdse71.supermarketfx.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private String customerId;
    private String name;
    private String nic;
    private String email;
    private String phone;

    /*@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;*/

}
