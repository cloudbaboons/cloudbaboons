package com.cloudbaboons.service.impl;

import com.cloudbaboons.service.ConfigurationsService;
import com.cloudbaboons.domain.Configurations;
import com.cloudbaboons.repository.ConfigurationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Configurations.
 */
@Service
@Transactional
public class ConfigurationsServiceImpl implements ConfigurationsService{

    private final Logger log = LoggerFactory.getLogger(ConfigurationsServiceImpl.class);

    private final ConfigurationsRepository configurationsRepository;

    public ConfigurationsServiceImpl(ConfigurationsRepository configurationsRepository) {
        this.configurationsRepository = configurationsRepository;
    }

    /**
     * Save a configurations.
     *
     * @param configurations the entity to save
     * @return the persisted entity
     */
    @Override
    public Configurations save(Configurations configurations) {
        log.debug("Request to save Configurations : {}", configurations);
        return configurationsRepository.save(configurations);
    }

    /**
     *  Get all the configurations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Configurations> findAll(Pageable pageable) {
        log.debug("Request to get all Configurations");
        return configurationsRepository.findAll(pageable);
    }

    /**
     *  Get one configurations by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Configurations findOne(Long id) {
        log.debug("Request to get Configurations : {}", id);
        return configurationsRepository.findOne(id);
    }

    /**
     *  Delete the  configurations by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Configurations : {}", id);
        configurationsRepository.delete(id);
    }
}
