package com.challenge2.challenge2.services.impl;

import java.util.List;
import java.util.Optional;

import com.challenge2.challenge2.entities.Classes;
import com.challenge2.challenge2.repositories.ClassRepository;

public interface ClassService {

    List<Classes> getAllClasses(); 

    Optional<Classes> getClassById(Long id);
    
    Classes saveClass(Classes classes);

    void deleteClass(Long id);
}
