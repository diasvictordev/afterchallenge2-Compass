package com.challenge2.challenge2.restControllers;

 // import com.challenge2.challenge2.dto.StudentDTO;
import com.challenge2.challenge2.dto.SquadDTO;
import com.challenge2.challenge2.entities.Classes;
import com.challenge2.challenge2.entities.ErrorResponse;
import com.challenge2.challenge2.entities.Squad;
import com.challenge2.challenge2.entities.Student;
import com.challenge2.challenge2.services.impl.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }
/*
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();

    }*/

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        ErrorResponse errorResponse = new ErrorResponse("Nenhum Estudante Encontrado"
                , new Timestamp(System.currentTimeMillis()),HttpStatus.NOT_FOUND.name());
        if(students.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(students);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional <Student> student = studentService.getStudentById(id);
        ErrorResponse errorResponse = new ErrorResponse("Estudante não encontrado"
                , new Timestamp(System.currentTimeMillis()),HttpStatus.NOT_FOUND.name());
        if(student.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(student);
        }
    }
/*
    @PostMapping
    public ResponseEntity addStudent(@RequestBody @Valid Student student) {
        Student savedStudent = studentService.saveStudent(student);
        return new ResponseEntity(savedStudent, HttpStatus.CREATED);
    }*/

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        Student savedStudent = studentService.saveStudent(student);
        ErrorResponse errorResponse = new ErrorResponse("Não foi possível criar o Estudante"
                , new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.name());
        if(savedStudent == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        ErrorResponse errorResponse = new ErrorResponse("Estudante não encontrado na base de dados"
                , new Timestamp(System.currentTimeMillis()),HttpStatus.BAD_REQUEST.name());

        return studentService.getStudentById(id).map(entidade ->{
            studentService.deleteStudent(entidade.getId());
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse));

    }

    @PutMapping
    public ResponseEntity<ErrorResponse> updateStudent(@RequestBody Student student) {
        ErrorResponse errorResponseSucces = new ErrorResponse("Estudante atualizado com sucesso!"
                , new java.sql.Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.name());

        ErrorResponse errorResponseFail = new ErrorResponse("Esse estudante não existe!"
                , new java.sql.Timestamp(System.currentTimeMillis()),HttpStatus.BAD_REQUEST.name());

        return studentService.getStudentById(student.getId()).map(entidade -> {
            studentService.saveStudent(student);
            return new ResponseEntity<>(errorResponseSucces, HttpStatus.OK);
        }).orElseGet(() ->
                new ResponseEntity<>(errorResponseFail, HttpStatus.BAD_REQUEST));
    }
}
