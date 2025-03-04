package lk.ijse.gdse71.supermarketfx.bo.custom;

import lk.ijse.gdse71.supermarketfx.bo.SuperBO;
import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ItemBO extends SuperBO {
     List<Item> getAllItemIds() throws SQLException ;
     Optional<Item> findById(String selecteItemId) throws SQLException;
     boolean reduceQty(OrderDetailsDto orderDetailsDto) throws SQLException ;
     ArrayList<ItemDto> getAllItems() throws SQLException ;
     String getNextItemId() throws SQLException ;
     boolean saveItem(ItemDto itemDto) throws SQLException ;
     boolean deleteItem(String itemId) throws SQLException ;
     boolean updateItem(ItemDto itemDto) throws SQLException;
     String generateNextItemId() throws SQLException ;

}
