module com.example.lab05questionnaire {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab05questionnaire to javafx.fxml;
    exports com.example.lab05questionnaire;
}