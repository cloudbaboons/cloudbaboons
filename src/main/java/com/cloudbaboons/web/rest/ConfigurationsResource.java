package com.cloudbaboons.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cloudbaboons.domain.Configurations;
import com.cloudbaboons.service.ConfigurationsService;
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
 * REST controller for managing Configurations.
 */
@RestController
@RequestMapping("/api")
public class ConfigurationsResource {

    private final Logger log = LoggerFactory.getLogger(ConfigurationsResource.class);

    private static final String ENTITY_NAME = "configurations";

    private final ConfigurationsService configurationsService;

    public ConfigurationsResource(ConfigurationsService configurationsService) {
        this.configurationsService = configurationsService;
    }

    /**
     * POST  /configurations : Create a new configurations.
     *
     * @param configurations the configurations to create
     * @return the ResponseEntity with status 201 (Created) and with body the new configurations, or with status 400 (Bad Request) if the configurations has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/configurations")
    @Timed
    public ResponseEntity<Configurations> createConfigurations(@RequestBody Configurations configurations) throws URISyntaxException {
        log.debug("REST request to save Configurations : {}", configurations);
        if (configurations.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new configurations cannot already have an ID")).body(null);
        }
        Configurations result = configurationsService.save(configurations);
        return ResponseEntity.created(new URI("/api/configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /configurations : Updates an existing configurations.
     *
     * @param configurations the configurations to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated configurations,
     * or with status 400 (Bad Request) if the configurations is not valid,
     * or with status 500 (Internal Server Error) if the configurations couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/configurations")
    @Timed
    public ResponseEntity<Configurations> updateConfigurations(@RequestBody Configurations configurations) throws URISyntaxException {
        log.debug("REST request to update Configurations : {}", configurations);
        if (configurations.getId() == null) {
            return createConfigurations(configurations);
        }
        Configurations result = configurationsService.save(configurations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configurations.getId().toString()))
            .body(result);
    }

    /**
     * GET  /configurations : get all the configurations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of configurations in body
     */
    @GetMapping("/configurations")
    @Timed
    public ResponseEntity<List<Configurations>> getAllConfigurations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Configurations");
        Page<Configurations> page = configurationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/configurations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /configurations/:id : get the "id" configurations.
     *
     * @param id the id of the configurations to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the configurations, or with status 404 (Not Found)
     */
    @GetMapping("/configurations/{id}")
    @Timed
    public ResponseEntity<Configurations> getConfigurations(@PathVariable Long id) {
        log.debug("REST request to get Configurations : {}", id);
        Configurations configurations = configurationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(configurations));
    }

    /**
     * DELETE  /configurations/:id : delete the "id" configurations.
     *
     * @param id the id of the configurations to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/configurations/{id}")
    @Timed
    public ResponseEntity<Void> deleteConfigurations(@PathVariable Long id) {
        log.debug("REST request to delete Configurations : {}", id);
        configurationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
