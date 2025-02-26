module lk.ijse.gdse71.supermarketfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires java.mail;
    requires net.sf.jasperreports.core;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens lk.ijse.gdse71.supermarketfx.view.tdm to javafx.base;
    opens lk.ijse.gdse71.supermarketfx.controller to javafx.fxml;
    exports lk.ijse.gdse71.supermarketfx;
}