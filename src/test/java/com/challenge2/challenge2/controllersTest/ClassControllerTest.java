package com.challenge2.challenge2.controllersTest;

import com.challenge2.challenge2.dto.SquadDTO;
import com.challenge2.challenge2.entities.*;
import com.challenge2.challenge2.restControllers.ClassController;
import com.challenge2.challenge2.services.impl.ClassServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClassController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClassControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassServiceImpl classService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private ClassController classController;
    private Classes classes;


    @Test
    public void testUpdateClassWhenClassExists() {
        Long id = 1L;

        Classes existingClass = new Classes();
        existingClass.setId(id);

        when(classService.getClassById(id)).thenReturn(Optional.of(existingClass));

        Classes updatedClass = Classes.builder()
                .id(id)
                .learningPath("TurminhaDoTeste")
                .sprint(2).build();

        when(classService.saveClass(any(Classes.class))).thenReturn(updatedClass);

        ResponseEntity<?> responseEntity = classController.updateClass(updatedClass);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        Assertions.assertNotNull(errorResponse);
        assertEquals("Turma atualizada com sucesso", errorResponse.getMessage());
    }

    @Test
    public void testAddClass() {
        Classes classToAdd = Classes.builder()
                .id(1L)
                .learningPath("TestClass")
                .sprint(10)
                .build();

        when(classService.saveClass(any(Classes.class))).thenReturn(classToAdd);



        ResponseEntity<?> responseEntity = classController.addClass(classToAdd);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        Classes savedClass = (Classes) responseEntity.getBody();
        assertThat(savedClass).isNotNull();
        assertEquals("TestClass", savedClass.getLearningPath());
        assertEquals(10, savedClass.getSprint());

    }


    @Test
    public void ClassController_GetAllClasses_ReturnEmptyException() throws Exception {
        when(classService.getAllClasses()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/classes"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Nenhuma turma encontrada"));
    }

    @Test
    public void ClassController_GetAllClasses_ReturnAllClasses() throws Exception {
        List<Classes> classesList = new ArrayList<>();
        classesList.add(new Classes(1L, "Java", 1));
        classesList.add(new Classes(2L, "AWS", 2));
        when(classService.getAllClasses()).thenReturn(classesList);

        mockMvc.perform(get("/api/classes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].learningPath").value("Java"))
                .andExpect(jsonPath("$[0].sprint").value(1));
    }

    @Test
    public void ClassController_GetClassByID_ReturnClassNotFound() {
        Long id = 1L;

        when(classService.getClassById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = classController.getClassById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals("Turma não encontrada", errorResponse.getMessage());
    }

    @Test
    public void ClassController_GetClassById_ReturnClassById() {
        Long id = 1L;

        Classes classFound = Classes.builder().learningPath("AWS").sprint(2).build();
        Optional <Classes> classOptional = Optional.of(classFound);
        when(classService.getClassById(id)).thenReturn(classOptional);

        ResponseEntity<?> responseEntity = classController.getClassById(id);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Optional<Classes> resultClassOptional = (Optional<Classes>) responseEntity.getBody();
        assert Objects.requireNonNull(resultClassOptional).isPresent();
        Classes resultClass = resultClassOptional.get();
        Assertions.assertNotNull(resultClass);
        assertEquals("AWS", resultClass.getLearningPath());
        assertEquals(2, resultClass.getSprint());
    }

    @Test
    public void ClassController_DeleteClass_ReturnNoContent() {
        Long id = 1L;


        Classes classFound = Classes.builder().build();
        when(classService.getClassById(id)).thenReturn(Optional.ofNullable(classFound));

        ResponseEntity<?> responseEntity = classController.deleteClass(id);


        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(classService, times(1)).deleteClass(id);
    }

    @Test
    public void ClassController_DeleteClass_ReturnBadRequest() {
        Long id = 1L;

        when(classService.getClassById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = classController.deleteClass(id);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(classService, never()).deleteClass(id);
    }

    }