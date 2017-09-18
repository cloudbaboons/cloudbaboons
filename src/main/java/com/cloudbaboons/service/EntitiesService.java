package com.cloudbaboons.service;

import com.cloudbaboons.domain.Entities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Entities.
 */
public interface EntitiesService {

    /**
     * Save a entities.
     *
     * @param entities the entity to save
     * @return the persisted entity
     */
    Entities save(Entities entities);

    /**
     *  Get all the entities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Entities> findAll(Pageable pageable);

    /**
     *  Get the "id" entities.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Entities findOne(Long id);

    /**
     *  Delete the "id" entities.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
