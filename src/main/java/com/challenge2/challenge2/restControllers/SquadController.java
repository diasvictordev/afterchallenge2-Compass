package com.challenge2.challenge2.restControllers;

import com.challenge2.challenge2.dto.SquadDTO;
import com.challenge2.challenge2.entities.ErrorResponse;
import com.challenge2.challenge2.entities.Squad;
import com.challenge2.challenge2.services.impl.SquadServiceImpl;
import com.challenge2.challenge2.services.impl.StudentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/squad")
public class SquadController {

    private final SquadServiceImpl squadService;

    public SquadController (SquadServiceImpl squadService){
        this.squadService = squadService;
    }

    @GetMapping
    public ResponseEntity<?> getAllSquads() {
        List<Squad> squads = squadService.getAllSquads();
        ErrorResponse errorResponse = new ErrorResponse("Nenhum Squad encontrado"
                , new Timestamp(System.currentTimeMillis()),HttpStatus.NOT_FOUND.name());
        if(squads.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(squads);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSquadById(@PathVariable Long id) {
        Optional <Squad> squad = squadService.getSquadById(id);
        ErrorResponse errorResponse = new ErrorResponse("Squad não encontrado"
                , new Timestamp(System.currentTimeMillis()),HttpStatus.NOT_FOUND.name());
        if(squad.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(squad);
        }
    }


    @PostMapping
    public ResponseEntity<?> createSquad(@RequestBody SquadDTO squadDTO) {
        Squad createdSquad = squadService.createSquadWithStudents(squadDTO);
        //if (createdSquad!=null) squadService.putSquadInStudents(createdSquad, squadDTO);


        ErrorResponse errorResponse = new ErrorResponse("Não foi possível criar a squad"
                , new Timestamp(System.currentTimeMillis()),HttpStatus.BAD_REQUEST.name());
        if(createdSquad == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSquad);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity deleteSquad(@PathVariable Long id){
        ErrorResponse errorResponse = new ErrorResponse("Squad não encontrada na base de dados"
                , new Timestamp(System.currentTimeMillis()),HttpStatus.BAD_REQUEST.name());

        return squadService.getSquadById(id).map(entidade ->{
            squadService.deleteSquad(entidade.getId());
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse));

    }



    @PutMapping
    public ResponseEntity<ErrorResponse> updateSquad(@RequestBody Squad squad){
        ErrorResponse errorResponseSucces = new ErrorResponse("Squad atualizada com sucesso!"
                , new Timestamp(System.currentTimeMillis()),HttpStatus.OK.name());
        ErrorResponse errorResponseFail = new ErrorResponse("Squad não existe, portanto não pode ser alterada"
                , new Timestamp(System.currentTimeMillis()),HttpStatus.BAD_REQUEST.name());

        if (squad.getId()==null) return new ResponseEntity<>(errorResponseFail, HttpStatus.BAD_REQUEST);

        return squadService.getSquadById(squad.getId()).map(entidade -> {
            squadService.saveSquad(squad);
            return new ResponseEntity<>(errorResponseSucces, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(errorResponseFail, HttpStatus.BAD_REQUEST));
    }
}
