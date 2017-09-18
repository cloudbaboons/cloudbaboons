package com.cloudbaboons.web.rest;

import com.cloudbaboons.CloudbaboonsApp;

import com.cloudbaboons.domain.Entities;
import com.cloudbaboons.repository.EntitiesRepository;
import com.cloudbaboons.service.EntitiesService;
import com.cloudbaboons.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntitiesResource REST controller.
 *
 * @see EntitiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudbaboonsApp.class)
public class EntitiesResourceIntTest {

    private static final String DEFAULT_ORGNAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGNAME = "BBBBBBBBBB";

    private static final String DEFAULT_APPNAME = "AAAAAAAAAA";
    private static final String UPDATED_APPNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELDNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDTYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELDTYPE = "BBBBBBBBBB";

    @Autowired
    private EntitiesRepository entitiesRepository;

    @Autowired
    private EntitiesService entitiesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntitiesMockMvc;

    private Entities entities;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntitiesResource entitiesResource = new EntitiesResource(entitiesService);
        this.restEntitiesMockMvc = MockMvcBuilders.standaloneSetup(entitiesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entities createEntity(EntityManager em) {
        Entities entities = new Entities()
            .orgname(DEFAULT_ORGNAME)
            .appname(DEFAULT_APPNAME)
            .fieldname(DEFAULT_FIELDNAME)
            .fieldtype(DEFAULT_FIELDTYPE);
        return entities;
    }

    @Before
    public void initTest() {
        entities = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntities() throws Exception {
        int databaseSizeBeforeCreate = entitiesRepository.findAll().size();

        // Create the Entities
        restEntitiesMockMvc.perform(post("/api/entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entities)))
            .andExpect(status().isCreated());

        // Validate the Entities in the database
        List<Entities> entitiesList = entitiesRepository.findAll();
        assertThat(entitiesList).hasSize(databaseSizeBeforeCreate + 1);
        Entities testEntities = entitiesList.get(entitiesList.size() - 1);
        assertThat(testEntities.getOrgname()).isEqualTo(DEFAULT_ORGNAME);
        assertThat(testEntities.getAppname()).isEqualTo(DEFAULT_APPNAME);
        assertThat(testEntities.getFieldname()).isEqualTo(DEFAULT_FIELDNAME);
        assertThat(testEntities.getFieldtype()).isEqualTo(DEFAULT_FIELDTYPE);
    }

    @Test
    @Transactional
    public void createEntitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entitiesRepository.findAll().size();

        // Create the Entities with an existing ID
        entities.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntitiesMockMvc.perform(post("/api/entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entities)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entities> entitiesList = entitiesRepository.findAll();
        assertThat(entitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEntities() throws Exception {
        // Initialize the database
        entitiesRepository.saveAndFlush(entities);

        // Get all the entitiesList
        restEntitiesMockMvc.perform(get("/api/entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entities.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgname").value(hasItem(DEFAULT_ORGNAME.toString())))
            .andExpect(jsonPath("$.[*].appname").value(hasItem(DEFAULT_APPNAME.toString())))
            .andExpect(jsonPath("$.[*].fieldname").value(hasItem(DEFAULT_FIELDNAME.toString())))
            .andExpect(jsonPath("$.[*].fieldtype").value(hasItem(DEFAULT_FIELDTYPE.toString())));
    }

    @Test
    @Transactional
    public void getEntities() throws Exception {
        // Initialize the database
        entitiesRepository.saveAndFlush(entities);

        // Get the entities
        restEntitiesMockMvc.perform(get("/api/entities/{id}", entities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entities.getId().intValue()))
            .andExpect(jsonPath("$.orgname").value(DEFAULT_ORGNAME.toString()))
            .andExpect(jsonPath("$.appname").value(DEFAULT_APPNAME.toString()))
            .andExpect(jsonPath("$.fieldname").value(DEFAULT_FIELDNAME.toString()))
            .andExpect(jsonPath("$.fieldtype").value(DEFAULT_FIELDTYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntities() throws Exception {
        // Get the entities
        restEntitiesMockMvc.perform(get("/api/entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntities() throws Exception {
        // Initialize the database
        entitiesService.save(entities);

        int databaseSizeBeforeUpdate = entitiesRepository.findAll().size();

        // Update the entities
        Entities updatedEntities = entitiesRepository.findOne(entities.getId());
        updatedEntities
            .orgname(UPDATED_ORGNAME)
            .appname(UPDATED_APPNAME)
            .fieldname(UPDATED_FIELDNAME)
            .fieldtype(UPDATED_FIELDTYPE);

        restEntitiesMockMvc.perform(put("/api/entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntities)))
            .andExpect(status().isOk());

        // Validate the Entities in the database
        List<Entities> entitiesList = entitiesRepository.findAll();
        assertThat(entitiesList).hasSize(databaseSizeBeforeUpdate);
        Entities testEntities = entitiesList.get(entitiesList.size() - 1);
        assertThat(testEntities.getOrgname()).isEqualTo(UPDATED_ORGNAME);
        assertThat(testEntities.getAppname()).isEqualTo(UPDATED_APPNAME);
        assertThat(testEntities.getFieldname()).isEqualTo(UPDATED_FIELDNAME);
        assertThat(testEntities.getFieldtype()).isEqualTo(UPDATED_FIELDTYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEntities() throws Exception {
        int databaseSizeBeforeUpdate = entitiesRepository.findAll().size();

        // Create the Entities

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntitiesMockMvc.perform(put("/api/entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entities)))
            .andExpect(status().isCreated());

        // Validate the Entities in the database
        List<Entities> entitiesList = entitiesRepository.findAll();
        assertThat(entitiesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntities() throws Exception {
        // Initialize the database
        entitiesService.save(entities);

        int databaseSizeBeforeDelete = entitiesRepository.findAll().size();

        // Get the entities
        restEntitiesMockMvc.perform(delete("/api/entities/{id}", entities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entities> entitiesList = entitiesRepository.findAll();
        assertThat(entitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entities.class);
        Entities entities1 = new Entities();
        entities1.setId(1L);
        Entities entities2 = new Entities();
        entities2.setId(entities1.getId());
        assertThat(entities1).isEqualTo(entities2);
        entities2.setId(2L);
        assertThat(entities1).isNotEqualTo(entities2);
        entities1.setId(null);
        assertThat(entities1).isNotEqualTo(entities2);
    }
}
