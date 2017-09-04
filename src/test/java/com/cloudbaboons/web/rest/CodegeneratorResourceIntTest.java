package com.cloudbaboons.web.rest;

import com.cloudbaboons.CloudbaboonsApp;

import com.cloudbaboons.domain.Codegenerator;
import com.cloudbaboons.repository.CodegeneratorRepository;
import com.cloudbaboons.service.CodegeneratorService;
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
 * Test class for the CodegeneratorResource REST controller.
 *
 * @see CodegeneratorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudbaboonsApp.class)
public class CodegeneratorResourceIntTest {

    private static final String DEFAULT_APPLICATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_NAME = "BBBBBBBBBB";

    @Autowired
    private CodegeneratorRepository codegeneratorRepository;

    @Autowired
    private CodegeneratorService codegeneratorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCodegeneratorMockMvc;

    private Codegenerator codegenerator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CodegeneratorResource codegeneratorResource = new CodegeneratorResource(codegeneratorService);
        this.restCodegeneratorMockMvc = MockMvcBuilders.standaloneSetup(codegeneratorResource)
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
    public static Codegenerator createEntity(EntityManager em) {
        Codegenerator codegenerator = new Codegenerator()
            .application_name(DEFAULT_APPLICATION_NAME)
            .package_name(DEFAULT_PACKAGE_NAME);
        return codegenerator;
    }

    @Before
    public void initTest() {
        codegenerator = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodegenerator() throws Exception {
        int databaseSizeBeforeCreate = codegeneratorRepository.findAll().size();

        // Create the Codegenerator
        restCodegeneratorMockMvc.perform(post("/api/codegenerators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codegenerator)))
            .andExpect(status().isCreated());

        // Validate the Codegenerator in the database
        List<Codegenerator> codegeneratorList = codegeneratorRepository.findAll();
        assertThat(codegeneratorList).hasSize(databaseSizeBeforeCreate + 1);
        Codegenerator testCodegenerator = codegeneratorList.get(codegeneratorList.size() - 1);
        assertThat(testCodegenerator.getApplication_name()).isEqualTo(DEFAULT_APPLICATION_NAME);
        assertThat(testCodegenerator.getPackage_name()).isEqualTo(DEFAULT_PACKAGE_NAME);
    }

    @Test
    @Transactional
    public void createCodegeneratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codegeneratorRepository.findAll().size();

        // Create the Codegenerator with an existing ID
        codegenerator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodegeneratorMockMvc.perform(post("/api/codegenerators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codegenerator)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Codegenerator> codegeneratorList = codegeneratorRepository.findAll();
        assertThat(codegeneratorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCodegenerators() throws Exception {
        // Initialize the database
        codegeneratorRepository.saveAndFlush(codegenerator);

        // Get all the codegeneratorList
        restCodegeneratorMockMvc.perform(get("/api/codegenerators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codegenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].application_name").value(hasItem(DEFAULT_APPLICATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].package_name").value(hasItem(DEFAULT_PACKAGE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCodegenerator() throws Exception {
        // Initialize the database
        codegeneratorRepository.saveAndFlush(codegenerator);

        // Get the codegenerator
        restCodegeneratorMockMvc.perform(get("/api/codegenerators/{id}", codegenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(codegenerator.getId().intValue()))
            .andExpect(jsonPath("$.application_name").value(DEFAULT_APPLICATION_NAME.toString()))
            .andExpect(jsonPath("$.package_name").value(DEFAULT_PACKAGE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCodegenerator() throws Exception {
        // Get the codegenerator
        restCodegeneratorMockMvc.perform(get("/api/codegenerators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodegenerator() throws Exception {
        // Initialize the database
        codegeneratorService.save(codegenerator);

        int databaseSizeBeforeUpdate = codegeneratorRepository.findAll().size();

        // Update the codegenerator
        Codegenerator updatedCodegenerator = codegeneratorRepository.findOne(codegenerator.getId());
        updatedCodegenerator
            .application_name(UPDATED_APPLICATION_NAME)
            .package_name(UPDATED_PACKAGE_NAME);

        restCodegeneratorMockMvc.perform(put("/api/codegenerators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCodegenerator)))
            .andExpect(status().isOk());

        // Validate the Codegenerator in the database
        List<Codegenerator> codegeneratorList = codegeneratorRepository.findAll();
        assertThat(codegeneratorList).hasSize(databaseSizeBeforeUpdate);
        Codegenerator testCodegenerator = codegeneratorList.get(codegeneratorList.size() - 1);
        assertThat(testCodegenerator.getApplication_name()).isEqualTo(UPDATED_APPLICATION_NAME);
        assertThat(testCodegenerator.getPackage_name()).isEqualTo(UPDATED_PACKAGE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCodegenerator() throws Exception {
        int databaseSizeBeforeUpdate = codegeneratorRepository.findAll().size();

        // Create the Codegenerator

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCodegeneratorMockMvc.perform(put("/api/codegenerators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codegenerator)))
            .andExpect(status().isCreated());

        // Validate the Codegenerator in the database
        List<Codegenerator> codegeneratorList = codegeneratorRepository.findAll();
        assertThat(codegeneratorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCodegenerator() throws Exception {
        // Initialize the database
        codegeneratorService.save(codegenerator);

        int databaseSizeBeforeDelete = codegeneratorRepository.findAll().size();

        // Get the codegenerator
        restCodegeneratorMockMvc.perform(delete("/api/codegenerators/{id}", codegenerator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Codegenerator> codegeneratorList = codegeneratorRepository.findAll();
        assertThat(codegeneratorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Codegenerator.class);
        Codegenerator codegenerator1 = new Codegenerator();
        codegenerator1.setId(1L);
        Codegenerator codegenerator2 = new Codegenerator();
        codegenerator2.setId(codegenerator1.getId());
        assertThat(codegenerator1).isEqualTo(codegenerator2);
        codegenerator2.setId(2L);
        assertThat(codegenerator1).isNotEqualTo(codegenerator2);
        codegenerator1.setId(null);
        assertThat(codegenerator1).isNotEqualTo(codegenerator2);
    }
}
