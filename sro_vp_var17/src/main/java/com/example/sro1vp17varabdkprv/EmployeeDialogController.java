package com.example.sro1vp17varabdkprv;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeDialogController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField experienceField;

    private Stage dialogStage;
    private Employee employee;
    private boolean savedClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        if (employee != null && employee.getFullName() != null && !employee.getFullName().isEmpty()) {
            fullNameField.setText(employee.getFullName());
            departmentField.setText(employee.getDepartment());
            positionField.setText(employee.getPosition());
            experienceField.setText(String.valueOf(employee.getExperience()));
        }
    }

    public boolean isSavedClicked() {
        return savedClicked;
    }

    public Employee getEmployee() {
        return employee;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            if (employee == null) {
                employee = new Employee(
                        fullNameField.getText(),
                        departmentField.getText(),
                        positionField.getText(),
                        Integer.parseInt(experienceField.getText())
                );
            } else {
                employee.setFullName(fullNameField.getText());
                employee.setDepartment(departmentField.getText());
                employee.setPosition(positionField.getText());
                employee.setExperience(Integer.parseInt(experienceField.getText()));
            }

            savedClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (fullNameField.getText() == null || fullNameField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнено поле ФИО!\n");
        }
        if (departmentField.getText() == null || departmentField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнено поле Отдел!\n");
        }
        if (positionField.getText() == null || positionField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнено поле Должность!\n");
        }

        String expText = experienceField.getText();
        if (expText == null || expText.trim().isEmpty()) {
            errorMessage.append("Не заполнено поле Стаж!\n");
        } else {
            try {
                int exp = Integer.parseInt(expText);
                if (exp < 0) {
                    errorMessage.append("Стаж не может быть отрицательным числом!\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Стаж должен быть целым числом!\n");
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Пожалуйста, исправьте следующие ошибки:");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            return false;
        }
    }
}