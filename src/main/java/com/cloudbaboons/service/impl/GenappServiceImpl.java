package com.cloudbaboons.service.impl;

import com.cloudbaboons.service.GenappService;
import com.cloudbaboons.web.rest.GenerateCodeController;
import com.cloudbaboons.domain.Genapp;
import com.cloudbaboons.repository.GenappRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



/**
 * Service Implementation for managing Genapp.
 */
@Service
@Transactional
public class GenappServiceImpl implements GenappService{

    private final Logger log = LoggerFactory.getLogger(GenappServiceImpl.class);

    private final GenappRepository genappRepository;

    public GenappServiceImpl(GenappRepository genappRepository) {
        this.genappRepository = genappRepository;
    }
    
    @Autowired
    GenerateCodeController controller;

    /**
     * Save a genapp.
     *
     * @param genapp the entity to save
     * @return the persisted entity
     */
    @Override
    public Genapp save(Genapp genapp) {
        log.debug("Request to save Genapp : {}", genapp);
        
        Genapp newapp = genappRepository.save(genapp);
        
        String appName = "test-app";
        String packageName = "com.test";
        String packageFolder = "com/test";
        String portNum =     "8189";
        String databaseType = "postgresql";
        String appPrefix = "cb";
        
        controller.generate(newapp.getApplication_name(),newapp.getPackage_name(),newapp.getPackage_folder(),newapp.getServer_port(),
        		newapp.getDatabase_type(),newapp.getApplication_prefix());
        
        return newapp;
    }

    /**
     *  Get all the genapps.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Genapp> findAll() {
        log.debug("Request to get all Genapps");
        return genappRepository.findAll();
    }

    /**
     *  Get one genapp by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Genapp findOne(Long id) {
        log.debug("Request to get Genapp : {}", id);
        return genappRepository.findOne(id);
    }

    /**
     *  Delete the  genapp by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Genapp : {}", id);
        genappRepository.delete(id);
    }
}
