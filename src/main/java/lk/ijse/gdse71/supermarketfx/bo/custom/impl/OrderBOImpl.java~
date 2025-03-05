package lk.ijse.gdse71.supermarketfx.bo.custom.impl;

import lk.ijse.gdse71.supermarketfx.bo.custom.OrderBO;
import lk.ijse.gdse71.supermarketfx.dao.DAOFactory;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.dao.custom.ItemDAO;
import lk.ijse.gdse71.supermarketfx.dao.custom.OrderDAO;
import lk.ijse.gdse71.supermarketfx.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.supermarketfx.db.DBConnection;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDto;
import lk.ijse.gdse71.supermarketfx.entity.OrderDetails;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDER_DETAIL);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEM);

    @Override
    public String getNextOrderId() throws SQLException {
        return generateNextOrderId();
    }
    public String generateNextOrderId() throws SQLException {
        String lastId = orderDAO.getLastId(); // Call DAO method
        if (lastId != null) {
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;
            return String.format("O%03d", newIndex);
        }
        return "O001";
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
