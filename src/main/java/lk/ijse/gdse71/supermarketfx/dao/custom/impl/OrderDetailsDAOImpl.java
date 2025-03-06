package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDAOImpl implements OrderDetailDAO {

    @Override
    public List<OrderDetails> getAll() throws SQLException {
        return null;
    }

    public boolean save(OrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException {
        return false;
    }
}
