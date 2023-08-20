package com.challenge2.challenge2.services.impl;

import com.challenge2.challenge2.dto.SquadDTO;
import com.challenge2.challenge2.entities.Squad;
import com.challenge2.challenge2.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface SquadService {

    List<Squad> getAllSquads();

    Optional<Squad> getSquadById(Long id);

    Squad saveSquad(Squad squad);

    void deleteSquad(Long id);

    public Squad createSquadWithStudents(SquadDTO squadDTO);

}
