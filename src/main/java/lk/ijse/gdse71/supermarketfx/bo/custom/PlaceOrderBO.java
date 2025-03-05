package lk.ijse.gdse71.supermarketfx.bo.custom;

import lk.ijse.gdse71.supermarketfx.bo.SuperBO;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
     String getNextOrderId() throws SQLException;
     boolean saveOrder(OrderDto orderDto) throws SQLException ;
     boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> orderDetailsDtos) throws SQLException ;
     boolean saveOrderDetail(OrderDetailsDto orderDetailsDto) throws SQLException ;
     String generateNextOrderId() throws SQLException ;
}
