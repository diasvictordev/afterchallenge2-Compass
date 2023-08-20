package com.challenge2.challenge2.services.impl;

import com.challenge2.challenge2.entities.Student;
import com.challenge2.challenge2.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private static StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        StudentServiceImpl.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
            return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {

        studentRepository.deleteById(id);
    }

    /*public boolean existsById(Long studentId) {
        return studentRepository.existsById(studentId);
    }*/
}
