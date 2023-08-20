package com.challenge2.challenge2.repositories;

import com.challenge2.challenge2.entities.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Classes,Long> {
}
