package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailDAO {

     private final ItemDAOImpl itemDAOImpl = new ItemDAOImpl();

     /*public boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> orderDetailsDtos) throws SQLException {
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
     }*/

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        return null;
    }

    public boolean save(OrderDetails entity) throws SQLException {
         return SQLUtil.execute(
                 "insert into OrderDetails values (?,?,?,?)",
                 entity.getOrderId(),
                 entity.getItemId(),
                 entity.getQtyOnHand(),
                 entity.getPrice()
         );
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException {
        return false;
    }
}
