package lk.ijse.gdse71.supermarketfx.bo.custom.impl;

import lk.ijse.gdse71.supermarketfx.bo.custom.PlaceOrderBO;
import lk.ijse.gdse71.supermarketfx.config.FactoryConfiguration;
import lk.ijse.gdse71.supermarketfx.dao.DAOFactory;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.dao.custom.CustomerDAO;
import lk.ijse.gdse71.supermarketfx.dao.custom.ItemDAO;
import lk.ijse.gdse71.supermarketfx.dao.custom.OrderDAO;
import lk.ijse.gdse71.supermarketfx.db.DBConnection;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDto;
import lk.ijse.gdse71.supermarketfx.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlacePlaceOrderBOImpl implements PlaceOrderBO {

    OrderDAO orderDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDER);
    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEM);
    CustomerDAO customerDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CUSTOMER);

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
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        System.out.println(orderDto);

        try{
            //save order,order details, update item
            String orderId = orderDto.getOrderId();
            System.out.println(orderId);

            // check order id exist
            Optional<Order> byId = orderDAO.findById(orderId);
            System.out.println(byId);
            if (byId.isPresent()){
                transaction.rollback();
                return false;
            }

            String customerId = orderDto.getCustomerId();
            Optional<Customer> bycustId = customerDAO.findById(customerId);
            if (bycustId.isEmpty()){
                transaction.rollback();
                return false;
            }

            // need to validate date
            Customer customer = bycustId.get();
            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderDate(orderDto.getOrderDate());
            order.setCustomer(customer);

            List<OrderDetails> orderDetails = new ArrayList<>();
            ArrayList<OrderDetailsDto> orderDetailsDtos = orderDto.getOrderDetailsDtos();
            for (OrderDetailsDto orderDetailsDto : orderDetailsDtos) {
                String itemId = orderDetailsDto.getItemId();
                Optional<Item> optionalItem = itemDAO.findById(itemId);

                //if not found item
                if (optionalItem.isEmpty()){
                    transaction.rollback();
                    return false;
                }
                Item item = optionalItem.get();

                OrderDetailId orderDetailId = new OrderDetailId(orderId, itemId);

                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setId(orderDetailId);
                orderDetail.setOrder(order);
                orderDetail.setItem(item);
                orderDetail.setQtyOnHand(orderDetailsDto.getQtyOnHand());
                orderDetail.setPrice(BigDecimal.valueOf(orderDetailsDto.getPrice()));

                orderDetails.add(orderDetail);
            }
            order.setOrderDetailsList(orderDetails);
            //save order --> order details save wenw...
            boolean isOrderSaved = orderDAO.saveOrderWithOrderDetails(session, order);
            if (!isOrderSaved){
                transaction.rollback();
                return false;
            }
            for (OrderDetails orderDetail : orderDetails) {
                String itemId = orderDetail.getItem().getItemId();
                Optional<Item> optionalItem = itemDAO.findById(itemId);

                if (optionalItem.isEmpty()){
                    transaction.rollback();
                    return false;
                }
                Item item = optionalItem.get();
                if (item.getQuantity() < orderDetail.getQtyOnHand()){
                    //5<10
                    transaction.rollback();
                    return false;
                }
                item.setQuantity(item.getQuantity()-orderDetail.getQtyOnHand());

                boolean isItemUpdated = itemDAO.updateItemWithOrder(session, item);
                if (!isItemUpdated){
                    transaction.rollback();
                    return false;
                }
            }
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }
        finally {
            if (session!=null){
                session.close();
            }
        }
    }

   /* public boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> orderDetailsDtos) throws SQLException {
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
    }*/

    /*@Override
    public boolean saveOrderDetail(OrderDetailsDto orderDetailsDto) throws SQLException {
        *//*return orderDetailDAO.save(new OrderDetails(
                orderDetailsDto.getOrderId(),
                orderDetailsDto.getItemId(),
                orderDetailsDto.getQtyOnHand(),
                orderDetailsDto.getPrice()
        ));*//*
        return false;
    }*/
}
