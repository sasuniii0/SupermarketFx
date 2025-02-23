package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl {
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

    public ArrayList<ItemDto> getAllItems() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Item");

        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        while (rst.next()){
            ItemDto itemDto = new ItemDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }


    public String getNextItemId() throws SQLException {
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

    public boolean saveItem(ItemDto itemDto) throws SQLException {
        return SQLUtil.execute("insert into Item values (?,?,?,?)",
                itemDto.getItemId(),
                itemDto.getItemName(),
                itemDto.getQuantity(),
                itemDto.getUnitPrice()

        );
    }

    public boolean deleteItem(String itemId) throws SQLException {
        return SQLUtil.execute("delete from Item where item_id = ?",itemId);
    }

    public boolean updateItem(ItemDto itemDto) throws SQLException {
        return  SQLUtil.execute("update Item set name =?, quantity = ?, price = ? where item_id = ?",
                    itemDto.getItemName(),
                    itemDto.getQuantity(),
                    itemDto.getUnitPrice(),
                    itemDto.getItemId()
                );

    }
}
