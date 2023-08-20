package com.challenge2.challenge2.serviceTests;


import com.challenge2.challenge2.entities.Classes;
import com.challenge2.challenge2.repositories.ClassRepository;
import com.challenge2.challenge2.services.impl.ClassServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClassServiceTest {

    @Mock
    private ClassRepository classRepository;
    @InjectMocks
    private ClassServiceImpl classServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateClass(){
        Classes newClass = new Classes();
        newClass.setId(1L);
        newClass.setLearningPath("AWS Spring");
        newClass.setSprint(2);


        when(classRepository.save(any(Classes.class))).thenReturn(newClass);


        Classes savedClass = classServiceImpl.saveClass(newClass);

        assertNotNull(savedClass);
        assertNotNull(savedClass.getId());
        assertEquals("AWS Spring", savedClass.getLearningPath());
        assertEquals(2, savedClass.getSprint());
        Mockito.verify(classRepository, times(1)).save(any(Classes.class));
    }

    @Test
    public void testGetClassById(){
        Long classId = 1L;
        Classes mockClass = new Classes();
        mockClass.setId(classId);
        when(classRepository.findById(classId)).thenReturn(Optional.of(mockClass));

        Optional<Classes> optionalClass = classServiceImpl.getClassById(classId);
        assertTrue(optionalClass.isPresent());

        Classes classes = optionalClass.get();
        assertEquals(classId, classes.getId());
    }

    @Test
    public void testUpdateClass() {
        Long classId = 1L;

        Classes mockClass = new Classes();
        mockClass.setId(classId);
        mockClass.setLearningPath("AWS Spring");
        mockClass.setSprint(2);


        when(classRepository.findById(classId)).thenReturn(Optional.of(mockClass));


        Optional<Classes> optionalClass = classServiceImpl.getClassById(classId);
        assertNotNull(optionalClass);


        Classes classes = optionalClass.orElseThrow(() -> new IllegalArgumentException("Class not found"));


        String updatedLearningPath = "Aws React";
        classes.setLearningPath(updatedLearningPath);


        when(classRepository.save(classes)).thenReturn(classes);
        Classes updatedClass = classServiceImpl.saveClass(classes);
        assertNotNull(updatedClass);
        assertEquals(updatedLearningPath, updatedClass.getLearningPath());
    }

    @Test
    public void testDeleteClass() {
        Long classId = 1L;

        Classes mockClass = new Classes();
        mockClass.setId(classId);
        mockClass.setLearningPath("AWS Spring");

        when(classRepository.findById(classId)).thenReturn(Optional.of(mockClass));

        Optional<Classes> optionalClass = classServiceImpl.getClassById(classId);
        assertTrue(optionalClass.isPresent());

        classServiceImpl.deleteClass(classId);

        verify(classRepository, times(1)).deleteById(classId);

        Optional<Classes> deletedClassOptional = classServiceImpl.getClassById(classId);
        assertTrue(deletedClassOptional.isPresent());
    }

    @Test
    public void testgetAllClasses(){
        Classes class1 = new Classes();
        class1.setId(1L);
        class1.setLearningPath("AWS Spring");
        class1.setSprint(2);

        Classes class2 = new Classes();
        class2.setId(2L);
        class2.setLearningPath("React Native");
        class2.setSprint(3);

        List<Classes> classList = new ArrayList<>();
        classList.add(class1);
        classList.add(class2);

        when(classRepository.findAll()).thenReturn(classList);


        List<Classes> result = classServiceImpl.getAllClasses();


        verify(classRepository, times(1)).findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("AWS Spring", result.get(0).getLearningPath());
        assertEquals(2, result.get(0).getSprint());
        assertEquals("React Native", result.get(1).getLearningPath());
        assertEquals(3, result.get(1).getSprint());
    }


}
