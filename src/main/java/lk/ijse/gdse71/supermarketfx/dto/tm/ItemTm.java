package lk.ijse.gdse71.supermarketfx.dto.tm;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemTm {
    private String itemId;
    private String itemName;
    private int quantity;
    private double unitPrice;
}
