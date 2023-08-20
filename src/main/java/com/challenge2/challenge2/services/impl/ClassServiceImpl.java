package com.challenge2.challenge2.services.impl;

import com.challenge2.challenge2.entities.Classes;
import com.challenge2.challenge2.repositories.ClassRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClassServiceImpl implements ClassService{
    private final ClassRepository classRepository;

    public ClassServiceImpl(ClassRepository classRepository) {

        this.classRepository = classRepository;
    }

    public List<Classes> getAllClasses() {
        return classRepository.findAll();
    }

    @Override
    public Optional<Classes> getClassById(Long id) {
        return classRepository.findById(id);
    }


    public Classes saveClass(Classes c) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Classes>> violations = validator.validate(c);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid entity: " + violations);
        }

        return classRepository.save(c);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }


}