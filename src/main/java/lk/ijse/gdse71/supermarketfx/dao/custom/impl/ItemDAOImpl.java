package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.dao.custom.ItemDAO;
import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    public ArrayList<String> getAllItemIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select item_id from Item");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()){
            itemIds.add(rst.getString(1));
        }
        return itemIds;
    }

    public ItemDto findById(String selecteItemId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Item where item_id = ?",selecteItemId);

        if (rst.next()){
            return new ItemDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
        }
        return null;
    }

    public boolean reduceQty(OrderDetailsDto orderDetailsDto) throws SQLException {
        return SQLUtil.execute(
                "update Item set quantity = quantity - ? where item_id = ?",
                orderDetailsDto.getQtyOnHand(),
                orderDetailsDto.getItemId()
        );
    }

    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Item");

        ArrayList<Item> entity = new ArrayList<>();
        while (rst.next()){
            Item itemDto = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
            entity.add(itemDto);
        }
        return entity;
    }


    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select item_id from Item order by item_id desc limit 1");
        if (rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("I%03d", newIndex);
        }
        return "I001";
    }

    public boolean save(Item entity) throws SQLException {
        return SQLUtil.execute("insert into Item values (?,?,?,?)",
                entity.getItemId(),
                entity.getItemName(),
                entity.getQuantity(),
                entity.getUnitPrice()

        );
    }

    public boolean delete(String itemId) throws SQLException {
        return SQLUtil.execute("delete from Item where item_id = ?",itemId);
    }

    public boolean update(Item entity) throws SQLException {
        return  SQLUtil.execute("update Item set name =?, quantity = ?, price = ? where item_id = ?",
                entity.getItemName(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getItemId()
                );

    }
}
