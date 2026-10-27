package kz.atu.lab09;

public class Participant {
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String tShirtSize;

    public Participant(String fullName, String email, String phone, String gender, String tShirtSize) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.tShirtSize = tShirtSize;
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public String getTShirtSize() { return tShirtSize; }
}