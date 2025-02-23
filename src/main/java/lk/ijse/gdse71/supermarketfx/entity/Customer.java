package lk.ijse.gdse71.supermarketfx.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private String customerId;
    private String customerName;
    private String nic;
    private String email;
    private String phone;
}
