package com.cloudbaboons.repository;

import com.cloudbaboons.domain.Configurations;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Configurations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigurationsRepository extends JpaRepository<Configurations,Long> {
    
}
