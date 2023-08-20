package com.challenge2.challenge2.controllersTest;

import com.challenge2.challenge2.entities.ErrorResponse;
import com.challenge2.challenge2.entities.Organizer;
import com.challenge2.challenge2.entities.Student;
import com.challenge2.challenge2.enums.OrganizerEnums;
import com.challenge2.challenge2.restControllers.OrganizerController;
import com.challenge2.challenge2.services.impl.OrganizerServiceImpl;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OrganizerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrganizerServiceImpl organizerService;

    @InjectMocks
    private OrganizerController organizerController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
    }

    private final Organizer organizer = Organizer.builder()
            .Id(1L)
            .name("TestOrganizer")
            .email("testorganizer@gmail.com")
            .password("TestPassword1234@#")
            .city("TestCity")
            .role(OrganizerEnums.Coordinator)
            .build();


    @Test
    public void OrganizerController_UpdateOrganizer_ReturnSucces() {
        Organizer updatedOrganizer = new Organizer();
        updatedOrganizer.setId(1L);
        updatedOrganizer.setRole(OrganizerEnums.Coordinator);

        when(organizerService.getOrganizerById(any())).thenReturn(Optional.of(updatedOrganizer));
        when(organizerService.saveOrganizer(any())).thenReturn(Optional.ofNullable(organizer));

        ResponseEntity<ErrorResponse> responseEntity = organizerController.updateOrganizer(updatedOrganizer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        Assertions.assertNotNull(errorResponse);
        assertEquals("Organizador atualizado com sucesso!", errorResponse.getMessage());
    }

    @Test
    public void OrganizerController_UpdateOrganizer_ReturnBadRequest() throws Exception {
        Organizer updatedOrganizer = new Organizer();
        updatedOrganizer.setId(1L);
        updatedOrganizer.setRole(OrganizerEnums.Coordinator);

        when(organizerService.getOrganizerById(1L)).thenReturn(Optional.empty());

        ErrorResponse expectedErrorResponse = new ErrorResponse(
                "Não foi possível atualizar o organizador",
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST.name());

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/organizer")
                        .content(objectMapper.writeValueAsString(updatedOrganizer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(organizerService, never()).saveOrganizer(updatedOrganizer);
    }

    @Test
    public void OrganizerController_AddOrganizer_ReturnCreatedOrganizer() throws Exception {
        Organizer tempOrganizer = Organizer.builder()
                .Id(1L)
                .name("TestOrganizer")
                .email("testorganizer@gmail.com")
                .password("TestPassword1234@#")
                .city("TestCity")
                .role(OrganizerEnums.Coordinator)
                .build();

        when(organizerService.saveOrganizer(any(Organizer.class))).thenReturn(Optional.of(tempOrganizer));

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizer")
                        .content(objectMapper.writeValueAsString(tempOrganizer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("TestOrganizer"));



        verify(organizerService, times(1)).saveOrganizer(tempOrganizer);
    }

    @Test
    public void OrganizerController_AddOrganizer_ReturnBadRequest() throws Exception {
        Organizer tempOrganizer = Organizer.builder()
                .Id(1L)
                .name("TestOrganizer")
                .email("testorganizer@gmail.com")
                .password("TestPassword1234@#")
                .city("TestCity")
                .role(OrganizerEnums.Coordinator)
                .build();

        when(organizerService.saveOrganizer(any(Organizer.class))).thenReturn(Optional.empty());

        ErrorResponse expectedErrorResponse = new ErrorResponse("Não foi possível criar o organizador",
                new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.name());

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizer")
                        .content(objectMapper.writeValueAsString(tempOrganizer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
                //.andExpect(content().json(objectMapper.writeValueAsString(expectedErrorResponse)));

        verify(organizerService, times(1)).saveOrganizer(tempOrganizer);
    }

    @Test
    public void OrganizerController_DeleteOrganizerByID_ReturnSucces() throws Exception {
        when(organizerService.getOrganizerById(organizer.getId())).thenReturn(Optional.of(organizer));
        ResponseEntity<?> responseEntity = organizerController.deleteOrganizer(organizer.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(organizerService).deleteOrganizer(organizer.getId());
    }

    @Test
    public void StudentController_DeleteStudentByID_ReturnBadRequest() {

        when(organizerService.getOrganizerById(organizer.getId())).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = organizerController.deleteOrganizer(organizer.getId());

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        Assertions.assertNotNull(errorResponse);
        assertEquals("Não foi possível deletar o organizador pois ele não existe", errorResponse.getMessage());
    }

    @Test
    public void OrganizerController_GetOrganizerByID_ReturnOrganizer() throws Exception {

        when(organizerService.getOrganizerById(organizer.getId())).thenReturn(Optional.ofNullable(organizer));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizer/{id}", organizer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(organizer.getName()))
                .andExpect(jsonPath("$.email").value(organizer.getEmail()))
                .andExpect(jsonPath("$.city").value(organizer.getCity()))
                .andExpect(jsonPath("$.password").value(organizer.getPassword()))
                .andExpect(jsonPath("$.role").value(organizer.getRole().toString()));

    }

    @Test
    public void OrganizerController_GetOrganizerByID_ReturnOrganizerNotFound() {
        Long id = 1L;

        when(organizerService.getOrganizerById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = organizerController.getOrganizerById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        Assertions.assertNotNull(errorResponse);
        assertEquals(((ErrorResponse) responseEntity.getBody()).getMessage(), errorResponse.getMessage());
    }

        @Test
        public void OrganizerController_GetAllOrganizers_ReturnAllOrganizers() throws Exception {
            List<Organizer> organizerList = new ArrayList<>();
            organizerList.add(organizer);

            when(organizerService.getAllOrganizers()).thenReturn(organizerList);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/organizer"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].name").value(organizer.getName()))
                    .andExpect(jsonPath("$[0].email").value(organizer.getEmail()))
                    .andExpect(jsonPath("$[0].city").value(organizer.getCity()))
                    .andExpect(jsonPath("$[0].password").value(organizer.getPassword()))
                    .andExpect(jsonPath("$[0].role").value(organizer.getRole().toString()));
        }

    @Test
    public void OrganizerController_GetAllOrganizers_ReturnNotFound() throws Exception {
        when(organizerService.getAllOrganizers()).thenReturn(Collections.emptyList());

        ErrorResponse expectedErrorResponse = new ErrorResponse(
                "Nenhum organizador encontrado",
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.NOT_FOUND.name());

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizer"))
                .andExpect(status().isNotFound());
    }
    }