package lk.ijse.gdse71.supermarketfx.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto{
    private String customerId;
    private String customerName;
    private String nic;
    private String email;
    private String phone;
}
