package lk.ijse.gdse71.supermarketfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.gdse71.supermarketfx.config.FactoryConfiguration;
import lk.ijse.gdse71.supermarketfx.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse71.supermarketfx.entity.Customer;
import org.hibernate.Session;

import java.sql.SQLException;
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

        CustomerDAOImpl customerDAO= new CustomerDAOImpl();
        Optional<Customer> customer = customerDAO.findById("C001");

        /*if (!customer.isEmpty()) {
            Customer customer1 = customer.get();
        }*/
        // have data

        if (customer.isPresent()) {
            Customer customer1 = customer.get();
        }
        launch(args);
    }
}
