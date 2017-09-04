package com.cloudbaboons.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cloudbaboons.domain.Genapp;
import com.cloudbaboons.service.GenappService;
import com.cloudbaboons.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Genapp.
 */
@RestController
@RequestMapping("/api")
public class GenappResource {

    private final Logger log = LoggerFactory.getLogger(GenappResource.class);

    private static final String ENTITY_NAME = "genapp";

    private final GenappService genappService;

    public GenappResource(GenappService genappService) {
        this.genappService = genappService;
    }

    /**
     * POST  /genapps : Create a new genapp.
     *
     * @param genapp the genapp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genapp, or with status 400 (Bad Request) if the genapp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/genapps")
    @Timed
    public ResponseEntity<Genapp> createGenapp(@RequestBody Genapp genapp) throws URISyntaxException {
        log.debug("REST request to save Genapp : {}", genapp);
        if (genapp.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new genapp cannot already have an ID")).body(null);
        }
        Genapp result = genappService.save(genapp);
        return ResponseEntity.created(new URI("/api/genapps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /genapps : Updates an existing genapp.
     *
     * @param genapp the genapp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genapp,
     * or with status 400 (Bad Request) if the genapp is not valid,
     * or with status 500 (Internal Server Error) if the genapp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/genapps")
    @Timed
    public ResponseEntity<Genapp> updateGenapp(@RequestBody Genapp genapp) throws URISyntaxException {
        log.debug("REST request to update Genapp : {}", genapp);
        if (genapp.getId() == null) {
            return createGenapp(genapp);
        }
        Genapp result = genappService.save(genapp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, genapp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /genapps : get all the genapps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of genapps in body
     */
    @GetMapping("/genapps")
    @Timed
    public List<Genapp> getAllGenapps() {
        log.debug("REST request to get all Genapps");
        return genappService.findAll();
    }

    /**
     * GET  /genapps/:id : get the "id" genapp.
     *
     * @param id the id of the genapp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genapp, or with status 404 (Not Found)
     */
    @GetMapping("/genapps/{id}")
    @Timed
    public ResponseEntity<Genapp> getGenapp(@PathVariable Long id) {
        log.debug("REST request to get Genapp : {}", id);
        Genapp genapp = genappService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(genapp));
    }

    /**
     * DELETE  /genapps/:id : delete the "id" genapp.
     *
     * @param id the id of the genapp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/genapps/{id}")
    @Timed
    public ResponseEntity<Void> deleteGenapp(@PathVariable Long id) {
        log.debug("REST request to delete Genapp : {}", id);
        genappService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
