package com.challenge2.challenge2.serviceTests;


import com.challenge2.challenge2.entities.Organizer;
import com.challenge2.challenge2.enums.OrganizerEnums;
import com.challenge2.challenge2.repositories.OrganizerRepository;
import com.challenge2.challenge2.services.impl.OrganizerServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.challenge2.challenge2.enums.OrganizerEnums.Instructor;
import static com.challenge2.challenge2.enums.OrganizerEnums.ScrumMaster;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrganizerServiceTest {

    @Mock
    private OrganizerRepository organizerRepository;
    @InjectMocks
    private OrganizerServiceImpl organizerServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrganizer(){
        Organizer newOrganizer = new Organizer();
        newOrganizer.setId(1L);
        newOrganizer.setName("Zezinho Scrum Master");
        newOrganizer.setRole(ScrumMaster);


        Optional<Organizer> optionalOrganizer = Optional.of(newOrganizer);
        when(organizerRepository.save(any(Organizer.class))).thenReturn(newOrganizer);

        Optional<Organizer> savedOrganizer = organizerServiceImpl.saveOrganizer(newOrganizer);

        assertNotNull(savedOrganizer);
        assertTrue(savedOrganizer.isPresent());
        assertNotNull(savedOrganizer.get().getId());
        assertEquals("Zezinho Scrum Master", savedOrganizer.get().getName());
        assertEquals(ScrumMaster, savedOrganizer.get().getRole());
        Mockito.verify(organizerRepository, times(1)).save(any(Organizer.class));
    }

    @Test
    public void testGetOrganizerById() {
        Long organizerId = 1L;
        Organizer mockOrganizer = new Organizer();
        mockOrganizer.setId(organizerId);
        when(organizerRepository.findById(organizerId)).thenReturn(Optional.of(mockOrganizer));

        Optional<Organizer> optionalOrganizer = organizerServiceImpl.getOrganizerById(organizerId);
        assertTrue(optionalOrganizer.isPresent());

        Organizer Organizer = optionalOrganizer.get();
        assertEquals(organizerId, Organizer.getId());
    }

    @Test
    public void testUpdateOrganizer() {
        Long organizerId = 1L;

        Organizer mockOrganizer = new Organizer();
        mockOrganizer.setId(organizerId);
        mockOrganizer.setName("John Doe");
        mockOrganizer.setRole(Instructor);


        when(organizerRepository.findById(organizerId)).thenReturn(Optional.of(mockOrganizer));


        Optional<Organizer> optionalOrganizer = organizerServiceImpl.getOrganizerById(organizerId);
        assertNotNull(optionalOrganizer);


        Organizer organizer = optionalOrganizer.orElseThrow(() -> new IllegalArgumentException("Organizer not found"));


        String updatedName = "Jane Doe";
        organizer.setName(updatedName);


        when(organizerRepository.save(organizer)).thenReturn(organizer);
        Optional<Organizer> updatedOrganizer = organizerServiceImpl.saveOrganizer(organizer);
        assertNotNull(updatedOrganizer);
        assertTrue(updatedOrganizer.isPresent());
        assertEquals(updatedName, updatedOrganizer.get().getName());
    }

    @Test
    public void testDeleteOrganizer() {
        Long organizerId = 1L;

        Organizer mockOrganizer = new Organizer();
        mockOrganizer.setId(organizerId);
        mockOrganizer.setName("John Doe");

        when(organizerRepository.findById(organizerId)).thenReturn(Optional.of(mockOrganizer));

        Optional<Organizer> optionalOrganizer = organizerServiceImpl.getOrganizerById(organizerId);
        assertTrue(optionalOrganizer.isPresent());

        organizerServiceImpl.deleteOrganizer(organizerId);

        verify(organizerRepository, times(1)).deleteById(organizerId);

        Optional<Organizer> deletedOrganizerOptional = organizerServiceImpl.getOrganizerById(organizerId);
        assertTrue(deletedOrganizerOptional.isPresent());
    }


    @Test
    public void testGetAllOrganizers(){
        Long organizerId = 1L;
        Organizer mockOrganizer = new Organizer();
        mockOrganizer.setId(organizerId);
        mockOrganizer.setName("Victor Renan");
        mockOrganizer.setRole(OrganizerEnums.Instructor);

        List<Organizer> organizerList = new ArrayList<>();
        organizerList.add(mockOrganizer);


        when(organizerRepository.findAll()).thenReturn(organizerList);


        List<Organizer> result = organizerServiceImpl.getAllOrganizers();


        verify(organizerRepository, times(1)).findAll();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(organizerId, result.get(0).getId());
        assertEquals("Victor Renan", result.get(0).getName());
        assertEquals(OrganizerEnums.Instructor, result.get(0).getRole());
    }



}
