package lk.ijse.gdse71.supermarketfx.dao.custom;

import lk.ijse.gdse71.supermarketfx.dao.CrudDAO;
import lk.ijse.gdse71.supermarketfx.dto.CustomerDto;
import lk.ijse.gdse71.supermarketfx.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {
     /*ArrayList<Customer> getAll() throws SQLException;
     boolean save(Customer entity) throws SQLException;
     String getNextId() throws SQLException ;*/
     ArrayList<String> getAllCustomerIds() throws SQLException;
     CustomerDto findById(String selectedCustId) throws SQLException ;
     /*boolean delete(String customerId) throws SQLException ;
     boolean update(Customer entity) throws SQLException ;*/
}
