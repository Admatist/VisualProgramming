module com.example.noteeditor {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.noteeditor to javafx.fxml;
    exports com.example.noteeditor;
}