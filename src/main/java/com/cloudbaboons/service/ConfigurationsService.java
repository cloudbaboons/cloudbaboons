package com.cloudbaboons.service;

import com.cloudbaboons.domain.Configurations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Configurations.
 */
public interface ConfigurationsService {

    /**
     * Save a configurations.
     *
     * @param configurations the entity to save
     * @return the persisted entity
     */
    Configurations save(Configurations configurations);

    /**
     *  Get all the configurations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Configurations> findAll(Pageable pageable);

    /**
     *  Get the "id" configurations.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Configurations findOne(Long id);

    /**
     *  Delete the "id" configurations.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
