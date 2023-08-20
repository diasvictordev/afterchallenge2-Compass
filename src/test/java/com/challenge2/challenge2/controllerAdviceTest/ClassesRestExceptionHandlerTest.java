package com.challenge2.challenge2.controllerAdviceTest;

import com.challenge2.challenge2.entities.ErrorResponse;
import com.challenge2.challenge2.exceptions.BadRequestException;
import com.challenge2.challenge2.exceptions.NotFoundException;
import com.challenge2.challenge2.restControllers.controllersAdvices.ClassesRestExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClassesRestExceptionHandlerTest {

    @InjectMocks
    private ClassesRestExceptionHandler classesRestExceptionHandler;

    @Test
    public void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Handle Illegal Argument Exception");

        ResponseEntity<ErrorResponse> responseEntity = classesRestExceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals("Illegal Argument Exception: Handle Illegal Argument Exception", errorResponse.getMessage());

        assertEquals(HttpStatus.BAD_REQUEST.name(), errorResponse.getHttpStatus());
    }

    @Test
    public void testHandleNotFoundException(){
        NotFoundException exception = new NotFoundException("Handle Not Found Exception");

        ResponseEntity<ErrorResponse> responseEntity = classesRestExceptionHandler.handleNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals(exception.getMessage(), errorResponse.getMessage());

        assertEquals(HttpStatus.NOT_FOUND.name(), errorResponse.getHttpStatus());
    }

    @Test
    public void testHandleGlobalException(){
        Exception exception = new Exception("Handle global exception");

        ResponseEntity<ErrorResponse> responseEntity = classesRestExceptionHandler.globalExceptionHandler(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals("Something went wrong: " + exception.getMessage(), errorResponse.getMessage());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), errorResponse.getHttpStatus());
    }

    @Test
    public void testHandleBadRequestException(){
        BadRequestException exception = new BadRequestException("Handle Bad Request Exception");

        ResponseEntity<ErrorResponse> responseEntity = classesRestExceptionHandler.handleBadRequestException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals(exception.getMessage(), errorResponse.getMessage());

        assertEquals(HttpStatus.BAD_REQUEST.name(), errorResponse.getHttpStatus());
    }
}
