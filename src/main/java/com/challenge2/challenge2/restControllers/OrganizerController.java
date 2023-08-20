package com.challenge2.challenge2.restControllers;

import com.challenge2.challenge2.entities.ErrorResponse;
import com.challenge2.challenge2.exceptions.BadRequestException;
import com.challenge2.challenge2.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.RestController;
import com.challenge2.challenge2.entities.Organizer;
import com.challenge2.challenge2.services.impl.OrganizerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizer")
public class OrganizerController {

    private final OrganizerServiceImpl organizerService;

    public OrganizerController (OrganizerServiceImpl organizerService){
        this.organizerService = organizerService;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrganizers() {
        ErrorResponse errorResponse = new ErrorResponse("Nenhum organizador encontrado",
        new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.name());
        if(organizerService.getAllOrganizers().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(organizerService.getAllOrganizers());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizerById(@PathVariable Long id) {
        ErrorResponse errorResponse = new ErrorResponse("Organizador não encontrado",
                new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.name());
        Optional <Organizer> organizer = organizerService.getOrganizerById(id);
        if (organizer.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(organizer);
        }
    }


    /*@PostMapping
    public ResponseEntity <?> addOrganizer(@RequestBody Organizer organizer) {
        Organizer savedOrganizer = organizerService.saveOrganizer(organizer);
        ErrorResponse errorResponse = new ErrorResponse("Não foi possível criar o organizador",
                new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.name());
        if (savedOrganizer == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrganizer);
    }*/

    @PostMapping
    public ResponseEntity<?> addOrganizer(@RequestBody Organizer organizer) {
        Optional<Organizer> savedOrganizer = organizerService.saveOrganizer(organizer);
        if (savedOrganizer.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Não foi possível criar o organizador",
                    new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrganizer.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> deleteOrganizer(@PathVariable Long id){
        ErrorResponse errorResponse = new ErrorResponse("Não foi possível deletar o organizador pois ele não existe",
                new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.name());
        return organizerService.getOrganizerById(id).map(entidade ->{
            organizerService.deleteOrganizer(entidade.getId());
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse));
    }

    @PutMapping
    public ResponseEntity<ErrorResponse> updateOrganizer(@RequestBody Organizer organizer){
        ErrorResponse errorResponseSuccess = new ErrorResponse("Organizador atualizado com sucesso!",
                new Timestamp(System.currentTimeMillis()), HttpStatus.OK.name());
        ErrorResponse errorResponseFail = new ErrorResponse("Não foi possível atualizar o organizador",
                new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.name());

        return organizerService.getOrganizerById(organizer.getId()).map(entidade -> {
            organizerService.saveOrganizer(organizer);
            return new ResponseEntity<>(errorResponseSuccess, HttpStatus.OK);
        }).orElseGet(() ->
                new ResponseEntity<>(errorResponseFail, HttpStatus.BAD_REQUEST));
    }
}
