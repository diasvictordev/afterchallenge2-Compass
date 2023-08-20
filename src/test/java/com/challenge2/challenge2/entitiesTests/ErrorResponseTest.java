package com.challenge2.challenge2.entitiesTests;

import com.challenge2.challenge2.entities.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

public class ErrorResponseTest {

    private ErrorResponse errorResponse;

    @BeforeEach
    public void setUp() {
        errorResponse = new ErrorResponse();
    }

    @Test
    public void testMessage() {
        String expectedMessage = "Erro interno no servidor.";
        errorResponse.setMessage(expectedMessage);

        String actualMessage = errorResponse.getMessage();

        assertEquals(expectedMessage, actualMessage, "A mensagem deve ser igual à esperada.");
    }

    @Test
    public void testTimestamp() {
        Timestamp expectedTimestamp = new Timestamp(System.currentTimeMillis());
        errorResponse.setTimestamp(expectedTimestamp);

        Timestamp actualTimestamp = errorResponse.getTimestamp();

        assertEquals(expectedTimestamp, actualTimestamp, "O timestamp deve ser igual ao esperado.");
    }

    @Test
    public void testHttpStatus() {
        String expectedHttpStatus = "500 Internal Server Error";
        errorResponse.setHttpStatus(expectedHttpStatus);

        String actualHttpStatus = errorResponse.getHttpStatus();

        assertEquals(expectedHttpStatus, actualHttpStatus, "O HttpStatus deve ser igual ao esperado.");
    }
}
