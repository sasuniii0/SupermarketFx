package lk.ijse.gdse71.supermarketfx.bo.custom.impl;

import lk.ijse.gdse71.supermarketfx.bo.custom.PlaceOrderBO;
import lk.ijse.gdse71.supermarketfx.dao.DAOFactory;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.dao.custom.ItemDAO;
import lk.ijse.gdse71.supermarketfx.dao.custom.OrderDAO;
import lk.ijse.gdse71.supermarketfx.db.DBConnection;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlacePlaceOrderBOImpl implements PlaceOrderBO {

    OrderDAO orderDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDER);
    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEM);

    @Override
    public String getNextOrderId() throws SQLException {
        return generateNextOrderId();
    }
    public String generateNextOrderId() throws SQLException {
        String lastId = orderDAO.getLastId(); // Retrieve the last ID from DB

        if (lastId == null || lastId.isEmpty()) {
            return "O001"; // Default ID when no previous records exist
        }

        try {
            // Assuming IDs are in the format "O001", "O002", etc.
            String prefix = lastId.substring(0, 1); // Extract "O"
            String numberPart = lastId.substring(1); // Extract "001"

            int nextNumber = Integer.parseInt(numberPart) + 1; // Increment number
            return String.format("%s%03d", prefix, nextNumber); // Format as "O002"
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return "O001"; // Fallback if parsing fails
        }
    }



    public boolean saveOrder(OrderDto orderDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);

            ResultSet rs = SQLUtil.execute("SELECT order_id FROM Orders WHERE order_id = ?", orderDto.getOrderId());
            if (rs.next()) {
                throw new SQLException("Duplicate Order ID: " + orderDto.getOrderId());
            }

            boolean isOrderSaved = SQLUtil.execute("insert into Orders values (?,?,?)",
                    orderDto.getOrderId(),
                    orderDto.getCustomerId(),
                    orderDto.getOrderDate()
            );
            if(isOrderSaved){
                boolean isOrderDetailsSaved = saveOrderDetailsList(orderDto.getOrderDetailsDtos());
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

    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> orderDetailsDtos) throws SQLException {
        for (OrderDetailsDto orderDetailsDto : orderDetailsDtos) {
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDto);

            if (!isOrderDetailsSaved) {
                return false;
            }
            boolean isItemUpdated = itemDAO.reduceQty(orderDetailsDto);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveOrderDetail(OrderDetailsDto orderDetailsDto) throws SQLException {
        /*return orderDetailDAO.save(new OrderDetails(
                orderDetailsDto.getOrderId(),
                orderDetailsDto.getItemId(),
                orderDetailsDto.getQtyOnHand(),
                orderDetailsDto.getPrice()
        ));*/
        return false;
    }
}
