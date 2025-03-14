package lk.ijse.gdse71.supermarketfx.bo.custom.impl;

import lk.ijse.gdse71.supermarketfx.bo.custom.ItemBO;
import lk.ijse.gdse71.supermarketfx.bo.exception.DuplicateException;
import lk.ijse.gdse71.supermarketfx.bo.exception.NotFoundException;
import lk.ijse.gdse71.supermarketfx.dao.DAOFactory;
import lk.ijse.gdse71.supermarketfx.dao.custom.ItemDAO;
import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.entity.Item;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEM);
    @Override
    public List<String> getAllItemIds() throws SQLException {
        return itemDAO.getAllItemIds();
    }

    @Override
    public Optional<Item> findById(String selecteItemId) throws SQLException {
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
                    item.getName(),
                    item.getQuantity(),
                    item.getUnitPrice().doubleValue()
            ));
        }
        return itemDtos;
    }

    @Override
    public String getNextItemId() throws SQLException {
        return generateNextItemId();
    }

    @Override
    public boolean saveItem(ItemDto itemDto) throws SQLException {
        try{
            if (itemDto.getItemId() == null || itemDto.getItemId().isEmpty()) {
                itemDto.setItemId(generateNextItemId());
            }
            Item item = new Item(
                    itemDto.getItemId(),
                    itemDto.getItemName(),
                    itemDto.getQuantity(),
                    BigDecimal.valueOf(itemDto.getUnitPrice())
            );
            itemDAO.save(item);
            return true;
        } catch (DuplicateException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteItem(String itemId) throws SQLException {
        try{
            itemDAO.delete(itemId);
            return true;
        } catch (NotFoundException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public boolean updateItem(ItemDto itemDto) throws SQLException {
        try{
            if (itemDto.getItemId() == null || itemDto.getItemId().isEmpty()) {
                itemDto.setItemId(generateNextItemId());
            }
            Item item = new Item(
                    itemDto.getItemId(),
                    itemDto.getItemName(),
                    itemDto.getQuantity(),
                    BigDecimal.valueOf(itemDto.getUnitPrice())
            );
            itemDAO.update(item);
            return true;
        } catch (DuplicateException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }
    public String generateNextItemId() throws SQLException {
        String lastId = itemDAO.getLastId(); // Retrieve the last ID from DB

        if (lastId == null || lastId.isEmpty()) {
            return "I001"; // Default ID when no previous records exist
        }

        try {
            // Assuming IDs are in the format "I001", "I002", etc.
            String prefix = lastId.substring(0, 1); // Extract "I"
            String numberPart = lastId.substring(1); // Extract "001"

            int nextNumber = Integer.parseInt(numberPart) + 1; // Increment number
            return String.format("%s%03d", prefix, nextNumber); // Format as "I002"
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return "I001"; // Fallback if parsing fails
        }
    }


}
