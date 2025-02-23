package lk.ijse.gdse71.supermarketfx.model;

import lk.ijse.gdse71.supermarketfx.db.DBConnection;
import lk.ijse.gdse71.supermarketfx.dto.OrderDto;
import lk.ijse.gdse71.supermarketfx.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {

    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from Orders order by order_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("0%03d", newIndex);
        }
        return "O001";
    }

    public boolean saveOrder(OrderDto orderDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);
            boolean isOrderSaved = CrudUtil.execute("insert into Orders values (?,?,?)",
                    orderDto.getOrderId(),
                    orderDto.getCustomerId(),
                    orderDto.getOrderDate()
            );
            if(isOrderSaved){
                boolean isOrderDetailsSaved = orderDetailsModel.saveOrderDetailsList(orderDto.getOrderDetailsDtos());
                if(isOrderDetailsSaved){
                    connection.commit();
                    return true;
                }

            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
