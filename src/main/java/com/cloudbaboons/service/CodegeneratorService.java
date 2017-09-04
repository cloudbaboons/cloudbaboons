package com.cloudbaboons.service;

import com.cloudbaboons.domain.Codegenerator;
import java.util.List;

/**
 * Service Interface for managing Codegenerator.
 */
public interface CodegeneratorService {

    /**
     * Save a codegenerator.
     *
     * @param codegenerator the entity to save
     * @return the persisted entity
     */
    Codegenerator save(Codegenerator codegenerator);

    /**
     *  Get all the codegenerators.
     *
     *  @return the list of entities
     */
    List<Codegenerator> findAll();

    /**
     *  Get the "id" codegenerator.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Codegenerator findOne(Long id);

    /**
     *  Delete the "id" codegenerator.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
