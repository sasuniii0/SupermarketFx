package lk.ijse.gdse71.supermarketfx.dao.custom;

import lk.ijse.gdse71.supermarketfx.dao.CrudDAO;
import lk.ijse.gdse71.supermarketfx.entity.Order;
import org.hibernate.Session;

import java.util.Optional;


public interface OrderDAO extends CrudDAO<Order> {
     Optional<Order> findById(String id);
     boolean saveOrderWithOrderDetails(Session session, Order order);

    }
