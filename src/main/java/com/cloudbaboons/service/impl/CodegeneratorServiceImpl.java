package com.cloudbaboons.service.impl;

import com.cloudbaboons.service.CodegeneratorService;
import com.cloudbaboons.domain.Codegenerator;
import com.cloudbaboons.repository.CodegeneratorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Codegenerator.
 */
@Service
@Transactional
public class CodegeneratorServiceImpl implements CodegeneratorService{

    private final Logger log = LoggerFactory.getLogger(CodegeneratorServiceImpl.class);

    private final CodegeneratorRepository codegeneratorRepository;

    public CodegeneratorServiceImpl(CodegeneratorRepository codegeneratorRepository) {
        this.codegeneratorRepository = codegeneratorRepository;
    }

    /**
     * Save a codegenerator.
     *
     * @param codegenerator the entity to save
     * @return the persisted entity
     */
    @Override
    public Codegenerator save(Codegenerator codegenerator) {
        log.debug("Request to save Codegenerator : {}", codegenerator);
        return codegeneratorRepository.save(codegenerator);
    }

    /**
     *  Get all the codegenerators.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Codegenerator> findAll() {
        log.debug("Request to get all Codegenerators");
        return codegeneratorRepository.findAll();
    }

    /**
     *  Get one codegenerator by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Codegenerator findOne(Long id) {
        log.debug("Request to get Codegenerator : {}", id);
        return codegeneratorRepository.findOne(id);
    }

    /**
     *  Delete the  codegenerator by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Codegenerator : {}", id);
        codegeneratorRepository.delete(id);
    }
}
