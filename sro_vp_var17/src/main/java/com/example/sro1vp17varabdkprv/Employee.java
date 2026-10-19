package com.example.sro1vp17varabdkprv;

public class Employee {
    private String fullName;
    private String department;
    private String position;
    private int experience;

    public Employee(String fullName, String department, String position, int experience) {
        this.fullName = fullName;
        this.department = department;
        this.position = position;
        this.experience = experience;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}