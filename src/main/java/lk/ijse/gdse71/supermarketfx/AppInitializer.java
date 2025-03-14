package lk.ijse.gdse71.supermarketfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.gdse71.supermarketfx.bo.BOFactory;
import lk.ijse.gdse71.supermarketfx.bo.custom.PlaceOrderBO;
import lk.ijse.gdse71.supermarketfx.config.FactoryConfiguration;
import lk.ijse.gdse71.supermarketfx.dao.DAOFactory;
import lk.ijse.gdse71.supermarketfx.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.supermarketfx.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse71.supermarketfx.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDto;
import lk.ijse.gdse71.supermarketfx.entity.Customer;
import lk.ijse.gdse71.supermarketfx.entity.Item;
import org.hibernate.Session;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent load =FXMLLoader.load(getClass().getResource("/view/Loading.fxml"));
        stage.setScene( new Scene(load));
        stage.show();

        Task<Scene> loadingTask = new Task<Scene>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/MainLayout.fxml"));
                return new Scene(fxmlLoader. load());
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            stage.setTitle("Supermarket Fx");
            stage.setScene(value);


        });

        new Thread(loadingTask).start();

        /*FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/MainLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Supermarket FX");

        Image image = new Image(getClass().getResourceAsStream("/images/app_icon.png"));
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.show();*/
    }

    public static void main(String[] args) throws SQLException {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        session.close();
        // hadala iwra krla run krl blnna one...

        /*CustomerDAOImpl customerDAO= DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CUSTOMER);
        Optional<Customer> customer = customerDAO.findById("C001");

        ItemDAOImpl itemDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEM);
        Optional<Item> item = itemDAO.findById("I001");

        *//*if (!customer.isEmpty()) {
            Customer customer1 = customer.get();
        }*//*
        // have data

        if (customer.isPresent()) {
            Customer customer1 = customer.get();
        }
        if (item.isPresent()) {
            Item item1 = item.get();
        }*/

        //place order

        /*PlaceOrderBO placeOrderBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

        String orderId = "O001";
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        orderDto.setCustomerId("C001");
        orderDto.setOrderDate(Date.valueOf(LocalDate.now()));

        OrderDetailsDto orderDetailDAO = new OrderDetailsDto();
        orderDetailDAO.setOrderId(orderId);
        orderDetailDAO.setItemId("I003");
        orderDetailDAO.setPrice(10);

        ArrayList<OrderDetailsDto> orderDetailsDtos = new ArrayList<>();
        orderDetailsDtos.add(orderDetailDAO);

        orderDto.setOrderDetailsDtos(orderDetailsDtos);
        boolean b = placeOrderBO.saveOrder(orderDto);
        System.out.println(b);

*/
        launch(args);
    }
}
