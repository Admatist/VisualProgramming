package kz.atu.lab09;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistrationController {

    @FXML private TextField fullNameField;
    @FXML private Label nameErrorLabel;

    @FXML private TextField emailField;
    @FXML private Label emailErrorLabel;

    @FXML private TextField phoneField;
    @FXML private Label phoneErrorLabel;

    @FXML private ToggleGroup genderGroup;
    @FXML private ComboBox<String> sizeComboBox;
    @FXML private Label successLabel;

    @FXML
    public void initialize() {
        sizeComboBox.getItems().addAll("S", "M", "L", "XL", "XXL");
        sizeComboBox.setValue("M");
    }

    @FXML
    protected void handleRegisterButton() {
        clearErrors();

        boolean isValid = true;

        // Проверка ФИО
        ValidationResult nameRes = RegistrationValidator.validateFullName(fullNameField.getText());
        if (!nameRes.isValid()) {
            nameErrorLabel.setText(nameRes.getMessage());
            fullNameField.getStyleClass().add("error-field");
            isValid = false;
        }

        // Проверка Email
        ValidationResult emailRes = RegistrationValidator.validateEmail(emailField.getText());
        if (!emailRes.isValid()) {
            emailErrorLabel.setText(emailRes.getMessage());
            emailField.getStyleClass().add("error-field");
            isValid = false;
        }

        // Проверка телефона
        ValidationResult phoneRes = RegistrationValidator.validatePhone(phoneField.getText());
        if (!phoneRes.isValid()) {
            phoneErrorLabel.setText(phoneRes.getMessage());
            phoneField.getStyleClass().add("error-field");
            isValid = false;
        }

        if (isValid) {
            RadioButton selectedGender = (RadioButton) genderGroup.getSelectedToggle();
            String gender = selectedGender != null ? selectedGender.getText() : "Не указан";
            String size = sizeComboBox.getValue();

            Participant participant = new Participant(
                    fullNameField.getText().trim(),
                    emailField.getText().trim(),
                    phoneField.getText().trim(),
                    gender,
                    size
            );

            successLabel.setText("Успешная регистрация участника: " + participant.getFullName());
        }
    }

    private void clearErrors() {
        nameErrorLabel.setText("");
        emailErrorLabel.setText("");
        phoneErrorLabel.setText("");
        successLabel.setText("");

        fullNameField.getStyleClass().remove("error-field");
        emailField.getStyleClass().remove("error-field");
        phoneField.getStyleClass().remove("error-field");
    }
}