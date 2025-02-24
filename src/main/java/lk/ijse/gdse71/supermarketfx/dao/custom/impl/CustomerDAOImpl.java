package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.dao.custom.CustomerDAO;
import lk.ijse.gdse71.supermarketfx.dto.CustomerDto;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;
import lk.ijse.gdse71.supermarketfx.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Customer");
        ArrayList<Customer> entity = new ArrayList<>();

        while (rst.next()) {
            Customer customerDto = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            entity.add(customerDto);
        }
        return entity;
    }

    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute("insert into Customer values(?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getCustomerName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getPhone()
        );
    }
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select customer_id from Customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("C%03d", newIndex);
        }
        return "C001";
    }
    public ArrayList<String> getAllCustomerIds() throws SQLException{
        ResultSet rst = SQLUtil.execute("select customer_id from Customer");
        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }
    public CustomerDto findById(String selectedCustId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Customer where customer_id=?", selectedCustId);

        if (rst.next()) {
            return new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5));
        }
        return null;
    }

    public boolean delete(String customerId) throws SQLException {
        return SQLUtil.execute("delete from Customer where customer_id = ?",customerId);
    }

    public boolean update(Customer entity) throws SQLException {
        return  SQLUtil.execute("update Customer set name=?, nic =?, email=?, phone=? where customer_id =?",
                entity.getCustomerName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getCustomerId()
        );
    }
}
