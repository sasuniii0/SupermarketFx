package lk.ijse.gdse71.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse71.supermarketfx.dto.CustomerDto;
import lk.ijse.gdse71.supermarketfx.view.tdm.CustomerTm;
import lk.ijse.gdse71.supermarketfx.dao.custom.impl.CustomerDAOImpl;
/*
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
*/

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private Button BtnAllCustomerRep;

    @FXML
    private Button BtnDelete;

    @FXML
    private Button BtnGenerateRep;

    @FXML
    private Button BtnReset;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnUpdate;

    @FXML
    private TableColumn<CustomerTm, String> ColCustId;

    @FXML
    private TableColumn<CustomerTm, String> ColCustName;

    @FXML
    private TableColumn<CustomerTm, String> ColEmail;

    @FXML
    private TableColumn<CustomerTm, String> ColNic;

    @FXML
    private TableColumn<CustomerTm, String> ColPhone;

    @FXML
    private Label LblCustId;

    @FXML
    private TableView<CustomerTm> TblCustomer;

    @FXML
    private TextField TxtCustomerName;

    @FXML
    private TextField TxtEmail;

    @FXML
    private TextField TxtNic;

    @FXML
    private TextField TxtPhone;

    @FXML
    private Button BtnOpenMailSendMail;

    @FXML
    void AllCustomerRepOnClickAction(ActionEvent event) {

    }

    @FXML
    void ClickOnTable(MouseEvent event) {
        CustomerTm customerTm = TblCustomer.getSelectionModel().getSelectedItem();
        if (customerTm != null) {
            LblCustId.setText(customerTm.getCustomerId());
            TxtCustomerName.setText(customerTm.getCustomerName());
            TxtNic.setText(customerTm.getNic());
            TxtEmail.setText(customerTm.getEmail());
            TxtPhone.setText(customerTm.getPhone());

            BtnSave.setDisable(true);

            BtnDelete.setDisable(false);
            BtnUpdate.setDisable(false);
        }
    }

    @FXML
    void DeleteOnClickAction(ActionEvent event) throws SQLException {
        String customerId = LblCustId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure ?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = customerModel.deleteCustomer(customerId);
            if(isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed to delete Customer").show();
            }
        }
    }

    /*@FXML
    void GenerateRepOnClickAction(ActionEvent event) {
        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream(
                    "/report/CustomerRep.jrxml"
            ));

            Connection connection = DBConnection.getInstance().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,null,connection
            );

            JasperViewer.viewReport(jasperPrint,false);
        }catch(JRException e){
            new Alert(Alert.AlertType.ERROR,"Failed to generate report..").show();
            e.printStackTrace();
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR,"DB Error").show();
        }
    }*/

    @FXML
    void ResetOnClickAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void SaveOnClickAction(ActionEvent event) throws SQLException {
        String customerId = LblCustId.getText();
        String customerName = TxtCustomerName.getText();
        String nic = TxtNic.getText();
        String email = TxtEmail.getText();
        String phone = TxtPhone.getText();

        TxtCustomerName.setStyle(TxtCustomerName.getStyle()+";-fx-border-color: blue;");
        TxtNic.setStyle(TxtNic.getStyle()+";-fx-border-color: blue;");
        TxtEmail.setStyle(TxtEmail.getStyle()+";-fx-border-color: blue;");
        TxtPhone.setStyle(TxtPhone.getStyle()+";-fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = customerName.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName){
            System.out.println(TxtCustomerName.getStyle());
            TxtCustomerName.setStyle("-fx-border-color: red;");
            System.out.println("Invalid name.......");
            return;
        }
        if (!isValidNic){
            TxtNic.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidEmail){
            TxtEmail.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidPhone){
            TxtPhone.setStyle("-fx-border-color: red;");
            return;
        }

        if (isValidEmail && isValidPhone && isValidName && isValidNic){
            CustomerDto customerDto = new CustomerDto(customerId, customerName, nic, email, phone);

            boolean isSaved = customerModel.saveCustomer(customerDto);
            if (isSaved){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved....").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed to save Customer...").show();
            }
        }

    }

    @FXML
    void UpdateOnClickAction(ActionEvent event) throws SQLException {
        String customerId = LblCustId.getText();
        String customerName = TxtCustomerName.getText();
        String nic = TxtNic.getText();
        String email = TxtEmail.getText();
        String phone = TxtPhone.getText();

        TxtCustomerName.setStyle(TxtCustomerName.getStyle()+";-fx-border-color: blue;");
        TxtNic.setStyle(TxtNic.getStyle()+";-fx-border-color: blue;");
        TxtEmail.setStyle(TxtEmail.getStyle()+";-fx-border-color: blue;");
        TxtPhone.setStyle(TxtPhone.getStyle()+";-fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = customerName.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName){
            TxtCustomerName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidNic){
            TxtNic.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidEmail){
            TxtEmail.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidPhone){
            TxtPhone.setStyle("-fx-border-color: red;");
            return;
        }
        if (isValidEmail && isValidPhone && isValidName && isValidNic){
            CustomerDto customerDto = new CustomerDto(
                    customerId, customerName, nic, email, phone
            );
            boolean isUpdate = customerModel.updateCustomer(customerDto);
            if (isUpdate){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION,"Customer Updated....").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed to update Customer...").show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        ColCustName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        ColNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        ColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        ColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try{
            refreshPage();
        } catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load Customer Id...").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextCustomerId();
        loadTableData();

        BtnSave.setDisable(false);
        BtnUpdate.setDisable(true);
        BtnDelete.setDisable(true);

        TxtCustomerName.setText("");
        TxtNic.setText("");
        TxtEmail.setText("");
        TxtPhone.setText("");
    }

    CustomerDAOImpl customerModel = new CustomerDAOImpl();

    private void loadTableData() throws SQLException {
        ArrayList<CustomerDto> customerDtos = customerModel.getAllCustomers();

        ObservableList<CustomerTm> customerTms = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDtos) {
            CustomerTm customerTm = new CustomerTm(
                    customerDto.getCustomerId(),
                    customerDto.getCustomerName(),
                    customerDto.getNic(),
                    customerDto.getEmail(),
                    customerDto.getPhone()
            );
            customerTms.add(customerTm);
        }
        TblCustomer.setItems(customerTms);
    }

    private void loadNextCustomerId() throws SQLException {
        String nextCustomerId = customerModel.getNextCustomerId();
        LblCustId.setText(nextCustomerId);
    }

    // extra mail sender......
    public void OpenMailAndSendMailClickOnAction(ActionEvent actionEvent) throws IOException {
        CustomerTm customerTm = TblCustomer.getSelectionModel().getSelectedItem();
        if (customerTm == null) {
            new Alert(Alert.AlertType.ERROR,"select Customer..").show();
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmailSend.fxml"));
            Parent load = loader.load();

            EmailSendeController emailSendeController = loader.getController();

            String email = customerTm.getEmail();
            emailSendeController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send Mail");
            stage.initModality(Modality.APPLICATION_MODAL);
            Window underWIndow = BtnUpdate.getScene().getWindow();
            stage.initOwner(underWIndow);
            stage.showAndWait();
        }catch(IOException e){
            new Alert(Alert.AlertType.ERROR,"Failed to load UI...").show();
            e.printStackTrace();
        }
    }

    public void GenerateRepOnClickAction(ActionEvent actionEvent) {
    }
}
