package com.challenge2.challenge2.services.impl;

import com.challenge2.challenge2.entities.Organizer;
import com.challenge2.challenge2.enums.OrganizerEnums;
import com.challenge2.challenge2.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface OrganizerService {

    List<Organizer> getAllOrganizers();

    Optional<Organizer> getOrganizerById(Long id);

    Optional <Organizer> saveOrganizer(Organizer organizer);

    boolean isValidRole(OrganizerEnums role);

    void deleteOrganizer(Long id);
}
