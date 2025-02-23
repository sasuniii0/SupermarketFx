package lk.ijse.gdse71.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Setter
public class EmailSendeController {

    @FXML
    private Button BtnSend;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextArea TxtBody;

    @FXML
    private TextField TxtEmail;

    @FXML
    private TextField TxtSubject;

    @Setter
    private String customerEmail;

    @FXML
    void SendActionClickOn(ActionEvent event) {
        if(customerEmail == null){
            return;
        }
        final String FROM = "sanjayagas@gmail.com";

        String subject = TxtSubject.getText();
        String body = TxtBody.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING,"Subject and Body must not be Empty!...").show();
            return;
        }
        sendEmailWithSenderGrid(FROM , customerEmail , subject, body);
    }

    private void sendEmailWithSenderGrid(String from, String to, String subject, String body) {
        String USER_NAME = "apikey";
        String PASSWORD = "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";

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION,"Email sent successfully!").show();
        }catch(MessagingException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to send Email!").show();
        }

    }

}
