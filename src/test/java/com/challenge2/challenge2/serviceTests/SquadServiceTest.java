package com.challenge2.challenge2.serviceTests;

import com.challenge2.challenge2.dto.SquadDTO;
import com.challenge2.challenge2.entities.Squad;
import com.challenge2.challenge2.entities.Student;
import com.challenge2.challenge2.repositories.SquadRepository;
import com.challenge2.challenge2.repositories.StudentRepository;
import com.challenge2.challenge2.services.impl.SquadServiceImpl;
import com.challenge2.challenge2.services.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SquadServiceTest {

    @Mock
    private SquadRepository squadRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SquadDTO squadDTO;
    @InjectMocks
    private SquadServiceImpl squadServiceImpl;
    private StudentServiceImpl studentServiceImpl;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        squadServiceImpl = new SquadServiceImpl(squadRepository, studentRepository);
    }

    @Test
    public void testCreateSquad() {
        Squad mockSquad = new Squad();
        mockSquad.setId(1L);
        mockSquad.setSquadName("Spring Force");



        when(squadRepository.save(Mockito.any(Squad.class))).thenReturn(mockSquad);


        Squad savedSquad = squadServiceImpl.saveSquad(mockSquad);


        assertNotNull(savedSquad);
        assertNotNull(savedSquad.getId());


        assertEquals("Spring Force", savedSquad.getSquadName());
    }

    @Test
    public void testGetSquadById() {
        Long SquadId = 1L;
        Squad mockSquad = new Squad();
        mockSquad.setId(SquadId);
        when(squadRepository.findById(SquadId)).thenReturn(Optional.of(mockSquad));

        Optional<Squad> optionalSquad = squadServiceImpl.getSquadById(SquadId);
        assertTrue(optionalSquad.isPresent());

        Squad Squad = optionalSquad.get();
        assertEquals(SquadId, Squad.getId());
    }




    @Test
    public void testUpdateSquad() {
        Long SquadId = 1L;

        Squad mockSquad = new Squad();
        mockSquad.setId(SquadId);
        mockSquad.setSquadName("ByteAnt");


        when(squadRepository.findById(SquadId)).thenReturn(Optional.of(mockSquad));


        Optional<Squad> optionalSquad = squadServiceImpl.getSquadById(SquadId);
        assertNotNull(optionalSquad);


        Squad Squad = optionalSquad.orElseThrow(() -> new IllegalArgumentException("Squad not found"));


        String updatedName = "Spring Force";
        Squad.setSquadName(updatedName);


        when(squadRepository.save(Squad)).thenReturn(Squad);
        Squad updatedSquad = squadServiceImpl.saveSquad(Squad);
        assertNotNull(updatedSquad);
        assertEquals(updatedName, updatedSquad.getSquadName());
    }

    @Test
    public void testDeleteSquad() {
        Long SquadId = 1L;

        Squad mockSquad = new Squad();
        mockSquad.setId(SquadId);
        mockSquad.setSquadName("Spring Force");

        when(squadRepository.findById(SquadId)).thenReturn(Optional.of(mockSquad));

        Optional<Squad> optionalSquad = squadServiceImpl.getSquadById(SquadId);
        assertTrue(optionalSquad.isPresent());

        squadServiceImpl.deleteSquad(SquadId);

        verify(squadRepository, times(1)).deleteById(SquadId);

        Optional<Squad> deletedSquadOptional = squadServiceImpl.getSquadById(SquadId);
        assertTrue(deletedSquadOptional.isPresent());
    }

    @Test
    public void testGetAllSquads(){
        Long squadId1 = 1L;
        Squad mockSquad1 = new Squad();
        mockSquad1.setId(squadId1);
        mockSquad1.setSquadName("Squad 1");

        Long squadId2 = 2L;
        Squad mockSquad2 = new Squad();
        mockSquad2.setId(squadId2);
        mockSquad2.setSquadName("Squad 2");

        List<Squad> squadList = new ArrayList<>();
        squadList.add(mockSquad1);
        squadList.add(mockSquad2);


        when(squadRepository.findAll()).thenReturn(squadList);


        List<Squad> result = squadServiceImpl.getAllSquads();


        verify(squadRepository, times(1)).findAll();

        assertNotNull(result);
        assertEquals(2, result.size());


        Squad resultSquad1 = result.get(0);
        assertEquals(squadId1, resultSquad1.getId());
        assertEquals("Squad 1", resultSquad1.getSquadName());

        Squad resultSquad2 = result.get(1);
        assertEquals(squadId2, resultSquad2.getId());
        assertEquals("Squad 2", resultSquad2.getSquadName());
    }

    @Test
    public void testCreateSquadWithStudents() {

        Student mockStudent1 = new Student();
        mockStudent1.setId(1L);
        mockStudent1.setName("John Doe");
        Student mockStudent2 = new Student();
        mockStudent2.setId(2L);
        mockStudent2.setName("Jane Smith");
        Student mockStudent3 = new Student();
        mockStudent3.setId(3L);
        mockStudent3.setName("Bob Johnson");


        SquadDTO squadDTO = new SquadDTO();
        squadDTO.setSquadName("Example Squad");
        List<Long> studentIds = Arrays.asList(1L, 2L, 3L);
        squadDTO.setStudents(studentIds);


        // Mock studentRepository behavior
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.existsById(2L)).thenReturn(true);
        when(studentRepository.existsById(3L)).thenReturn(true);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent1));
        when(studentRepository.findById(2L)).thenReturn(Optional.of(mockStudent2));
        when(studentRepository.findById(3L)).thenReturn(Optional.of(mockStudent3));

        // Mock Squad object
        Squad mockSquad = new Squad();
        mockSquad.setId(1L);
        mockSquad.setSquadName("Example Squad");
        List<Student> squadStudents = Arrays.asList(mockStudent1, mockStudent2, mockStudent3);
        mockSquad.setStudents(squadStudents);


        when(squadRepository.save(any(Squad.class))).thenReturn(mockSquad);


        Squad createdSquad = squadServiceImpl.createSquadWithStudents(squadDTO);

        assertNotNull(createdSquad);
        assertEquals("Example Squad", createdSquad.getSquadName());
        assertEquals(3, createdSquad.getStudents().size());
        assertTrue(createdSquad.getStudents().contains(mockStudent1));
        assertTrue(createdSquad.getStudents().contains(mockStudent2));
        assertTrue(createdSquad.getStudents().contains(mockStudent3));


        verify(squadRepository, times(1)).save(any(Squad.class));
    }

}