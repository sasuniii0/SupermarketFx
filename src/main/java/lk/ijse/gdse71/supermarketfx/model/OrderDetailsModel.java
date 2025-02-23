package lk.ijse.gdse71.supermarketfx.model;

import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {

     private final ItemModel itemModel = new ItemModel();

     public boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> orderDetailsDtos) throws SQLException {
         for (OrderDetailsDto orderDetailsDto : orderDetailsDtos) {
             boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDto);

             if (!isOrderDetailsSaved) {
                 return false;
             }
             boolean isItemUpdated =itemModel.reduceQty(orderDetailsDto);
             if (!isItemUpdated) {
                 return false;
             }
         }
         return true;
     }

    private boolean saveOrderDetail(OrderDetailsDto orderDetailsDto) throws SQLException {
         return CrudUtil.execute(
                 "insert into OrderDetails values (?,?,?,?)",
                 orderDetailsDto.getOrderId(),
                 orderDetailsDto.getItemId(),
                 orderDetailsDto.getQtyOnHand(),
                 orderDetailsDto.getPrice()
         );
    }
}
