package lk.ijse.gdse71.supermarketfx.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerTm {
    private String customerId;
    private String customerName;
    private  String nic;
    private String email;
    private String phone;
}
