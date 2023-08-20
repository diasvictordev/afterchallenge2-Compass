package com.challenge2.challenge2.entitiesTests;

import com.challenge2.challenge2.entities.Student;
import com.challenge2.challenge2.entities.Squad;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidStudent() {
        Student student = new Student();
                student.setName("John Doe");
                student.setEmail("john.doe@example.com");
                student.setCity("New York");
                student.setPassword("Abcd1234@");
                student.setCollege("University of Example");
                student.setGrade(8.5f);
                student.setAttendance(90.0f);
                student.setStartDate(LocalDateTime.of(2023, 7, 1, 10, 0));
                student.setEndDate(LocalDateTime.of(2023, 12, 31, 18, 0));
                student.setSquad(new Squad());

        var violations = validator.validate(student);
        assertTrue(violations.isEmpty(), "Não deve haver violações das restrições de validação.");
    }

    @Test
    public void testInvalidGrade() {
        Student student = new Student();
                student.setName("Jane Smith");
                student.setEmail("jane.smith@example.com");
                student.setCity("Los Angeles");
                student.setPassword("Password123@");
                student.setCollege("College of Testing");
                student.setGrade(15.0f);
                student.setAttendance(85.0f);
                student.setStartDate(LocalDateTime.of(2022, 9, 15, 9, 30));
                student.setEndDate(LocalDateTime.of(2022, 12, 31, 18, 0));
                student.setSquad(new Squad());

        var violations = validator.validate(student);
        assertFalse(violations.isEmpty(), "Deve haver violações das restrições de validação para nota inválida.");
    }

    @Test
    public void testInvalidAttendance() {
        Student student = new Student();
                student.setName(("Bob Johnson"));
                student.setEmail("bob.johnson@example.com");
                student.setCity("Chicago");
                student.setPassword("Test1234@");
                student.setCollege("College of Testing");
                student.setGrade(9.0F);
                student.setAttendance(-10.0f);
                student.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0));
                student.setEndDate(LocalDateTime.of(2024, 6, 30, 18, 0));
                student.setSquad(new Squad());

        var violations = validator.validate(student);
        assertFalse(violations.isEmpty(), "Deve haver violações das restrições de validação para presença inválida.");
    }
}
