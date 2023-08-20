package com.challenge2.challenge2.entitiesTests;

import com.challenge2.challenge2.entities.Organizer;
import com.challenge2.challenge2.enums.OrganizerEnums;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizerTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testRole() {
        OrganizerEnums expectedRole = OrganizerEnums.Coordinator;
        Organizer organizer = new Organizer();
        organizer.setRole(expectedRole);

        OrganizerEnums actualRole = organizer.getRole();

        assertEquals(expectedRole, actualRole, "O papel do organizador deve ser igual ao esperado.");
    }

    @Test
    public void testInheritedProperties() {
        // Testando propriedades herdadas da classe "User"
        String expectedName = "John Doe";
        String expectedEmail = "john.doe@example.com";
        String expectedCity = "New York";
        String expectedPassword = "Abcd1234@";

        Organizer organizer = new Organizer();
        organizer.setName(expectedName);
        organizer.setEmail(expectedEmail);
        organizer.setCity(expectedCity);
        organizer.setPassword(expectedPassword);

        // Validação usando o Jakarta Validation API para garantir que as restrições sejam atendidas
        var violations = validator.validate(organizer);
        assertTrue(violations.isEmpty(), "Não deve haver violações das restrições de validação.");

        String actualName = organizer.getName();
        String actualEmail = organizer.getEmail();
        String actualCity = organizer.getCity();
        String actualPassword = organizer.getPassword();

        assertEquals(expectedName, actualName, "O nome do organizador deve ser igual ao esperado.");
        assertEquals(expectedEmail, actualEmail, "O e-mail do organizador deve ser igual ao esperado.");
        assertEquals(expectedCity, actualCity, "A cidade do organizador deve ser igual à esperada.");
        assertEquals(expectedPassword, actualPassword, "A senha do organizador deve ser igual à esperada.");
    }
}

