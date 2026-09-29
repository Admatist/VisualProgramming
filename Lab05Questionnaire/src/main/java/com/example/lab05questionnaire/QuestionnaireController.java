package com.example.lab05questionnaire;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class QuestionnaireController {

    @FXML private TextField txtFullName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtGroup;
    @FXML private ComboBox<String> cmbCity;
    @FXML private DatePicker dpBirthDate;

    @FXML private ToggleGroup courseGroup;
    @FXML private ToggleGroup studyFormGroup;

    @FXML private CheckBox chkDormitory;
    @FXML private CheckBox chkScholarship;
    @FXML private CheckBox chkActivist;
    @FXML private CheckBox chkConsent;

    @FXML private Button btnCreate;
    @FXML private Label lblResult;

    @FXML
    public void initialize() {
        // Заполнение ComboBox минимум 5 значениями (Самостоятельная часть)
        cmbCity.setItems(FXCollections.observableArrayList(
                "Алматы", "Астана", "Шымкент", "Караганда", "Актобе"
        ));

        // Кнопка "Сформировать" недоступна, пока ФИО пустое (Самостоятельная часть)
        btnCreate.disableProperty().bind(txtFullName.textProperty().isEmpty());
    }

    @FXML
    private void onCreateClick() {
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String group = txtGroup.getText().trim();
        String city = cmbCity.getValue();
        LocalDate birthDate = dpBirthDate.getValue();

        // 1. Проверка текстовых полей
        if (fullName.isBlank() || email.isBlank() || group.isBlank()) {
            showError("Заполните обязательные поля (ФИО, Email, Группа).");
            return;
        }

        // 2. Валидация Email
        if (!isEmailValid(email)) {
            showError("Введите корректный Email (например, user@example.com).");
            txtEmail.requestFocus();
            return;
        }

        // 3. Проверка выбора города и даты рождения
        if (city == null || birthDate == null) {
            showError("Выберите город и дату рождения.");
            return;
        }

        // 4. Проверка RadioButton в ToggleGroup
        RadioButton selectedCourse = (RadioButton) courseGroup.getSelectedToggle();
        RadioButton selectedForm = (RadioButton) studyFormGroup.getSelectedToggle();

        if (selectedCourse == null || selectedForm == null) {
            showError("Выберите курс и форму обучения.");
            return;
        }

        // 5. Проверка согласия на обработку данных (Самостоятельная часть)
        if (!chkConsent.isSelected()) {
            showError("Необходимо дать согласие на обработку персональных данных.");
            return;
        }

        // Формирование итогового текста
        String extras = buildExtras();
        lblResult.setText(
                "Студент: " + fullName +
                        "\nEmail: " + email +
                        "\nГруппа: " + group +
                        "\nГород: " + city +
                        "\nДата рождения: " + birthDate +
                        "\nКурс: " + selectedCourse.getText() +
                        "\nФорма обучения: " + selectedForm.getText() +
                        "\nДополнительно: " + extras
        );
    }

    // Базовая валидация Email
    private boolean isEmailValid(String email) {
        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1;
    }

    // Вспомогательный метод сбора флажков
    private String buildExtras() {
        StringBuilder result = new StringBuilder();
        if (chkDormitory.isSelected()) result.append("общежитие; ");
        if (chkScholarship.isSelected()) result.append("стипендия; ");
        if (chkActivist.isSelected()) result.append("активист; ");
        if (result.length() == 0) return "не выбрано";
        return result.toString();
    }

    // Окно сообщения об ошибке
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка ввода");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Очистка формы
    @FXML
    private void onClearClick() {
        txtFullName.clear();
        txtEmail.clear();
        txtGroup.clear();
        cmbCity.setValue(null);
        dpBirthDate.setValue(null);

        if (courseGroup.getSelectedToggle() != null) {
            courseGroup.getSelectedToggle().setSelected(false);
        }
        if (studyFormGroup.getSelectedToggle() != null) {
            studyFormGroup.getSelectedToggle().setSelected(false);
        }

        chkDormitory.setSelected(false);
        chkScholarship.setSelected(false);
        chkActivist.setSelected(false);
        chkConsent.setSelected(false);

        lblResult.setText("Результат: карточка очищена.");
        txtFullName.requestFocus();
    }

    // Выход из приложения
    @FXML
    private void onExitClick() {
        Platform.exit();
    }
}