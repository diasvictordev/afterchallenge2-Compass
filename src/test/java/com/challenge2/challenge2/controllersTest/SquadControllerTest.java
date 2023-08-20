package com.challenge2.challenge2.controllersTest;

import com.challenge2.challenge2.dto.SquadDTO;
import com.challenge2.challenge2.entities.ErrorResponse;
import com.challenge2.challenge2.entities.Squad;
import com.challenge2.challenge2.entities.Student;
import com.challenge2.challenge2.repositories.SquadRepository;
import com.challenge2.challenge2.repositories.StudentRepository;
import com.challenge2.challenge2.restControllers.SquadController;
import com.challenge2.challenge2.services.impl.SquadServiceImpl;
import com.challenge2.challenge2.services.impl.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SquadControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SquadServiceImpl squadService;


    @InjectMocks
    private SquadController squadController;

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(squadController).build();
        updatedSquad.setStudents(new ArrayList<Student>(Collections.singletonList(student)));
    }

    Squad updatedSquad = Squad.builder()
            .id(1L)
            .squadName("TestSquad")
            //.students(new ArrayList<Student>(Collections.singletonList(student)))
            .students(null)
            .build();

    Student student = Student.builder()
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


    @Test
    public void SquadController_UpdateClass_ReturnSucces() {

        Long id = 1L;

        Squad existingSquad = new Squad();
        existingSquad.setId(id);

        when(squadService.getSquadById(id)).thenReturn(Optional.of(existingSquad));


        when(squadService.saveSquad(any(Squad.class))).thenReturn(updatedSquad);

        ResponseEntity<?> responseEntity = squadController.updateSquad(updatedSquad);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        Assertions.assertNotNull(errorResponse);
        assertEquals("Squad atualizada com sucesso!", errorResponse.getMessage());
    }


    @Test
    public void SquadController_GetSquad_ReturnSquadNotFound() {
        Long id = 1L;

        when(squadService.getSquadById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = squadController.getSquadById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals("Squad não encontrado", errorResponse.getMessage());
    }

    @Test
    public void SquadController_GetAllSquads_ReturnNotFound() throws Exception {
        List<Squad> squadList = Collections.emptyList();

        when(squadService.getAllSquads()).thenReturn(squadList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/squad"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Nenhum Squad encontrado"))
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"));
    }

    @Test
    public void SquadController_addSquad_ReturnsCreatedSquad() throws Exception {
        SquadDTO squadDTO = SquadDTO.builder()
                .squadName("TestSquad")
                .students(new ArrayList<Long>(Collections.singletonList(1L)))
                .build();

        when(squadService.createSquadWithStudents(squadDTO)).thenReturn(updatedSquad);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/squad")
                        .content(objectMapper.writeValueAsString(squadDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedSquad)));

        verify(squadService, times(1)).createSquadWithStudents(squadDTO);
    }

    @Test
    public void SquadController_DeleteSquad_ReturnBadRequest() {
        Long id = 1L;

        when(squadService.getSquadById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = squadController.deleteSquad(id);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(squadService, never()).deleteSquad(id);
    }

    @Test
    public void SquadController_DeleteSquad_ReturnNoContent() {
        Long id = 1L;


        Squad SquadFound = Squad.builder().build();
        when(squadService.getSquadById(id)).thenReturn(Optional.ofNullable(SquadFound));

        ResponseEntity<?> responseEntity = squadController.deleteSquad(id);


        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        //verify(squadService, times(1)).deleteSquad(id);
    }

}