package com.cloudbaboons.repository;

import com.cloudbaboons.domain.Codegenerator;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Codegenerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodegeneratorRepository extends JpaRepository<Codegenerator,Long> {
    
}
