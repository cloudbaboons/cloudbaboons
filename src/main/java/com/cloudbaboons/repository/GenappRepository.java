package com.cloudbaboons.repository;

import com.cloudbaboons.domain.Genapp;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Genapp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenappRepository extends JpaRepository<Genapp,Long> {
    
}
