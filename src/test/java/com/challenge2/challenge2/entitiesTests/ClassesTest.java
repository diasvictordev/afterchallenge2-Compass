package com.challenge2.challenge2.entitiesTests;

import com.challenge2.challenge2.entities.Classes;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ClassesTest {

    private Classes classes;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @BeforeEach
    public void setUp() {
        classes = new Classes();
    }

    @Test
    public void testValidClass(){
        classes.setLearningPath("AWS");
        classes.setSprint(3);

        Set<ConstraintViolation<Classes>> violations = validator.validate(classes);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidSprint(){
        classes.setLearningPath("Spring Boot");
        classes.setSprint(0);

        Set<ConstraintViolation<Classes>> violations = validator.validate(classes);
        assertEquals(1, violations.size());
        ConstraintViolation<Classes> violation = violations.iterator().next();
        assertEquals("O valor mínimo para sprint é 1", violation.getMessage());
        assertEquals("sprint", violation.getPropertyPath().toString());
    }

    @Test
    public void testEmptyLearningPath(){
        classes.setLearningPath("");
        classes.setSprint(2);

        Set<ConstraintViolation<Classes>> violations = validator.validate(classes);
        assertEquals(1, violations.size());
        ConstraintViolation<Classes> violation = violations.iterator().next();
        assertEquals("Learning path não pode ser vazio.", violation.getMessage());
        assertEquals("learningPath", violation.getPropertyPath().toString());
    }

    @Test
    public void testId() {
        Long expectedId = 1L;
        classes.setId(expectedId);

        Long actualId = classes.getId();

        assertEquals(expectedId, actualId, "O ID deve ser igual ao esperado.");
    }

    @Test
    public void testLearningPath() {
        String expectedLearningPath = "Java Programming";
        classes.setLearningPath(expectedLearningPath);

        String actualLearningPath = classes.getLearningPath();

        assertEquals(expectedLearningPath, actualLearningPath, "O Learning Path deve ser igual ao esperado.");
    }

    @Test
    public void testSprint() {
        Integer expectedSprint = 3;
        classes.setSprint(expectedSprint);

        Integer actualSprint = classes.getSprint();

        assertEquals(expectedSprint, actualSprint, "A Sprint deve ser igual à esperada.");
    }


}
