package com.cloudbaboons.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cloudbaboons.domain.Entities;
import com.cloudbaboons.service.EntitiesService;
import com.cloudbaboons.web.rest.util.HeaderUtil;
import com.cloudbaboons.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Entities.
 */
@RestController
@RequestMapping("/api")
public class EntitiesResource {

    private final Logger log = LoggerFactory.getLogger(EntitiesResource.class);

    private static final String ENTITY_NAME = "entities";

    private final EntitiesService entitiesService;

    public EntitiesResource(EntitiesService entitiesService) {
        this.entitiesService = entitiesService;
    }

    /**
     * POST  /entities : Create a new entities.
     *
     * @param entities the entities to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entities, or with status 400 (Bad Request) if the entities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entities")
    @Timed
    public ResponseEntity<Entities> createEntities(@RequestBody Entities entities) throws URISyntaxException {
        log.debug("REST request to save Entities : {}", entities);
        if (entities.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entities cannot already have an ID")).body(null);
        }
        Entities result = entitiesService.save(entities);
        return ResponseEntity.created(new URI("/api/entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entities : Updates an existing entities.
     *
     * @param entities the entities to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entities,
     * or with status 400 (Bad Request) if the entities is not valid,
     * or with status 500 (Internal Server Error) if the entities couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entities")
    @Timed
    public ResponseEntity<Entities> updateEntities(@RequestBody Entities entities) throws URISyntaxException {
        log.debug("REST request to update Entities : {}", entities);
        if (entities.getId() == null) {
            return createEntities(entities);
        }
        Entities result = entitiesService.save(entities);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entities.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entities : get all the entities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entities in body
     */
    @GetMapping("/entities")
    @Timed
    public ResponseEntity<List<Entities>> getAllEntities(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Entities");
        Page<Entities> page = entitiesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entities/:id : get the "id" entities.
     *
     * @param id the id of the entities to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entities, or with status 404 (Not Found)
     */
    @GetMapping("/entities/{id}")
    @Timed
    public ResponseEntity<Entities> getEntities(@PathVariable Long id) {
        log.debug("REST request to get Entities : {}", id);
        Entities entities = entitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entities));
    }

    /**
     * DELETE  /entities/:id : delete the "id" entities.
     *
     * @param id the id of the entities to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntities(@PathVariable Long id) {
        log.debug("REST request to delete Entities : {}", id);
        entitiesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
