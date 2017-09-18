package com.cloudbaboons.web.rest;

import com.cloudbaboons.CloudbaboonsApp;

import com.cloudbaboons.domain.Configurations;
import com.cloudbaboons.repository.ConfigurationsRepository;
import com.cloudbaboons.service.ConfigurationsService;
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
 * Test class for the ConfigurationsResource REST controller.
 *
 * @see ConfigurationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudbaboonsApp.class)
public class ConfigurationsResourceIntTest {

    private static final String DEFAULT_GITURL = "AAAAAAAAAA";
    private static final String UPDATED_GITURL = "BBBBBBBBBB";

    private static final String DEFAULT_GITUSERNAME = "AAAAAAAAAA";
    private static final String UPDATED_GITUSERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_GITPASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_GITPASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_JENKINSURL = "AAAAAAAAAA";
    private static final String UPDATED_JENKINSURL = "BBBBBBBBBB";

    private static final String DEFAULT_JENKINSUSERNAME = "AAAAAAAAAA";
    private static final String UPDATED_JENKINSUSERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_JENKINSPASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_JENKINSPASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_APPNAME = "AAAAAAAAAA";
    private static final String UPDATED_APPNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORGNAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGNAME = "BBBBBBBBBB";

    @Autowired
    private ConfigurationsRepository configurationsRepository;

    @Autowired
    private ConfigurationsService configurationsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConfigurationsMockMvc;

    private Configurations configurations;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConfigurationsResource configurationsResource = new ConfigurationsResource(configurationsService);
        this.restConfigurationsMockMvc = MockMvcBuilders.standaloneSetup(configurationsResource)
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
    public static Configurations createEntity(EntityManager em) {
        Configurations configurations = new Configurations()
            .giturl(DEFAULT_GITURL)
            .gitusername(DEFAULT_GITUSERNAME)
            .gitpassword(DEFAULT_GITPASSWORD)
            .jenkinsurl(DEFAULT_JENKINSURL)
            .jenkinsusername(DEFAULT_JENKINSUSERNAME)
            .jenkinspassword(DEFAULT_JENKINSPASSWORD)
            .appname(DEFAULT_APPNAME)
            .orgname(DEFAULT_ORGNAME);
        return configurations;
    }

    @Before
    public void initTest() {
        configurations = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfigurations() throws Exception {
        int databaseSizeBeforeCreate = configurationsRepository.findAll().size();

        // Create the Configurations
        restConfigurationsMockMvc.perform(post("/api/configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurations)))
            .andExpect(status().isCreated());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeCreate + 1);
        Configurations testConfigurations = configurationsList.get(configurationsList.size() - 1);
        assertThat(testConfigurations.getGiturl()).isEqualTo(DEFAULT_GITURL);
        assertThat(testConfigurations.getGitusername()).isEqualTo(DEFAULT_GITUSERNAME);
        assertThat(testConfigurations.getGitpassword()).isEqualTo(DEFAULT_GITPASSWORD);
        assertThat(testConfigurations.getJenkinsurl()).isEqualTo(DEFAULT_JENKINSURL);
        assertThat(testConfigurations.getJenkinsusername()).isEqualTo(DEFAULT_JENKINSUSERNAME);
        assertThat(testConfigurations.getJenkinspassword()).isEqualTo(DEFAULT_JENKINSPASSWORD);
        assertThat(testConfigurations.getAppname()).isEqualTo(DEFAULT_APPNAME);
        assertThat(testConfigurations.getOrgname()).isEqualTo(DEFAULT_ORGNAME);
    }

    @Test
    @Transactional
    public void createConfigurationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = configurationsRepository.findAll().size();

        // Create the Configurations with an existing ID
        configurations.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigurationsMockMvc.perform(post("/api/configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurations)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConfigurations() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList
        restConfigurationsMockMvc.perform(get("/api/configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configurations.getId().intValue())))
            .andExpect(jsonPath("$.[*].giturl").value(hasItem(DEFAULT_GITURL.toString())))
            .andExpect(jsonPath("$.[*].gitusername").value(hasItem(DEFAULT_GITUSERNAME.toString())))
            .andExpect(jsonPath("$.[*].gitpassword").value(hasItem(DEFAULT_GITPASSWORD.toString())))
            .andExpect(jsonPath("$.[*].jenkinsurl").value(hasItem(DEFAULT_JENKINSURL.toString())))
            .andExpect(jsonPath("$.[*].jenkinsusername").value(hasItem(DEFAULT_JENKINSUSERNAME.toString())))
            .andExpect(jsonPath("$.[*].jenkinspassword").value(hasItem(DEFAULT_JENKINSPASSWORD.toString())))
            .andExpect(jsonPath("$.[*].appname").value(hasItem(DEFAULT_APPNAME.toString())))
            .andExpect(jsonPath("$.[*].orgname").value(hasItem(DEFAULT_ORGNAME.toString())));
    }

    @Test
    @Transactional
    public void getConfigurations() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get the configurations
        restConfigurationsMockMvc.perform(get("/api/configurations/{id}", configurations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(configurations.getId().intValue()))
            .andExpect(jsonPath("$.giturl").value(DEFAULT_GITURL.toString()))
            .andExpect(jsonPath("$.gitusername").value(DEFAULT_GITUSERNAME.toString()))
            .andExpect(jsonPath("$.gitpassword").value(DEFAULT_GITPASSWORD.toString()))
            .andExpect(jsonPath("$.jenkinsurl").value(DEFAULT_JENKINSURL.toString()))
            .andExpect(jsonPath("$.jenkinsusername").value(DEFAULT_JENKINSUSERNAME.toString()))
            .andExpect(jsonPath("$.jenkinspassword").value(DEFAULT_JENKINSPASSWORD.toString()))
            .andExpect(jsonPath("$.appname").value(DEFAULT_APPNAME.toString()))
            .andExpect(jsonPath("$.orgname").value(DEFAULT_ORGNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConfigurations() throws Exception {
        // Get the configurations
        restConfigurationsMockMvc.perform(get("/api/configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfigurations() throws Exception {
        // Initialize the database
        configurationsService.save(configurations);

        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();

        // Update the configurations
        Configurations updatedConfigurations = configurationsRepository.findOne(configurations.getId());
        updatedConfigurations
            .giturl(UPDATED_GITURL)
            .gitusername(UPDATED_GITUSERNAME)
            .gitpassword(UPDATED_GITPASSWORD)
            .jenkinsurl(UPDATED_JENKINSURL)
            .jenkinsusername(UPDATED_JENKINSUSERNAME)
            .jenkinspassword(UPDATED_JENKINSPASSWORD)
            .appname(UPDATED_APPNAME)
            .orgname(UPDATED_ORGNAME);

        restConfigurationsMockMvc.perform(put("/api/configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConfigurations)))
            .andExpect(status().isOk());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
        Configurations testConfigurations = configurationsList.get(configurationsList.size() - 1);
        assertThat(testConfigurations.getGiturl()).isEqualTo(UPDATED_GITURL);
        assertThat(testConfigurations.getGitusername()).isEqualTo(UPDATED_GITUSERNAME);
        assertThat(testConfigurations.getGitpassword()).isEqualTo(UPDATED_GITPASSWORD);
        assertThat(testConfigurations.getJenkinsurl()).isEqualTo(UPDATED_JENKINSURL);
        assertThat(testConfigurations.getJenkinsusername()).isEqualTo(UPDATED_JENKINSUSERNAME);
        assertThat(testConfigurations.getJenkinspassword()).isEqualTo(UPDATED_JENKINSPASSWORD);
        assertThat(testConfigurations.getAppname()).isEqualTo(UPDATED_APPNAME);
        assertThat(testConfigurations.getOrgname()).isEqualTo(UPDATED_ORGNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();

        // Create the Configurations

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConfigurationsMockMvc.perform(put("/api/configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurations)))
            .andExpect(status().isCreated());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConfigurations() throws Exception {
        // Initialize the database
        configurationsService.save(configurations);

        int databaseSizeBeforeDelete = configurationsRepository.findAll().size();

        // Get the configurations
        restConfigurationsMockMvc.perform(delete("/api/configurations/{id}", configurations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Configurations.class);
        Configurations configurations1 = new Configurations();
        configurations1.setId(1L);
        Configurations configurations2 = new Configurations();
        configurations2.setId(configurations1.getId());
        assertThat(configurations1).isEqualTo(configurations2);
        configurations2.setId(2L);
        assertThat(configurations1).isNotEqualTo(configurations2);
        configurations1.setId(null);
        assertThat(configurations1).isNotEqualTo(configurations2);
    }
}
