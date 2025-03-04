package lk.ijse.gdse71.supermarketfx.dao.custom;

import lk.ijse.gdse71.supermarketfx.dao.CrudDAO;
import lk.ijse.gdse71.supermarketfx.entity.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CustomerDAO extends CrudDAO<Customer> {
     List<Customer> getAllCustomerIds() throws SQLException;
     Optional<Customer> findById(String selectedCustId) throws SQLException ;

     }
