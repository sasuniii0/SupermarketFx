package lk.ijse.gdse71.supermarketfx.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Item")
public class Item {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(length = 100)
    private String name;
    private int quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /*@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsList;*/
}
