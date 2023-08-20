package com.challenge2.challenge2.serviceTests;


import com.challenge2.challenge2.entities.Student;
import com.challenge2.challenge2.repositories.StudentRepository;
import com.challenge2.challenge2.services.impl.StudentServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStudent() {
        Student mockStudent = new Student();
        mockStudent.setId(1L);
        mockStudent.setName("John Doe");
        mockStudent.setEmail("john.doe@example.com");
        mockStudent.setCity("New York");
        mockStudent.setPassword("Abc@1234");
        mockStudent.setGrade(8.5f);
        mockStudent.setAttendance(90.0f);
        mockStudent.setStartDate(LocalDateTime.now());
        mockStudent.setEndDate(LocalDateTime.now().plusMonths(6));


        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(mockStudent);


        Student savedStudent = studentServiceImpl.saveStudent(mockStudent);


        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getId());


        assertEquals("John Doe", savedStudent.getName());
        assertEquals("john.doe@example.com", savedStudent.getEmail());
        assertEquals("New York", savedStudent.getCity());
        assertEquals(8.5f, savedStudent.getGrade(), 0.01);
        assertEquals(90.0f, savedStudent.getAttendance(), 0.01);
    }

    @Test
    public void testGetStudentById() {
        Long studentId = 1L;
        Student mockStudent = new Student();
        mockStudent.setId(studentId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));

        Optional<Student> optionalStudent = studentServiceImpl.getStudentById(studentId);
        assertTrue(optionalStudent.isPresent());

        Student student = optionalStudent.get();
        assertEquals(studentId, student.getId());
    }




    @Test
    public void testUpdateStudent() {
        Long studentId = 1L;

        Student mockStudent = new Student();
        mockStudent.setId(studentId);
        mockStudent.setName("John Doe");


        when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));


        Optional<Student> optionalStudent = studentServiceImpl.getStudentById(studentId);
        assertNotNull(optionalStudent);


        Student student = optionalStudent.orElseThrow(() -> new IllegalArgumentException("Student not found"));


        String updatedName = "Jane Doe";
        student.setName(updatedName);


        when(studentRepository.save(student)).thenReturn(student);
        Student updatedStudent = studentServiceImpl.saveStudent(student);
        assertNotNull(updatedStudent);
        assertEquals(updatedName, updatedStudent.getName());
    }

    @Test
    public void testDeleteStudent() {
        Long studentId = 1L;

        Student mockStudent = new Student();
        mockStudent.setId(studentId);
        mockStudent.setName("John Doe");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));

        Optional<Student> optionalStudent = studentServiceImpl.getStudentById(studentId);
        assertTrue(optionalStudent.isPresent());

        studentServiceImpl.deleteStudent(studentId);

        verify(studentRepository, times(1)).deleteById(studentId);

        Optional<Student> deletedStudentOptional = studentServiceImpl.getStudentById(studentId);
        assertTrue(deletedStudentOptional.isPresent());
    }

    @Test
    public void testgetAllStudents(){
        Long studentId = 1L;
        Student mockStudent = new Student();
        mockStudent.setId(studentId);
        mockStudent.setName("John Doe");


        when(studentRepository.findAll()).thenReturn(Arrays.asList(mockStudent));


        List<Student> students = studentServiceImpl.getAllStudents();


        assertNotNull(students);
        assertEquals(1, students.size());

        Student retrievedStudent = students.get(0);
        assertEquals(studentId, retrievedStudent.getId());
        assertEquals("John Doe", retrievedStudent.getName());

        verify(studentRepository, times(1)).findAll();
    }



}

