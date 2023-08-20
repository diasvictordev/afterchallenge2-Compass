package com.challenge2.challenge2.controllersTest;

import com.challenge2.challenge2.entities.Classes;
import com.challenge2.challenge2.entities.ErrorResponse;
import com.challenge2.challenge2.entities.Squad;
import com.challenge2.challenge2.entities.Student;
import com.challenge2.challenge2.restControllers.ClassController;
import com.challenge2.challenge2.restControllers.StudentController;
import com.challenge2.challenge2.services.impl.ClassServiceImpl;
import com.challenge2.challenge2.services.impl.StudentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)

public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentServiceImpl studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private StudentController studentController;

    Squad updatedSquad = Squad.builder()
            .id(1L)
            .squadName("TestSquad")
            //.students(new ArrayList<Student>(Collections.singletonList(student)))
            .students(null)
            .build();

    Student updatedStudent = Student.builder()
            .Id(1L)
            .name("TestStudent")
            .email("testStudent@gmail.com")
            .city("TestCity")
            .password("TestPassword1234@#")
            .squad(updatedSquad)
            .college("UFES")
            .attendance(9f)
            .grade(9f)
            .startDate(null)
            .endDate(null)
            .build();

    ErrorResponse errorResponseMap(String responseBody){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
            return errorResponse;
        } catch (IOException e) {
        }
        return null;
    }
        @Test
        public void StudentController_UpdateClass_ReturnSucces() {
            Student updatedStudent = new Student();
            updatedStudent.setId(1L);

            when(studentService.getStudentById(updatedStudent.getId())).thenReturn(Optional.of(updatedStudent));
            when(studentService.saveStudent(updatedStudent)).thenReturn(updatedStudent);

            ResponseEntity<ErrorResponse> responseEntity = studentController.updateStudent(updatedStudent);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            ErrorResponse errorResponse = responseEntity.getBody();
            Assertions.assertNotNull(errorResponse);
            assertEquals("Estudante atualizado com sucesso!", errorResponse.getMessage());
        }

    @Test
    public void StudentController_UpdateClass_ReturnBadRequest() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        // Set other properties

        when(studentService.getStudentById(1L)).thenReturn(Optional.empty());

        ErrorResponse expectedErrorResponse = new ErrorResponse(
                "Esse estudante não existe!",
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST.name());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/student")
                        .content(objectMapper.writeValueAsString(updatedStudent))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).saveStudent(updatedStudent);
    }

    @Test
    public void StudentController_GetStudentByID_ReturnStudentNotFound() {
        Long id = 1L;

        when(studentService.getStudentById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = studentController.getStudentById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals("Estudante não encontrado", errorResponse.getMessage());
    }

    @Test
    public void StudentController_GetAllStudents_ReturnAllStudents() throws Exception {
        List<Student> studentList = new ArrayList<>();
        studentList.add(updatedStudent);

        when(studentService.getAllStudents()).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("TestStudent"))
                .andExpect(jsonPath("$[0].email").value("testStudent@gmail.com"))
                .andExpect(jsonPath("$[0].city").value("TestCity"))
                .andExpect(jsonPath("$[0].password").value("TestPassword1234@#"))
                .andExpect(jsonPath("$[0].college").value("UFES"))
                .andExpect(jsonPath("$[0].attendance").value(9.0))
                .andExpect(jsonPath("$[0].grade").value(9.0))
                .andExpect(jsonPath("$[0].startDate").value(updatedStudent.getStartDate()))
                .andExpect(jsonPath("$[0].endDate").value(updatedStudent.getEndDate()));
    }

    @Test
    public void StudentController_GetStudentByID_ReturnStudent() throws Exception {

        when(studentService.getStudentById(updatedStudent.getId())).thenReturn(Optional.ofNullable(updatedStudent));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/{id}", updatedStudent.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("TestStudent"))
                .andExpect(jsonPath("$.email").value("testStudent@gmail.com"))
                .andExpect(jsonPath("$.city").value("TestCity"))
                .andExpect(jsonPath("$.password").value("TestPassword1234@#"))
                .andExpect(jsonPath("$.college").value("UFES"))
                .andExpect(jsonPath("$.attendance").value(9.0))
                .andExpect(jsonPath("$.grade").value(9.0))
                .andExpect(jsonPath("$.startDate").value(updatedStudent.getStartDate()))
                .andExpect(jsonPath("$.endDate").value(updatedStudent.getEndDate()));
    }

    @Test
    public void StudentController_PostStudentByID_ReturnStudent() throws Exception {

        when(studentService.saveStudent(any(Student.class))).thenReturn(updatedStudent);

        ResponseEntity<?> responseEntity = studentController.addStudent(updatedStudent);
        Student returnedStudent = (Student) responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assert returnedStudent != null;
        assertEquals(updatedStudent.getName(), returnedStudent.getName());
        assertEquals(updatedStudent.getEmail(), returnedStudent.getEmail());
        assertEquals(updatedStudent.getCity(), returnedStudent.getCity());
        assertEquals(updatedStudent.getPassword(), returnedStudent.getPassword());
        assertEquals(updatedStudent.getCollege(), returnedStudent.getCollege());
        assertEquals(Optional.of(updatedStudent.getAttendance()) , Optional.ofNullable(returnedStudent.getAttendance()));
        assertEquals(Optional.of(updatedStudent.getGrade()) , Optional.ofNullable(returnedStudent.getGrade()));
        assertEquals(updatedStudent.getStartDate() , returnedStudent.getStartDate());
        assertEquals(updatedStudent.getEndDate() , returnedStudent.getEndDate());
    }

    @Test
    public void StudentController_AddStudentByID_ReturnBadRequest() throws Exception {
        when(studentService.saveStudent(any(Student.class))).thenReturn(null);
        ResponseEntity<?> responseEntity = studentController.addStudent(updatedStudent);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void StudentController_DeleteStudentByID_ReturnSucces() throws Exception {
        when(studentService.getStudentById(updatedStudent.getId())).thenReturn(Optional.ofNullable(updatedStudent));
        ResponseEntity<?> responseEntity = studentController.deleteStudent(updatedStudent.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(studentService).deleteStudent(updatedStudent.getId());
    }

    @Test
    public void StudentController_DeleteStudentByID_ReturnBadRequest() {

        when(studentService.getStudentById(updatedStudent.getId())).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = studentController.deleteStudent(updatedStudent.getId());

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        Assertions.assertNotNull(errorResponse);
        assertEquals("Estudante não encontrado na base de dados", errorResponse.getMessage());
    }
}