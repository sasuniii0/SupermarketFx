package lk.ijse.gdse71.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.supermarketfx.bo.BOFactory;
import lk.ijse.gdse71.supermarketfx.bo.custom.CustomerBO;
import lk.ijse.gdse71.supermarketfx.bo.custom.ItemBO;
import lk.ijse.gdse71.supermarketfx.bo.custom.PlaceOrderBO;
import lk.ijse.gdse71.supermarketfx.dto.OrderDetailsDto;
import lk.ijse.gdse71.supermarketfx.dto.OrderDto;
import lk.ijse.gdse71.supermarketfx.entity.Customer;
import lk.ijse.gdse71.supermarketfx.entity.Item;
import lk.ijse.gdse71.supermarketfx.view.tdm.CartTm;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    private Button BtnAddToCart;

    @FXML
    private Button BtnGenerateRep;

    @FXML
    private Button BtnPlaceOrder;

    @FXML
    private Button BtnReset;

    @FXML
    private ComboBox<String> CmbCustId;

    @FXML
    private ComboBox<String> CmbItemId;

    @FXML
    private TableColumn<?, ?> ColAction;

    @FXML
    private TableColumn<CartTm, String> ColItemId;

    @FXML
    private TableColumn<CartTm, String> ColItemName;

    @FXML
    private TableColumn<CartTm, Integer> ColQty;

    @FXML
    private TableColumn<CartTm, Double> ColTotal;

    @FXML
    private TableColumn<CartTm, Double> ColUnitPrice;

    @FXML
    private Label LblCustName;

    @FXML
    private Label LblItemName;

    @FXML
    private Label LblOrder;

    @FXML
    private Label LblOrderDate;

    @FXML
    private Label LblQty;

    @FXML
    private Label LblUnitPrice;

    @FXML
    private TextField TxtQuantity;

    @FXML
    private TableView<CartTm> TblOrder;

    CustomerBO customerBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    ItemBO itemBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    PlaceOrderBO placeOrderBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    private final ObservableList<CartTm> cartTms = FXCollections.observableArrayList();

    @FXML
    void AddToCartOnClickAction(ActionEvent event) {
        String selectedItemId = CmbItemId.getValue();

        if (selectedItemId == null){
            new Alert(Alert.AlertType.ERROR, "Please Select Item", ButtonType.OK).show();
            return;
        }
        String cartQtyString = TxtQuantity.getText();
        String qtyPattern = "^[0-9]+$";
        String pricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
//        right :- 500.00. 500.65, 500,
//        wrong :- 787.8, 6777.9999

        if (!cartQtyString.matches(qtyPattern)){
            new Alert(Alert.AlertType.ERROR, "Please enter valid Quantity...", ButtonType.OK).show();
            return;
        }
        String itemName = LblItemName.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(LblQty.getText());

        if (qtyOnHand  < cartQty){
            new Alert(Alert.AlertType.ERROR, "Please enter valid Quantity...", ButtonType.OK).show();
            return;
        }
        TxtQuantity.setText("");

        double unitPrice = Double.parseDouble(LblUnitPrice.getText());
        double total = unitPrice * cartQty;

        for (CartTm cartTm : cartTms){
            if (cartTm.getItemId().equals(selectedItemId)){
             int newQty = cartTm.getCartQuantity() + cartQty;
             cartTm.setCartQuantity(newQty);
             cartTm.setTotal(unitPrice * newQty);

             TblOrder.refresh();
             return;
            }
        }

        Button btn = new Button("Remove");

        CartTm newCartTm = new CartTm(
                selectedItemId,
                itemName,
                cartQty,
                unitPrice,
                total,
                btn
        );
        btn.setOnAction(actionEvent -> {
            cartTms.remove(newCartTm);
            TblOrder.refresh();
        });
        cartTms.add(newCartTm);
    }

    @FXML
    void GenerateRepOnClickAction(ActionEvent event) {

    }

    @FXML
    void PlaceOrderOnClickAction(ActionEvent event) throws SQLException {
        if (TblOrder.getItems().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Add items to cart...", ButtonType.OK).show();
            return;
        }
        if (CmbCustId.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Select Customer for place order...", ButtonType.OK).show();
            return;
        }
        String orderId = LblOrder.getText();
        Date dateofOrder = Date.valueOf(LblOrderDate.getText());
        String customerId = CmbCustId.getValue();

        ArrayList<OrderDetailsDto> orderDetailsDtos = new ArrayList<>();

        for (CartTm cartTm: cartTms){
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    orderId,
                    cartTm.getItemId(),
                    cartTm.getCartQuantity(),
                    cartTm.getUnitPrice()
            );
            orderDetailsDtos.add(orderDetailsDto);
        }
        OrderDto orderDto = new OrderDto(
                orderId,
                customerId,
                dateofOrder,
                orderDetailsDtos
        );
        //boolean isSaved = orderModel.saveOrder(orderDto);
        boolean isSaved = placeOrderBO.saveOrder(orderDto);

        if (isSaved){
            new Alert(Alert.AlertType.INFORMATION, "Order Placed", ButtonType.OK).show();
            refreshPage();
        }else{
            new Alert(Alert.AlertType.ERROR, "Order failed", ButtonType.OK).show();
        }
    }

    @FXML
    void ResetOnClickAction(ActionEvent event) throws SQLException {
        refreshPage();
    }


    @FXML
    void CmbCustomerOnClickAction(ActionEvent event) throws SQLException {
        String selectedCustId = CmbCustId.getSelectionModel().getSelectedItem();
        Optional<Customer> customerDto = customerBO.findById(selectedCustId);

        if (customerDto != null) {
            LblCustName.setText(customerDto.get().getName());
        }
    }

    @FXML
    void CmbItemOnClickAction(ActionEvent event) throws SQLException {
        String selecteItemId = CmbItemId.getSelectionModel().getSelectedItem();
        Optional<Item> itemDto = itemBO.findById(selecteItemId);

        if (itemDto != null) {
            LblItemName.setText(itemDto.get().getName());
            LblQty.setText(String.valueOf(itemDto.get().getQuantity()));
            LblUnitPrice.setText(String.valueOf(itemDto.get().getUnitPrice()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try{
            refreshPage();
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, "Failed to load Data...!!!", ButtonType.OK).show();
        }
    }

    private void refreshPage() throws SQLException {
        LblOrder.setText(placeOrderBO.getNextOrderId());
        LblOrderDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadItemId();

        CmbCustId.getSelectionModel().clearSelection();
        CmbItemId.getSelectionModel().clearSelection();
        LblItemName.setText("");
        LblQty.setText("");
        LblUnitPrice.setText("");
        TxtQuantity.setText("");
        LblCustName.setText("");

        cartTms.clear();
        TblOrder.refresh();
    }

    private void loadItemId() throws SQLException {
        List<String> itemIds = itemBO.getAllItemIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(String.valueOf(itemIds));
        CmbItemId.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException {
        List<String> customerIds = customerBO.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        CmbCustId.setItems(observableList);
    }

    private void setCellValues() {
        ColItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        ColItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        ColQty.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        ColUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        ColTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        ColAction.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

        TblOrder.setItems(cartTms);
    }

    public void TblOnActionAction(MouseEvent mouseEvent) {
    }
}
