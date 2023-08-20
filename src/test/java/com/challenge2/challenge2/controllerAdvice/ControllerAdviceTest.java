package com.challenge2.challenge2.controllerAdvice;
import com.challenge2.challenge2.entities.ErrorResponse;
import com.challenge2.challenge2.exceptions.NotFoundException;
import com.challenge2.challenge2.restControllers.controllersAdvices.ClassesRestExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.sql.Timestamp;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc(addFilters = false)
public class ControllerAdviceTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        ClassesRestExceptionHandler controllerAdvice = new ClassesRestExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(controllerAdvice)
                .setControllerAdvice(controllerAdvice)
                .build();
    }
/*
    @Test
    public void testHandleNotFoundException_ReturnsNotFoundErrorResponse() throws Exception {
        String exceptionMessage = "Recurso não encontrado";
        NotFoundException notFoundException = new NotFoundException(exceptionMessage);

        ErrorResponse expectedErrorResponse = new ErrorResponse(
                exceptionMessage,
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.NOT_FOUND.name());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/some-route-that-triggers-exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(exceptionMessage))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()));

        verifyNoInteractions(any());  // Make sure no other interactions occurred
    }*/
}
