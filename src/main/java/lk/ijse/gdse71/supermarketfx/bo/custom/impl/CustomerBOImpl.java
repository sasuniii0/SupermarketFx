package lk.ijse.gdse71.supermarketfx.bo.custom.impl;

import lk.ijse.gdse71.supermarketfx.bo.custom.CustomerBO;
import lk.ijse.gdse71.supermarketfx.dao.DAOFactory;
import lk.ijse.gdse71.supermarketfx.dao.custom.CustomerDAO;
import lk.ijse.gdse71.supermarketfx.dto.CustomerDto;
import lk.ijse.gdse71.supermarketfx.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException {
        ArrayList<Customer> customerDtos = (ArrayList<Customer>) customerDAO.getAll();
        ArrayList<CustomerDto> customerDtos1 = new ArrayList<>();

        for (Customer customer : customerDtos) {
            CustomerDto customerDto = new CustomerDto(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getNic(),
                    customer.getEmail(),
                    customer.getPhone()
            );
            customerDtos1.add(customerDto);
        }
        return customerDtos1;
    }

    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {
        return customerDAO.save(new Customer(customerDto.getCustomerId(), customerDto.getCustomerName(), customerDto.getNic(), customerDto.getEmail(), customerDto.getPhone()));
    }

    @Override
    public String getNextCustomerId() throws SQLException {
        return customerDAO.getNextId();
    }

    @Override
    public List<Customer> getAllCustomerIds() throws SQLException {
        return customerDAO.getAllCustomerIds();
    }

    @Override
    public Optional<Customer> findById(String selectedCustId) throws SQLException {
        return customerDAO.findById(selectedCustId);
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        return customerDAO.update(new Customer(customerDto.getCustomerId(), customerDto.getCustomerName(), customerDto.getNic(), customerDto.getEmail(), customerDto.getPhone()));
    }
}
