package kz.atu.lab11.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kz.atu.lab11.model.Student;
import kz.atu.lab11.service.StudentService;

import java.util.Optional;

public class StudentController {
    @FXML private TableView<Student> tableStudents;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colGroup;
    @FXML private TableColumn<Student, Number> colGpa;

    @FXML private TextField txtName;
    @FXML private TextField txtGroup;
    @FXML private TextField txtGpa;
    @FXML private TextField txtSearch;

    @FXML private ComboBox<String> cmbGroupFilter;
    @FXML private Label lblCount;

    private final StudentService service = new StudentService();
    private FilteredList<Student> filtered;

    @FXML
    private void initialize() {
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colGroup.setCellValueFactory(c -> c.getValue().groupProperty());
        colGpa.setCellValueFactory(c -> c.getValue().gpaProperty());

        service.addStudent("Иванов Иван", "ИС-301", 3.5);
        service.addStudent("Петров Пётр", "ИС-302", 3.8);

        filtered = new FilteredList<>(service.getStudents(), s -> true);
        SortedList<Student> sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(tableStudents.comparatorProperty());
        tableStudents.setItems(sorted);

        updateGroupFilterItems();
        cmbGroupFilter.setValue("Все");

        txtSearch.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        cmbGroupFilter.valueProperty().addListener((o, oldVal, newVal) -> applyFilter());

        tableStudents.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtName.setText(newSel.getName());
                txtGroup.setText(newSel.getGroup());
                txtGpa.setText(String.valueOf(newSel.getGpa()));
            }
        });

        updateCount();
    }

    private void applyFilter() {
        String q = txtSearch.getText().trim().toLowerCase();
        String group = cmbGroupFilter.getValue();

        filtered.setPredicate(s -> s.getName().toLowerCase().contains(q)
                && (group == null || group.equals("Все") || s.getGroup().equals(group)));

        updateCount();
    }

    private void updateCount() {
        lblCount.setText("Найдено студентов: " + filtered.size());
    }

    private void updateGroupFilterItems() {
        String currentSelection = cmbGroupFilter.getValue();
        cmbGroupFilter.getItems().clear();
        cmbGroupFilter.getItems().add("Все");

        service.getStudents().stream()
                .map(Student::getGroup)
                .distinct()
                .forEach(cmbGroupFilter.getItems()::add);

        if (currentSelection != null && cmbGroupFilter.getItems().contains(currentSelection)) {
            cmbGroupFilter.setValue(currentSelection);
        } else {
            cmbGroupFilter.setValue("Все");
        }
    }

    private double readGpa() {
        try {
            return Double.parseDouble(txtGpa.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введите числовой GPA");
        }
    }

    @FXML
    private void onAdd() {
        try {
            service.addStudent(txtName.getText(), txtGroup.getText(), readGpa());
            updateGroupFilterItems();
            applyFilter();
            clearFields();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void onUpdate() {
        try {
            Student s = tableStudents.getSelectionModel().getSelectedItem();
            service.updateStudent(s, txtName.getText(), txtGroup.getText(), readGpa());
            updateGroupFilterItems();
            applyFilter();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void onDelete() {
        Student selected = tableStudents.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Студент не выбран");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Подтверждение");
        confirm.setHeaderText(null);
        confirm.setContentText("Вы действительно хотите удалить запись?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                service.deleteStudent(selected);
                updateGroupFilterItems();
                applyFilter();
                clearFields();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        }
    }

    private void clearFields() {
        txtName.clear();
        txtGroup.clear();
        txtGpa.clear();
        tableStudents.getSelectionModel().clearSelection();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}