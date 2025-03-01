package lk.ijse.gdse71.supermarketfx.bo.custom;

import lk.ijse.gdse71.supermarketfx.bo.SuperBO;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
     ArrayList<String> getAllItemIds() throws SQLException ;
     ItemDto findById(String selecteItemId) throws SQLException;
     boolean reduceQty(OrderDetailsDto orderDetailsDto) throws SQLException ;
     ArrayList<ItemDto> getAllItems() throws SQLException ;
     String getNextItemId() throws SQLException ;
     boolean saveItem(ItemDto itemDto) throws SQLException ;
     boolean deleteItem(String itemId) throws SQLException ;
     boolean updateItem(ItemDto itemDto) throws SQLException;
}
