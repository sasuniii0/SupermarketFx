package lk.ijse.gdse71.supermarketfx.bo.custom.impl;

import lk.ijse.gdse71.supermarketfx.bo.custom.CustomerBO;
import lk.ijse.gdse71.supermarketfx.bo.exception.DuplicateException;
import lk.ijse.gdse71.supermarketfx.bo.exception.NotFoundException;
import lk.ijse.gdse71.supermarketfx.dao.DAOFactory;
import lk.ijse.gdse71.supermarketfx.dao.custom.CustomerDAO;
import lk.ijse.gdse71.supermarketfx.dto.CustomerDto;
import lk.ijse.gdse71.supermarketfx.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException {
        ArrayList<Customer> customerDtos = (ArrayList<Customer>) customerDAO.getAll();
        ArrayList<CustomerDto> customerDtos1 = new ArrayList<>();

        for (Customer customer : customerDtos) {
            CustomerDto customerDto = new CustomerDto(
                    customer.getCustomerId(),
                    customer.getName(),
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
        try{
            if (customerDto.getCustomerId() == null || customerDto.getCustomerId().isEmpty()) {
                customerDto.setCustomerId(generateNextCustomerId());
            }
            Customer customer = new Customer(
                    customerDto.getCustomerId(),
                    customerDto.getCustomerName(),
                    customerDto.getNic(),
                    customerDto.getEmail(),
                    customerDto.getPhone()
            );
            customerDAO.save(customer);
            return true;
        }catch (DuplicateException e){
            e.getMessage();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getNextCustomerId() throws SQLException {
        return generateNextCustomerId();
    }

    @Override
    public List<String> getAllCustomerIds() throws SQLException {
        return customerDAO.getAllCustomerIds();
    }

    @Override
    public Optional<Customer> findById(String selectedCustId) throws SQLException {
        return customerDAO.findById(selectedCustId);
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        try{
            customerDAO.delete(customerId);
            return true;
        }catch (NotFoundException e){
            e.getMessage();
        }catch (Exception e){
            e.getMessage();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        try{
            if (customerDto.getCustomerId() == null || customerDto.getCustomerId().isEmpty()) {
                customerDto.setCustomerId(generateNextCustomerId());
            }
            Customer customer = new Customer(
                    customerDto.getCustomerId(),
                    customerDto.getCustomerName(),
                    customerDto.getNic(),
                    customerDto.getEmail(),
                    customerDto.getPhone()
            );
            customerDAO.update(customer);
            return true;
        }catch (DuplicateException e){
            e.getMessage();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    return false;
    }
    public String generateNextCustomerId() throws SQLException {
        String lastId = customerDAO.getLastId(); // Retrieve the last ID from DB

        if (lastId == null || lastId.isEmpty()) {
            return "C001"; // Default ID if no customers exist
        }

        try {
            // Assuming IDs are in the format "C001", "C002", etc.
            String prefix = lastId.substring(0, 1); // Extract "C"
            String numberPart = lastId.substring(1); // Extract "001"

            int nextNumber = Integer.parseInt(numberPart) + 1; // Increment number
            return String.format("%s%03d", prefix, nextNumber); // Format as "C002"
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return "C001"; // Fallback if parsing fails
        }
    }



}
