package lk.ijse.gdse71.supermarketfx.bo.custom.impl;

import lk.ijse.gdse71.supermarketfx.bo.custom.ItemBO;
import lk.ijse.gdse71.supermarketfx.dao.DAOFactory;
import lk.ijse.gdse71.supermarketfx.dao.custom.ItemDAO;
import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<String> getAllItemIds() throws SQLException {
        return itemDAO.getAllItemIds();
    }

    @Override
    public ItemDto findById(String selecteItemId) throws SQLException {
        return itemDAO.findById(selecteItemId);
    }

    @Override
    public boolean reduceQty(OrderDetailsDto orderDetailsDto) throws SQLException {
        return itemDAO.reduceQty(orderDetailsDto);
    }

    @Override
    public ArrayList<ItemDto> getAllItems() throws SQLException {
        ArrayList<Item> items = (ArrayList<Item>) itemDAO.getAll();
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            itemDtos.add(new ItemDto(
                    item.getItemId(),
                    item.getItemName(),
                    item.getQuantity(),
                    item.getUnitPrice()
            ));
        }
        return itemDtos;
    }

    @Override
    public String getNextItemId() throws SQLException {
        return itemDAO.getNextId();
    }

    @Override
    public boolean saveItem(ItemDto itemDto) throws SQLException {
        return itemDAO.save(new Item(
                itemDto.getItemId(),
                itemDto.getItemName(),
                itemDto.getQuantity(),
                itemDto.getUnitPrice()
        ));
    }

    @Override
    public boolean deleteItem(String itemId) throws SQLException {
        return itemDAO.delete(itemId);
    }

    @Override
    public boolean updateItem(ItemDto itemDto) throws SQLException {
        return itemDAO.update(new Item(
                itemDto.getItemId(),
                itemDto.getItemName(),
                itemDto.getQuantity(),
                itemDto.getUnitPrice()
        ));
    }
}
