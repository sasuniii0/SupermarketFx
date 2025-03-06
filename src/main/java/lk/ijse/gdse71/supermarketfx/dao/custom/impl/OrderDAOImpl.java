package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.config.FactoryConfiguration;
import lk.ijse.gdse71.supermarketfx.dao.custom.OrderDAO;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Order dto) throws SQLException {
        return false;
    }

    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try{
            Query<String> query = session.createQuery("SELECT MAX(o.orderId) FROM Order o", String.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        }catch(Exception e){
            throw new SQLException("Error retrieving last order ID", e);
        }finally{
            if(session != null){
                session.close();
            }
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Order dto) throws SQLException {
        return false;
    }
    public Optional<Order> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.err.println("Invalid ID: " + id); // Debugging statement
            return Optional.empty(); // Return empty if ID is invalid
        }

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Order order = session.get(Order.class, id);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            e.printStackTrace(); // Print the exact error for debugging
            return Optional.empty();
        }
    }



    @Override
    public boolean saveOrderWithOrderDetails(Session session, Order order) {
        try{
            //data if exist update, no data save
            session.merge(order);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
