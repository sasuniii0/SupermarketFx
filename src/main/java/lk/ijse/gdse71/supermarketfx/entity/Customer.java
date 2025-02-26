package lk.ijse.gdse71.supermarketfx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Customer {
    @Id
    private String customerId;
    private String customerName;
    private String nic;
    private String email;
    private String phone;
}
