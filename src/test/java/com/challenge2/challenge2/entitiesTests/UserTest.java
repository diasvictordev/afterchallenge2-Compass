package com.challenge2.challenge2.entitiesTests;

import com.challenge2.challenge2.entities.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUser() {
        User user = User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .city("New York")
                .password("Abcd1234@")
                .build();

        var violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Não deve haver violações das restrições de validação.");
    }

    @Test
    public void testInvalidEmail() {
        User user = User.builder()
                .name("Nome Teste")
                .email("Test#Exemplo")
                .city("Fenda do Biquini")
                .password("Password123@")
                .build();

        var violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Deve haver violações das restrições de validação para e-mail inválido.");
    }

    @Test
    public void testInvalidPassword() {
        User user = User.builder()
                .name("Bob Johnson")
                .email("bob.johnson@example.com")
                .city("Chicago")
                .password("password123")
                .build();

        var violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Deve haver violações das restrições de validação para senha inválida.");
    }
}
