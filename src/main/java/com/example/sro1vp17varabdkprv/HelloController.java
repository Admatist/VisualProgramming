package com.example.sro1vp17varabdkprv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> deptColumn;
    @FXML
    private TableColumn<Employee, String> posColumn;
    @FXML
    private TableColumn<Employee, Integer> expColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    private ObservableList<Employee> employeeList;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        posColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        expColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));

        // 10 демонстрационных записей
        employeeList = FXCollections.observableArrayList(
                new Employee("Иванов Иван Иванович", "IT", "Java Developer", 3),
                new Employee("Петров Пётр Петрович", "HR", "Recruiter", 2),
                new Employee("Сидорова Анна Сергеевна", "Finance", "Accountant", 5),
                new Employee("Смирнов Алексей Павлович", "IT", "DevOps Engineer", 4),
                new Employee("Кузнецова Елена Викторовна", "Marketing", "Manager", 1),
                new Employee("Васильев Дмитрий Олегович", "IT", "QA Engineer", 3),
                new Employee("Морозова Ольга Николаевна", "HR", "HR Director", 8),
                new Employee("Федоров Максим Андреевич", "Finance", "Financial Analyst", 6),
                new Employee("Николаева Екатерина Игоревна", "Marketing", "Content Maker", 2),
                new Employee("Степанов Андрей Георгиевич", "IT", "Team Lead", 7)
        );

        // Фильтрация поиска
        FilteredList<Employee> filteredData = new FilteredList<>(employeeList, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (employee.getFullName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getDepartment().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getPosition().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(employee.getExperience()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            statusLabel.setText("Статус: Фильтр применен. Найдено записей: " + filteredData.size());
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
        employeeTable.setItems(sortedData);
    }

    @FXML
    private void handleAddEmployee() {
        Employee tempEmployee = new Employee("", "", "", 0);
        boolean savedClicked = showEmployeeDialog(tempEmployee, "Добавить сотрудника");
        if (savedClicked) {
            employeeList.add(tempEmployee);
            statusLabel.setText("Статус: Сотрудник добавлен. Всего записей: " + employeeList.size());
        }
    }

    @FXML
    private void handleEditEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            boolean savedClicked = showEmployeeDialog(selectedEmployee, "Редактировать сотрудника");
            if (savedClicked) {
                employeeTable.refresh();
                statusLabel.setText("Статус: Данные сотрудника обновлены.");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Элемент не выбран");
            alert.setContentText("Пожалуйста, выберите сотрудника в таблице для редактирования.");
            alert.showAndWait();
        }
    }

    private boolean showEmployeeDialog(Employee employee, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("employee-dialog.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(employeeTable.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EmployeeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEmployee(employee);

            dialogStage.showAndWait();

            return controller.isSavedClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employeeList.remove(selected);
            statusLabel.setText("Статус: Сотрудник удален. Всего записей: " + employeeList.size());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Элемент не выбран");
            alert.setContentText("Пожалуйста, выберите сотрудника в таблице для удаления.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("СРО №1. Визуальное программирование");
        alert.setContentText("Вариант 17: Employee Management System\nРазработано для выполнения СРО.");
        alert.showAndWait();
    }
}