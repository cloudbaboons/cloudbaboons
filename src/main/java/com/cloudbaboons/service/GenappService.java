package com.cloudbaboons.service;

import com.cloudbaboons.domain.Genapp;
import java.util.List;

/**
 * Service Interface for managing Genapp.
 */
public interface GenappService {

    /**
     * Save a genapp.
     *
     * @param genapp the entity to save
     * @return the persisted entity
     */
    Genapp save(Genapp genapp);

    /**
     *  Get all the genapps.
     *
     *  @return the list of entities
     */
    List<Genapp> findAll();

    /**
     *  Get the "id" genapp.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Genapp findOne(Long id);

    /**
     *  Delete the "id" genapp.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
