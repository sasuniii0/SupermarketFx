package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.dto.CustomerDto;
import lk.ijse.gdse71.supermarketfx.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl {
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Customer");
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();

        while (rst.next()) {
            CustomerDto customerDto = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {
        return SQLUtil.execute("insert into Customer values(?,?,?,?,?)",
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getNic(),
                customerDto.getEmail(),
                customerDto.getPhone()
        );
    }
    public String getNextCustomerId() throws SQLException {
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

    public boolean deleteCustomer(String customerId) throws SQLException {
        return SQLUtil.execute("delete from Customer where customer_id = ?",customerId);
    }

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        return  SQLUtil.execute("update Customer set name=?, nic =?, email=?, phone=? where customer_id =?",
                customerDto.getCustomerName(),
                customerDto.getNic(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                customerDto.getCustomerId()
        );
    }
}
