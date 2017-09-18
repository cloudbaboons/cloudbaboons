package com.cloudbaboons.service.impl;

import com.cloudbaboons.service.EntitiesService;
import com.cloudbaboons.domain.Entities;
import com.cloudbaboons.repository.EntitiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Entities.
 */
@Service
@Transactional
public class EntitiesServiceImpl implements EntitiesService{

    private final Logger log = LoggerFactory.getLogger(EntitiesServiceImpl.class);

    private final EntitiesRepository entitiesRepository;

    public EntitiesServiceImpl(EntitiesRepository entitiesRepository) {
        this.entitiesRepository = entitiesRepository;
    }

    /**
     * Save a entities.
     *
     * @param entities the entity to save
     * @return the persisted entity
     */
    @Override
    public Entities save(Entities entities) {
        log.debug("Request to save Entities : {}", entities);
        return entitiesRepository.save(entities);
    }

    /**
     *  Get all the entities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Entities> findAll(Pageable pageable) {
        log.debug("Request to get all Entities");
        return entitiesRepository.findAll(pageable);
    }

    /**
     *  Get one entities by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Entities findOne(Long id) {
        log.debug("Request to get Entities : {}", id);
        return entitiesRepository.findOne(id);
    }

    /**
     *  Delete the  entities by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entities : {}", id);
        entitiesRepository.delete(id);
    }
}
