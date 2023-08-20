package com.challenge2.challenge2.repositories;

import com.challenge2.challenge2.entities.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquadRepository extends JpaRepository<Squad,Long> {
}
