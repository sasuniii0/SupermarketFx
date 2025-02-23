package lk.ijse.gdse71.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.supermarketfx.dto.ItemDto;
import lk.ijse.gdse71.supermarketfx.dto.tm.ItemTm;
import lk.ijse.gdse71.supermarketfx.model.CustomerModel;
import lk.ijse.gdse71.supermarketfx.model.ItemModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private Button BtnDelete;

    @FXML
    private Button BtnReset;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnUpdate;

    @FXML
    private TableColumn<ItemTm, String> ColItemId;

    @FXML
    private TableColumn<ItemTm, String> ColItemName;

    @FXML
    private TableColumn<ItemTm, Double> ColPrice;

    @FXML
    private TableColumn<ItemTm, Integer> ColQuantity;

    @FXML
    private TableView<ItemTm> TblItem;

    @FXML
    private Label LblItemId;

    @FXML
    private Label LblItemName;

    @FXML
    private Label LblItemPrice;

    @FXML
    private Label LblQuantity;

    @FXML
    private TextField TxtName;

    @FXML
    private TextField TxtPrice;

    @FXML
    private TextField TxtQuantity;

    @FXML
    void ClickOnTableAction(MouseEvent event) {
        ItemTm itemTm = TblItem.getSelectionModel().getSelectedItem();
        if (itemTm != null) {
            LblItemId.setText(itemTm.getItemId());
            TxtName.setText(itemTm.getItemName());
            TxtQuantity.setText(String.valueOf(itemTm.getQuantity()));
            TxtPrice.setText(String.valueOf(itemTm.getUnitPrice()));

            BtnSave.setDisable(true);
            BtnDelete.setDisable(false);
            BtnUpdate.setDisable(false);
        }
    }

    @FXML
    void DeleteClickOnAction(ActionEvent event) throws SQLException {
        String itemId = LblItemId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = itemModel.deleteItem(itemId);
            if (isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed to delete Item...").show();
            }
        }
    }

    @FXML
    void ResetClickOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void SaveClickOnACtion(ActionEvent event) throws SQLException {
        String itemId = LblItemId.getText();
        String itemName = TxtName.getText();
        int quantity = Integer.parseInt(TxtQuantity.getText());
        double price = Double.parseDouble(TxtPrice.getText());

        ItemDto itemDto = new ItemDto(itemId, itemName, quantity, price);

        boolean isSaved = itemModel.saveItem(itemDto);
        if (isSaved){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Item Saved").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Failed to save Item").show();
        }
    }

    @FXML
    void UpdateClickOnAction(ActionEvent event) throws SQLException {
        String itemId = LblItemId.getText();
        String itemName = TxtName.getText();
        int quantity = Integer.parseInt(TxtQuantity.getText());
        double price = Double.parseDouble(TxtPrice.getText());

        ItemDto itemDto = new ItemDto(itemId, itemName, quantity, price);

        boolean isUpdated = itemModel.updateItem(itemDto);
        if (isUpdated){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Item Updated").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Failed to update Item...").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        ColItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        ColQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ColPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try{
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load ItemId...", ButtonType.OK).show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextItemId();
        loadTableData();

        BtnSave.setDisable(false);
        BtnDelete.setDisable(true);
        BtnUpdate.setDisable(true);

        TxtName.setText("");
        TxtPrice.setText("");
        TxtQuantity.setText("");
    }

    ItemModel itemModel = new ItemModel();

    private void loadTableData() throws SQLException {
        ArrayList<ItemDto> itemDtos = itemModel.getAllItems();

        ObservableList<ItemTm>itemTms = FXCollections.observableArrayList();

        for (ItemDto itemDto : itemDtos) {
            ItemTm itemTm = new ItemTm(
                    itemDto.getItemId(),
                    itemDto.getItemName(),
                    itemDto.getQuantity(),
                    itemDto.getUnitPrice()
            );
            itemTms.add(itemTm);
        }
        TblItem.setItems(itemTms);
    }

    private void loadNextItemId() throws SQLException {
        String nextItemIdId = itemModel.getNextItemId();
        LblItemId.setText(nextItemIdId);
    }
}