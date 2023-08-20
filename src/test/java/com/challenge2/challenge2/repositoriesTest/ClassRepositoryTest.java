package com.challenge2.challenge2.repositoriesTest;

import com.challenge2.challenge2.entities.Classes;
import com.challenge2.challenge2.repositories.ClassRepository;
import com.challenge2.challenge2.services.impl.ClassServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@DataJpaTest
@SpringBootTest
public class ClassRepositoryTest {

    //arange
    //act
    //assert
    @Autowired
    private ClassRepository classRepository;

    @Test
    public void ClassRepository_Save_ReturnSavedClass(){
        Classes c = Classes.builder()
                .learningPath("Java")
                .sprint(5).build();

        Classes savedClass = classRepository.save(c);

        assertThat(savedClass).isNotNull();
        assertEquals(c, savedClass);
    }

}
