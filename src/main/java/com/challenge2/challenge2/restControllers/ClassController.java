package com.challenge2.challenge2.restControllers;

import java.sql.Timestamp;
import java.util.Optional;
import com.challenge2.challenge2.entities.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge2.challenge2.entities.Classes;
import com.challenge2.challenge2.services.impl.ClassServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private ClassServiceImpl classService;

    public ClassController(ClassServiceImpl classService){
        this.classService = classService;
    }

    @GetMapping
    public ResponseEntity<?> getAllClasses(){
        ErrorResponse errorResponse = new ErrorResponse("Nenhuma turma encontrada"
                , new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.name());
        if(classService.getAllClasses().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(classService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClassById(@PathVariable Long id){
        ErrorResponse errorResponse = new ErrorResponse("Turma não encontrada"
                , new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.name());
        Optional<Classes> classes = classService.getClassById(id);
        if(classes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(classes);
    }
    
    @PostMapping
    public ResponseEntity<?> addClass(@RequestBody Classes classes){
        Classes savedClass = classService.saveClass(classes);
        ErrorResponse errorResponse = new ErrorResponse("Não foi possível criar a turma"
                , new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.name());
        if(savedClass == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClass);
    }
/*
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) {
        ErrorResponse errorResponse = new ErrorResponse("Não foi possível deletar a turma pois ela não existe"
                , new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.name());
        return classService.getClassById(id)
                .map(entidade -> {
                    classService.deleteClass(entidade.getId());
                    return new ResponseEntity<>( HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse));
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) {
        Optional<Classes> optionalClass = classService.getClassById(id);
        ErrorResponse errorResponse = new ErrorResponse("Não foi possível deletar a turma pois ela não existe"
                , new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.name());
        if (optionalClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        classService.deleteClass(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


    @PutMapping
    public ResponseEntity<?> updateClass(@RequestBody Classes classes) {
        ErrorResponse errorResponseSuccess = new ErrorResponse("Turma atualizada com sucesso"
                , new Timestamp(System.currentTimeMillis()), HttpStatus.OK.name());
        ErrorResponse errorResponseError = new ErrorResponse("Não foi possível atualizar a turma"
                , new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.name());
        return classService.getClassById(classes.getId()).map(entidade -> {
            classService.saveClass(classes);
            return ResponseEntity.status(HttpStatus.OK).body(errorResponseSuccess);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseError));
    }
}