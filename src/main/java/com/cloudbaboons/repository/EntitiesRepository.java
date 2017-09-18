package com.cloudbaboons.repository;

import com.cloudbaboons.domain.Entities;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Entities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntitiesRepository extends JpaRepository<Entities,Long> {
    
}
