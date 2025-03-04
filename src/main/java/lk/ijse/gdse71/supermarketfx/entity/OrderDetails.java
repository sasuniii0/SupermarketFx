package lk.ijse.gdse71.supermarketfx.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "OrderDetails")
public class OrderDetails {
    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "itemId")
    private Item item;
    private int qtyOnHand;

    @Column(scale = 2,name = "unit_price",precision = 10)
    private double price;
}
