package com.cloudbaboons.web.rest;

import com.cloudbaboons.CloudbaboonsApp;

import com.cloudbaboons.domain.Genapp;
import com.cloudbaboons.repository.GenappRepository;
import com.cloudbaboons.service.GenappService;
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
 * Test class for the GenappResource REST controller.
 *
 * @see GenappResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudbaboonsApp.class)
public class GenappResourceIntTest {

    private static final String DEFAULT_APPLICATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_SERVER_PORT = "AAAAAAAAAA";
    private static final String UPDATED_SERVER_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_DATABASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_PREFIX = "BBBBBBBBBB";

    @Autowired
    private GenappRepository genappRepository;

    @Autowired
    private GenappService genappService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGenappMockMvc;

    private Genapp genapp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GenappResource genappResource = new GenappResource(genappService);
        this.restGenappMockMvc = MockMvcBuilders.standaloneSetup(genappResource)
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
    public static Genapp createEntity(EntityManager em) {
        Genapp genapp = new Genapp()
            .application_name(DEFAULT_APPLICATION_NAME)
            .package_name(DEFAULT_PACKAGE_NAME)
            .package_folder(DEFAULT_PACKAGE_FOLDER)
            .server_port(DEFAULT_SERVER_PORT)
            .database_type(DEFAULT_DATABASE_TYPE)
            .application_prefix(DEFAULT_APPLICATION_PREFIX);
        return genapp;
    }

    @Before
    public void initTest() {
        genapp = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenapp() throws Exception {
        int databaseSizeBeforeCreate = genappRepository.findAll().size();

        // Create the Genapp
        restGenappMockMvc.perform(post("/api/genapps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genapp)))
            .andExpect(status().isCreated());

        // Validate the Genapp in the database
        List<Genapp> genappList = genappRepository.findAll();
        assertThat(genappList).hasSize(databaseSizeBeforeCreate + 1);
        Genapp testGenapp = genappList.get(genappList.size() - 1);
        assertThat(testGenapp.getApplication_name()).isEqualTo(DEFAULT_APPLICATION_NAME);
        assertThat(testGenapp.getPackage_name()).isEqualTo(DEFAULT_PACKAGE_NAME);
        assertThat(testGenapp.getPackage_folder()).isEqualTo(DEFAULT_PACKAGE_FOLDER);
        assertThat(testGenapp.getServer_port()).isEqualTo(DEFAULT_SERVER_PORT);
        assertThat(testGenapp.getDatabase_type()).isEqualTo(DEFAULT_DATABASE_TYPE);
        assertThat(testGenapp.getApplication_prefix()).isEqualTo(DEFAULT_APPLICATION_PREFIX);
    }

    @Test
    @Transactional
    public void createGenappWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genappRepository.findAll().size();

        // Create the Genapp with an existing ID
        genapp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenappMockMvc.perform(post("/api/genapps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genapp)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Genapp> genappList = genappRepository.findAll();
        assertThat(genappList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGenapps() throws Exception {
        // Initialize the database
        genappRepository.saveAndFlush(genapp);

        // Get all the genappList
        restGenappMockMvc.perform(get("/api/genapps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genapp.getId().intValue())))
            .andExpect(jsonPath("$.[*].application_name").value(hasItem(DEFAULT_APPLICATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].package_name").value(hasItem(DEFAULT_PACKAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].package_folder").value(hasItem(DEFAULT_PACKAGE_FOLDER.toString())))
            .andExpect(jsonPath("$.[*].server_port").value(hasItem(DEFAULT_SERVER_PORT.toString())))
            .andExpect(jsonPath("$.[*].database_type").value(hasItem(DEFAULT_DATABASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].application_prefix").value(hasItem(DEFAULT_APPLICATION_PREFIX.toString())));
    }

    @Test
    @Transactional
    public void getGenapp() throws Exception {
        // Initialize the database
        genappRepository.saveAndFlush(genapp);

        // Get the genapp
        restGenappMockMvc.perform(get("/api/genapps/{id}", genapp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(genapp.getId().intValue()))
            .andExpect(jsonPath("$.application_name").value(DEFAULT_APPLICATION_NAME.toString()))
            .andExpect(jsonPath("$.package_name").value(DEFAULT_PACKAGE_NAME.toString()))
            .andExpect(jsonPath("$.package_folder").value(DEFAULT_PACKAGE_FOLDER.toString()))
            .andExpect(jsonPath("$.server_port").value(DEFAULT_SERVER_PORT.toString()))
            .andExpect(jsonPath("$.database_type").value(DEFAULT_DATABASE_TYPE.toString()))
            .andExpect(jsonPath("$.application_prefix").value(DEFAULT_APPLICATION_PREFIX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGenapp() throws Exception {
        // Get the genapp
        restGenappMockMvc.perform(get("/api/genapps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenapp() throws Exception {
        // Initialize the database
        genappService.save(genapp);

        int databaseSizeBeforeUpdate = genappRepository.findAll().size();

        // Update the genapp
        Genapp updatedGenapp = genappRepository.findOne(genapp.getId());
        updatedGenapp
            .application_name(UPDATED_APPLICATION_NAME)
            .package_name(UPDATED_PACKAGE_NAME)
            .package_folder(UPDATED_PACKAGE_FOLDER)
            .server_port(UPDATED_SERVER_PORT)
            .database_type(UPDATED_DATABASE_TYPE)
            .application_prefix(UPDATED_APPLICATION_PREFIX);

        restGenappMockMvc.perform(put("/api/genapps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGenapp)))
            .andExpect(status().isOk());

        // Validate the Genapp in the database
        List<Genapp> genappList = genappRepository.findAll();
        assertThat(genappList).hasSize(databaseSizeBeforeUpdate);
        Genapp testGenapp = genappList.get(genappList.size() - 1);
        assertThat(testGenapp.getApplication_name()).isEqualTo(UPDATED_APPLICATION_NAME);
        assertThat(testGenapp.getPackage_name()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testGenapp.getPackage_folder()).isEqualTo(UPDATED_PACKAGE_FOLDER);
        assertThat(testGenapp.getServer_port()).isEqualTo(UPDATED_SERVER_PORT);
        assertThat(testGenapp.getDatabase_type()).isEqualTo(UPDATED_DATABASE_TYPE);
        assertThat(testGenapp.getApplication_prefix()).isEqualTo(UPDATED_APPLICATION_PREFIX);
    }

    @Test
    @Transactional
    public void updateNonExistingGenapp() throws Exception {
        int databaseSizeBeforeUpdate = genappRepository.findAll().size();

        // Create the Genapp

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGenappMockMvc.perform(put("/api/genapps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genapp)))
            .andExpect(status().isCreated());

        // Validate the Genapp in the database
        List<Genapp> genappList = genappRepository.findAll();
        assertThat(genappList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGenapp() throws Exception {
        // Initialize the database
        genappService.save(genapp);

        int databaseSizeBeforeDelete = genappRepository.findAll().size();

        // Get the genapp
        restGenappMockMvc.perform(delete("/api/genapps/{id}", genapp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Genapp> genappList = genappRepository.findAll();
        assertThat(genappList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Genapp.class);
        Genapp genapp1 = new Genapp();
        genapp1.setId(1L);
        Genapp genapp2 = new Genapp();
        genapp2.setId(genapp1.getId());
        assertThat(genapp1).isEqualTo(genapp2);
        genapp2.setId(2L);
        assertThat(genapp1).isNotEqualTo(genapp2);
        genapp1.setId(null);
        assertThat(genapp1).isNotEqualTo(genapp2);
    }
}
