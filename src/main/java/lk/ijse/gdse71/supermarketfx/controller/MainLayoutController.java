package lk.ijse.gdse71.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML
    private AnchorPane AncPaneMain;

    @FXML
    private AnchorPane AncPaneSide;

    @FXML
    private Button BtnCustomer;

    @FXML
    private Button BtnItem;

    @FXML
    private Button BtnOrders;

    @FXML
    private Label LblSuperMarket;

    @FXML
    private Pane PaneSide;

    @FXML
    void CustomerClickOnAction(ActionEvent event) {
        navigateTo("/view/Customer.fxml");
    }

    @FXML
    void ItemClickOnAction(ActionEvent event) {
        navigateTo("/view/Item.fxml");
    }

    @FXML
    void OrdersClickOnACtion(ActionEvent event) {
        navigateTo("/view/Order.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/Customer.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try{
            AncPaneSide.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            load.prefWidthProperty().bind(AncPaneSide.widthProperty());
            load.prefHeightProperty().bind(AncPaneSide.heightProperty());
            AncPaneSide.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load page").show();
        }
    }

}