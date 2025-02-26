package lk.ijse.gdse71.supermarketfx.config;

import lk.ijse.gdse71.supermarketfx.entity.Customer;
import lk.ijse.gdse71.supermarketfx.entity.Item;
import lk.ijse.gdse71.supermarketfx.entity.Order;
import lk.ijse.gdse71.supermarketfx.entity.OrderDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration(){
        Configuration configuration = new Configuration().configure();

        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(OrderDetails.class);

        sessionFactory = configuration.buildSessionFactory();
    }
    public static FactoryConfiguration getInstance(){
        return (factoryConfiguration==null) ? factoryConfiguration = new FactoryConfiguration() :factoryConfiguration;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }
}
