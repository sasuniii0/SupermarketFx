package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailDAO {

     private final ItemDAOImpl itemDAOImpl = new ItemDAOImpl();

     public boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> orderDetailsDtos) throws SQLException {
         for (OrderDetailsDto orderDetailsDto : orderDetailsDtos) {
             boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDto);

             if (!isOrderDetailsSaved) {
                 return false;
             }
             boolean isItemUpdated = itemDAOImpl.reduceQty(orderDetailsDto);
             if (!isItemUpdated) {
                 return false;
             }
         }
         return true;
     }

    private boolean saveOrderDetail(OrderDetailsDto orderDetailsDto) throws SQLException {
         return SQLUtil.execute(
                 "insert into OrderDetails values (?,?,?,?)",
                 orderDetailsDto.getOrderId(),
                 orderDetailsDto.getItemId(),
                 orderDetailsDto.getQtyOnHand(),
                 orderDetailsDto.getPrice()
         );
    }
}
