package lk.ijse.gdse71.supermarketfx.dao.custom;

import lk.ijse.gdse71.supermarketfx.dao.CrudDAO;
import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
     ArrayList<String> getAllItemIds() throws SQLException;
     ItemDto findById(String selecteItemId) throws SQLException;
     boolean reduceQty(OrderDetailsDto orderDetailsDto) throws SQLException ;
}
