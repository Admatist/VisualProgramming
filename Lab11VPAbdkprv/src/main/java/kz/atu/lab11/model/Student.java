package kz.atu.lab11.model;

import javafx.beans.property.*;

public class Student {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty group = new SimpleStringProperty();
    private final DoubleProperty gpa = new SimpleDoubleProperty();

    public Student(String name, String group, double gpa) {
        setName(name);
        setGroup(group);
        setGpa(gpa);
    }

    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    public String getGroup() { return group.get(); }
    public void setGroup(String value) { group.set(value); }
    public StringProperty groupProperty() { return group; }

    public double getGpa() { return gpa.get(); }
    public void setGpa(double value) { gpa.set(value); }
    public DoubleProperty gpaProperty() { return gpa; }
}