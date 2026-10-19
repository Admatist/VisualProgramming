package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    @FXML private TextField txtName;
    @FXML private TextField txtSurname;
    @FXML private TextField txtAge;
    @FXML private TextField txtSpeciality;
    @FXML private ComboBox<String> cmbCourse;
    @FXML private ComboBox<String> cmbCity;
    @FXML private RadioButton rbOchnaya;
    @FXML private ToggleGroup studyGroup;
    @FXML private CheckBox chkHostel;
    @FXML private Label lblResult;

    @FXML
    public void initialize() {
        // Инициализация выпадающего списка курсов
        cmbCourse.getItems().addAll("1 курс", "2 курс", "3 курс", "4 курс");
        cmbCourse.getSelectionModel().selectFirst();

        // Инициализация выпадающего списка городов (самостоятельная часть)
        cmbCity.getItems().addAll("Алматы", "Астана", "Шымкент", "Караганда", "Актобе");
        cmbCity.getSelectionModel().selectFirst();
    }

    @FXML
    private void onCreateClick() {
        String name = txtName.getText().trim();
        String surname = txtSurname.getText().trim();
        String ageStr = txtAge.getText().trim();
        String speciality = txtSpeciality.getText().trim();
        String course = cmbCourse.getValue();
        String city = cmbCity.getValue();

        // Проверка на пустые поля
        if (name.isEmpty() || surname.isEmpty() || ageStr.isEmpty() || speciality.isEmpty()) {
            showError("Заполните все обязательные поля!");
            return;
        }

        // Проверка возраста на число
        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            showError("Возраст должен быть целым числом!");
            return;
        }

        // Диапазон возраста
        if (age < 16 || age > 100) {
            showError("Введите корректный возраст (от 16 до 100 лет).");
            return;
        }

        // Получение выбранной формы обучения из RadioButton
        RadioButton selectedRadio = (RadioButton) studyGroup.getSelectedToggle();
        String studyMode = (selectedRadio != null) ? selectedRadio.getText() : "Не указано";

        // Проверка чекбокса общежития
        String hostel = chkHostel.isSelected() ? "Да" : "Нет";

        // Вывод итоговой карточки в Label
        lblResult.setText(String.format(
                "Студент: %s %s\nВозраст: %d\nСпециальность: %s\nКурс: %s\nГород: %s\nФорма обучения: %s\nОбщежитие: %s",
                surname, name, age, speciality, course, city, studyMode, hostel
        ));
    }

    @FXML
    private void onClearClick() {
        txtName.clear();
        txtSurname.clear();
        txtAge.clear();
        txtSpeciality.clear();
        cmbCourse.getSelectionModel().selectFirst();
        cmbCity.getSelectionModel().selectFirst();
        rbOchnaya.setSelected(true);
        chkHostel.setSelected(false);
        lblResult.setText("Результат:");
        txtName.requestFocus();
    }

    @FXML
    private void onExitClick() {
        Platform.exit();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка ввода");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}