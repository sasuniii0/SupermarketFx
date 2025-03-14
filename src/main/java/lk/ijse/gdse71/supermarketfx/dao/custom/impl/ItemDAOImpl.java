package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.bo.exception.DuplicateException;
import lk.ijse.gdse71.supermarketfx.config.FactoryConfiguration;
import lk.ijse.gdse71.supermarketfx.dao.custom.ItemDAO;
import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.entity.Customer;
import lk.ijse.gdse71.supermarketfx.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    public List<String> getAllItemIds() throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<String> fromItem = session.createQuery("select i.id from Item i", String.class);
        List<String> items = fromItem.list();
        return items;
    }

    public Optional<Item> findById(String selecteItemId) throws SQLException {
        if (selecteItemId == null || selecteItemId.trim().isEmpty()) {
            System.err.println("Invalid Customer ID: " + selecteItemId); // Debugging
            return Optional.empty(); // Return empty to avoid error
        }

        try (Session session = factoryConfiguration.getSession()) {
            Item item = session.get(Item.class, selecteItemId);
            return Optional.ofNullable(item);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean reduceQty(OrderDetailsDto orderDetailsDto) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query query = session.createQuery("update Item set quantity = quantity - :qty where itemId = :itemId");
            query.setParameter("qty", orderDetailsDto.getQtyOnHand());
            query.setParameter("itemId", orderDetailsDto.getItemId());

            int updatedRows = query.executeUpdate();
            transaction.commit();
            return updatedRows > 0;
        } catch (Exception e) {
            transaction.rollback();
            throw new SQLException("Error updating item quantity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        /*return SQLUtil.execute(
                "update Item set quantity = quantity - ? where item_id = ?",
                orderDetailsDto.getQtyOnHand(),
                orderDetailsDto.getItemId()
        );*/
    }

    @Override
    public boolean updateItemWithOrder(Session session, Item item) {
        try{
            session.merge(item);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Item> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<Item> query = session.createQuery("from Item ", Item.class);
        List<Item> list = query.list();
        return list;

        /*ResultSet rst = SQLUtil.execute("select * from Item");

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
        return entity;*/
    }


    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT i.itemId FROM Item i ORDER BY i.itemId DESC", String.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new SQLException("Error retrieving last item ID", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /*public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select item_id from Item order by item_id desc limit 1");
        if (rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("I%03d", newIndex);
        }
        return "I001";
    }*/

    public boolean save(Item entity) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (entity == null || entity.getItemId() == null) {
                throw new IllegalArgumentException("Invalid Item entity or ID is null");
            }

            Item item = session.get(Item.class, entity.getItemId());
            if (item != null){
                //Already exists duplicates
                throw new DuplicateException("ItemId is Duplicated");
            }
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null){
                session.close();
            }
        }
        /*return SQLUtil.execute("insert into Item values (?,?,?,?)",
                entity.getItemId(),
                entity.getItemName(),
                entity.getQuantity(),
                entity.getUnitPrice()

        );*/
    }

    public boolean delete(String itemId) throws SQLException {
        // apply orm to layered project
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Item item = session.get(Item.class, itemId);
            if (item == null){
                return false;
            }
            session.remove(item);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null){
                session.close();
            }
        }
/*
        return SQLUtil.execute("delete from Item where item_id = ?",itemId);
*/
    }

    public boolean update(Item entity) throws SQLException {
        // apply orm to layered project
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null){
                session.close();
            }
        }
        /*return  SQLUtil.execute("update Item set name =?, quantity = ?, price = ? where item_id = ?",
                entity.getItemName(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getItemId()
                );*/

    }
}
