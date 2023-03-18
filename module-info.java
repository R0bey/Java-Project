module com.example.lab4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.lab4 to javafx.fxml;
    exports com.example.lab4;

    opens com.example.lab4.entities to javafx.fxml;
    exports com.example.lab4.entities;
}