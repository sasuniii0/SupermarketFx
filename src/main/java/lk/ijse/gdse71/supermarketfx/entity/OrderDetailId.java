package lk.ijse.gdse71.supermarketfx.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderDetailId {
    private String order_id;
    private String item_id;
}
