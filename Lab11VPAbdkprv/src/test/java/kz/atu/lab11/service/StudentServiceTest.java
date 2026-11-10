package kz.atu.lab11.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService service;

    @BeforeEach
    void setUp() {
        service = new StudentService();
    }

    @Test
    void shouldAddStudent() {
        service.addStudent("Алия", "ИС-301", 3.7);
        assertEquals(1, service.getStudents().size());
    }

    @Test
    void shouldRejectInvalidGpa() {
        assertThrows(IllegalArgumentException.class, () -> service.addStudent("Алия", "ИС-301", 5.0));
    }

    @Test
    void shouldRejectDuplicateStudent() {
        service.addStudent("Иванов Иван", "ИС-301", 3.5);
        assertThrows(IllegalArgumentException.class, () -> service.addStudent("Иванов Иван", "ИС-301", 3.8));
    }

    @Test
    void shouldCalculateAverageGpaCorrectly() {
        service.addStudent("Студент 1", "ИС-301", 3.0);
        service.addStudent("Студент 2", "ИС-302", 4.0);
        assertEquals(3.5, service.getAverageGpa(), 0.001);
    }
}