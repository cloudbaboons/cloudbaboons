package com.cloudbaboons.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cloudbaboons.domain.Codegenerator;
import com.cloudbaboons.service.CodegeneratorService;
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
 * REST controller for managing Codegenerator.
 */
@RestController
@RequestMapping("/api")
public class CodegeneratorResource {

    private final Logger log = LoggerFactory.getLogger(CodegeneratorResource.class);

    private static final String ENTITY_NAME = "codegenerator";

    private final CodegeneratorService codegeneratorService;

    public CodegeneratorResource(CodegeneratorService codegeneratorService) {
        this.codegeneratorService = codegeneratorService;
    }

    /**
     * POST  /codegenerators : Create a new codegenerator.
     *
     * @param codegenerator the codegenerator to create
     * @return the ResponseEntity with status 201 (Created) and with body the new codegenerator, or with status 400 (Bad Request) if the codegenerator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/codegenerators")
    @Timed
    public ResponseEntity<Codegenerator> createCodegenerator(@RequestBody Codegenerator codegenerator) throws URISyntaxException {
        log.debug("REST request to save Codegenerator : {}", codegenerator);
        if (codegenerator.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new codegenerator cannot already have an ID")).body(null);
        }
        Codegenerator result = codegeneratorService.save(codegenerator);
        return ResponseEntity.created(new URI("/api/codegenerators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /codegenerators : Updates an existing codegenerator.
     *
     * @param codegenerator the codegenerator to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated codegenerator,
     * or with status 400 (Bad Request) if the codegenerator is not valid,
     * or with status 500 (Internal Server Error) if the codegenerator couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/codegenerators")
    @Timed
    public ResponseEntity<Codegenerator> updateCodegenerator(@RequestBody Codegenerator codegenerator) throws URISyntaxException {
        log.debug("REST request to update Codegenerator : {}", codegenerator);
        if (codegenerator.getId() == null) {
            return createCodegenerator(codegenerator);
        }
        Codegenerator result = codegeneratorService.save(codegenerator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, codegenerator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /codegenerators : get all the codegenerators.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of codegenerators in body
     */
    @GetMapping("/codegenerators")
    @Timed
    public List<Codegenerator> getAllCodegenerators() {
        log.debug("REST request to get all Codegenerators");
        return codegeneratorService.findAll();
    }

    /**
     * GET  /codegenerators/:id : get the "id" codegenerator.
     *
     * @param id the id of the codegenerator to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the codegenerator, or with status 404 (Not Found)
     */
    @GetMapping("/codegenerators/{id}")
    @Timed
    public ResponseEntity<Codegenerator> getCodegenerator(@PathVariable Long id) {
        log.debug("REST request to get Codegenerator : {}", id);
        Codegenerator codegenerator = codegeneratorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(codegenerator));
    }

    /**
     * DELETE  /codegenerators/:id : delete the "id" codegenerator.
     *
     * @param id the id of the codegenerator to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/codegenerators/{id}")
    @Timed
    public ResponseEntity<Void> deleteCodegenerator(@PathVariable Long id) {
        log.debug("REST request to delete Codegenerator : {}", id);
        codegeneratorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
