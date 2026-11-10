package kz.atu.lab11.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kz.atu.lab11.model.Student;

public class StudentService {
    private final ObservableList<Student> students = FXCollections.observableArrayList();

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void addStudent(String name, String group, double gpa) {
        validate(name, group, gpa);

        boolean exists = students.stream().anyMatch(s ->
                s.getName().equalsIgnoreCase(name.trim()) && s.getGroup().equalsIgnoreCase(group.trim())
        );
        if (exists) {
            throw new IllegalArgumentException("Студент с таким ФИО и группой уже существует");
        }

        students.add(new Student(name.trim(), group.trim(), gpa));
    }

    public void updateStudent(Student student, String name, String group, double gpa) {
        if (student == null) {
            throw new IllegalArgumentException("Студент не выбран");
        }
        validate(name, group, gpa);
        student.setName(name.trim());
        student.setGroup(group.trim());
        student.setGpa(gpa);
    }

    public void deleteStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Студент не выбран");
        }
        students.remove(student);
    }

    public double getAverageGpa() {
        if (students.isEmpty()) return 0.0;
        return students.stream().mapToDouble(Student::getGpa).average().orElse(0.0);
    }

    private void validate(String name, String group, double gpa) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Введите ФИО");
        }
        if (group == null || group.isBlank()) {
            throw new IllegalArgumentException("Введите группу");
        }
        if (Double.isNaN(gpa) || gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA должен быть в диапазоне от 0 до 4");
        }
    }
}