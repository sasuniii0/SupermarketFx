package lk.ijse.gdse71.supermarketfx.dao.custom.impl;

import lk.ijse.gdse71.supermarketfx.bo.exception.DuplicateException;
import lk.ijse.gdse71.supermarketfx.bo.exception.NotFoundException;
import lk.ijse.gdse71.supermarketfx.config.FactoryConfiguration;
import lk.ijse.gdse71.supermarketfx.dao.custom.CustomerDAO;
import lk.ijse.gdse71.supermarketfx.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {

    // hama dao ekktama dagnnnwa...
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Customer> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<Customer> query = session.createQuery("from Customer", Customer.class);
        List<Customer> list = query.list();
        return list;
    }

    public boolean save(Customer entity) throws SQLException {
        // apply orm to layered project
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (entity == null || entity.getCustomerId() == null) {
                throw new IllegalArgumentException("Invalid customer entity or ID is null");
            }

            Customer customer = session.get(Customer.class, entity.getCustomerId());
            if (customer != null){
                //Already exists duplicates
                throw new DuplicateException("CustomerId is Duplicated");
            }
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            if (session != null){
                session.close();
            }
        }

        /*return SQLUtil.execute("insert into Customer values(?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getCustomerName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getPhone()
        );*/
    }
    public String getLastId() throws SQLException {
        try (Session session = factoryConfiguration.getSession()) { // Auto-close session
            Query<String> query = session.createQuery("SELECT c.customerId FROM Customer c ORDER BY c.customerId DESC", String.class);
            query.setMaxResults(1);
            return query.uniqueResult(); // May return null if no customers exist
        } catch (Exception e) {
            System.err.println("Error retrieving last customer ID: " + e.getMessage());
            throw new SQLException("Error retrieving last customer ID", e);
        }
    }


    /*public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select customer_id from Customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("C%03d", newIndex);
        }
        return "C001";
    }*/

    // arraylist return krddi tight coupling hadenwa... list use krnn one eka nisa
    public List<String> getAllCustomerIds() throws SQLException{
        // apply orm to layered project
        Session session = factoryConfiguration.getSession();
        Query<String> fromCustomer = session.createQuery("select c.id from Customer c", String.class);
        List<String> customers = fromCustomer.list();
        return customers;

        // adin passe array list mehma hadnna one
       // List<String> stirngs = new ArrayList<>();

        /*ResultSet rst = SQLUtil.execute("select customer_id from Customer");
        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;*/
    }
    public Optional<Customer> findById(String selectedCustId) throws SQLException {
        if (selectedCustId == null || selectedCustId.trim().isEmpty()) {
            System.err.println("Invalid Customer ID: " + selectedCustId); // Debugging
            return Optional.empty(); // Return empty to avoid error
        }

        try (Session session = factoryConfiguration.getSession()) {
            Customer customer = session.get(Customer.class, selectedCustId);
            return Optional.ofNullable(customer);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public boolean delete(String customerId) throws SQLException {
        // apply orm to layered project
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Customer customer = session.get(Customer.class, customerId);
            if (customer == null){
                //Not found
                throw new NotFoundException("No customer found to delete");
            }
            // customer have order
            // In use
            session.remove(customer);
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
        //return SQLUtil.execute("delete from Customer where customer_id = ?",customerId);
    }

    public boolean update(Customer entity) throws SQLException {
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
        /*return  SQLUtil.execute("update Customer set name=?, nic =?, email=?, phone=? where customer_id =?",
                entity.getCustomerName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getCustomerId()
        );*/
    }
}
