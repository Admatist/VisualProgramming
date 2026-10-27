package kz.atu.lab09;

import java.util.regex.Pattern;

public class RegistrationValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,12}$");

    public static ValidationResult validateFullName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ValidationResult(false, "ФИО не может быть пустым");
        }
        if (name.trim().split("\\s+").length < 2) {
            return new ValidationResult(false, "Введите имя и фамилию");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            return new ValidationResult(false, "Неверный формат email");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validatePhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            return new ValidationResult(false, "Телефон: от 10 до 12 цифр");
        }
        return new ValidationResult(true, "");
    }
}