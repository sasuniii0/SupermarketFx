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

    /*public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT MAX(order_id) FROM Orders");

        if (rst.next()) {
            String lastId = rst.getString(1);
            if (lastId != null && lastId.startsWith("O")) {
                int newIndex = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("O%03d", newIndex);
            }
        }
        return "O001";  // First order
    }*/

    public String getLastId() throws SQLException {
        /*ResultSet rst = SQLUtil.execute("SELECT MAX(order_id) FROM Orders");
        return rst.next() ? rst.getString(1) : null;*/

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

    /*public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select order_id from Orders order by order_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("0%03d", newIndex);
        }
        return "O001";
    }*/

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Order dto) throws SQLException {
        return false;
    }
}
