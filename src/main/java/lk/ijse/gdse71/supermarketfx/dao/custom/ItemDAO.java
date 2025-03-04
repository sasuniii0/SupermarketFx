package lk.ijse.gdse71.supermarketfx.dao.custom;

import lk.ijse.gdse71.supermarketfx.dao.CrudDAO;
import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ItemDAO extends CrudDAO<Item> {
     List<Item> getAllItemIds() throws SQLException;
     Optional<Item> findById(String selecteItemId) throws SQLException;
     boolean reduceQty(OrderDetailsDto orderDetailsDto) throws SQLException ;
}
