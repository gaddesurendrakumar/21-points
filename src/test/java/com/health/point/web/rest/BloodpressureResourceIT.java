package com.health.point.web.rest;

import com.health.point.TwentyOnePointsApp;
import com.health.point.domain.Bloodpressure;
import com.health.point.repository.BloodpressureRepository;
import com.health.point.repository.search.BloodpressureSearchRepository;
import com.health.point.service.BloodpressureService;
import com.health.point.service.dto.BloodpressureDTO;
import com.health.point.service.mapper.BloodpressureMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.health.point.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BloodpressureResource} REST controller.
 */
@SpringBootTest(classes = TwentyOnePointsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BloodpressureResourceIT {

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_SYSTOLIC = 1;
    private static final Integer UPDATED_SYSTOLIC = 2;

    private static final Integer DEFAULT_DIASTOLIC = 1;
    private static final Integer UPDATED_DIASTOLIC = 2;

    @Autowired
    private BloodpressureRepository bloodpressureRepository;

    @Autowired
    private BloodpressureMapper bloodpressureMapper;

    @Autowired
    private BloodpressureService bloodpressureService;

    /**
     * This repository is mocked in the com.health.point.repository.search test package.
     *
     * @see com.health.point.repository.search.BloodpressureSearchRepositoryMockConfiguration
     */
    @Autowired
    private BloodpressureSearchRepository mockBloodpressureSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBloodpressureMockMvc;

    private Bloodpressure bloodpressure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bloodpressure createEntity(EntityManager em) {
        Bloodpressure bloodpressure = new Bloodpressure()
            .timestamp(DEFAULT_TIMESTAMP)
            .systolic(DEFAULT_SYSTOLIC)
            .diastolic(DEFAULT_DIASTOLIC);
        return bloodpressure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bloodpressure createUpdatedEntity(EntityManager em) {
        Bloodpressure bloodpressure = new Bloodpressure()
            .timestamp(UPDATED_TIMESTAMP)
            .systolic(UPDATED_SYSTOLIC)
            .diastolic(UPDATED_DIASTOLIC);
        return bloodpressure;
    }

    @BeforeEach
    public void initTest() {
        bloodpressure = createEntity(em);
    }

    @Test
    @Transactional
    public void createBloodpressure() throws Exception {
        int databaseSizeBeforeCreate = bloodpressureRepository.findAll().size();

        // Create the Bloodpressure
        BloodpressureDTO bloodpressureDTO = bloodpressureMapper.toDto(bloodpressure);
        restBloodpressureMockMvc.perform(post("/api/bloodpressures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bloodpressureDTO)))
            .andExpect(status().isCreated());

        // Validate the Bloodpressure in the database
        List<Bloodpressure> bloodpressureList = bloodpressureRepository.findAll();
        assertThat(bloodpressureList).hasSize(databaseSizeBeforeCreate + 1);
        Bloodpressure testBloodpressure = bloodpressureList.get(bloodpressureList.size() - 1);
        assertThat(testBloodpressure.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testBloodpressure.getSystolic()).isEqualTo(DEFAULT_SYSTOLIC);
        assertThat(testBloodpressure.getDiastolic()).isEqualTo(DEFAULT_DIASTOLIC);

        // Validate the Bloodpressure in Elasticsearch
        verify(mockBloodpressureSearchRepository, times(1)).save(testBloodpressure);
    }

    @Test
    @Transactional
    public void createBloodpressureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bloodpressureRepository.findAll().size();

        // Create the Bloodpressure with an existing ID
        bloodpressure.setId(1L);
        BloodpressureDTO bloodpressureDTO = bloodpressureMapper.toDto(bloodpressure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloodpressureMockMvc.perform(post("/api/bloodpressures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bloodpressureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bloodpressure in the database
        List<Bloodpressure> bloodpressureList = bloodpressureRepository.findAll();
        assertThat(bloodpressureList).hasSize(databaseSizeBeforeCreate);

        // Validate the Bloodpressure in Elasticsearch
        verify(mockBloodpressureSearchRepository, times(0)).save(bloodpressure);
    }


    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = bloodpressureRepository.findAll().size();
        // set the field null
        bloodpressure.setTimestamp(null);

        // Create the Bloodpressure, which fails.
        BloodpressureDTO bloodpressureDTO = bloodpressureMapper.toDto(bloodpressure);

        restBloodpressureMockMvc.perform(post("/api/bloodpressures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bloodpressureDTO)))
            .andExpect(status().isBadRequest());

        List<Bloodpressure> bloodpressureList = bloodpressureRepository.findAll();
        assertThat(bloodpressureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSystolicIsRequired() throws Exception {
        int databaseSizeBeforeTest = bloodpressureRepository.findAll().size();
        // set the field null
        bloodpressure.setSystolic(null);

        // Create the Bloodpressure, which fails.
        BloodpressureDTO bloodpressureDTO = bloodpressureMapper.toDto(bloodpressure);

        restBloodpressureMockMvc.perform(post("/api/bloodpressures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bloodpressureDTO)))
            .andExpect(status().isBadRequest());

        List<Bloodpressure> bloodpressureList = bloodpressureRepository.findAll();
        assertThat(bloodpressureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiastolicIsRequired() throws Exception {
        int databaseSizeBeforeTest = bloodpressureRepository.findAll().size();
        // set the field null
        bloodpressure.setDiastolic(null);

        // Create the Bloodpressure, which fails.
        BloodpressureDTO bloodpressureDTO = bloodpressureMapper.toDto(bloodpressure);

        restBloodpressureMockMvc.perform(post("/api/bloodpressures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bloodpressureDTO)))
            .andExpect(status().isBadRequest());

        List<Bloodpressure> bloodpressureList = bloodpressureRepository.findAll();
        assertThat(bloodpressureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBloodpressures() throws Exception {
        // Initialize the database
        bloodpressureRepository.saveAndFlush(bloodpressure);

        // Get all the bloodpressureList
        restBloodpressureMockMvc.perform(get("/api/bloodpressures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodpressure.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].systolic").value(hasItem(DEFAULT_SYSTOLIC)))
            .andExpect(jsonPath("$.[*].diastolic").value(hasItem(DEFAULT_DIASTOLIC)));
    }
    
    @Test
    @Transactional
    public void getBloodpressure() throws Exception {
        // Initialize the database
        bloodpressureRepository.saveAndFlush(bloodpressure);

        // Get the bloodpressure
        restBloodpressureMockMvc.perform(get("/api/bloodpressures/{id}", bloodpressure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bloodpressure.getId().intValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.systolic").value(DEFAULT_SYSTOLIC))
            .andExpect(jsonPath("$.diastolic").value(DEFAULT_DIASTOLIC));
    }

    @Test
    @Transactional
    public void getNonExistingBloodpressure() throws Exception {
        // Get the bloodpressure
        restBloodpressureMockMvc.perform(get("/api/bloodpressures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBloodpressure() throws Exception {
        // Initialize the database
        bloodpressureRepository.saveAndFlush(bloodpressure);

        int databaseSizeBeforeUpdate = bloodpressureRepository.findAll().size();

        // Update the bloodpressure
        Bloodpressure updatedBloodpressure = bloodpressureRepository.findById(bloodpressure.getId()).get();
        // Disconnect from session so that the updates on updatedBloodpressure are not directly saved in db
        em.detach(updatedBloodpressure);
        updatedBloodpressure
            .timestamp(UPDATED_TIMESTAMP)
            .systolic(UPDATED_SYSTOLIC)
            .diastolic(UPDATED_DIASTOLIC);
        BloodpressureDTO bloodpressureDTO = bloodpressureMapper.toDto(updatedBloodpressure);

        restBloodpressureMockMvc.perform(put("/api/bloodpressures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bloodpressureDTO)))
            .andExpect(status().isOk());

        // Validate the Bloodpressure in the database
        List<Bloodpressure> bloodpressureList = bloodpressureRepository.findAll();
        assertThat(bloodpressureList).hasSize(databaseSizeBeforeUpdate);
        Bloodpressure testBloodpressure = bloodpressureList.get(bloodpressureList.size() - 1);
        assertThat(testBloodpressure.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testBloodpressure.getSystolic()).isEqualTo(UPDATED_SYSTOLIC);
        assertThat(testBloodpressure.getDiastolic()).isEqualTo(UPDATED_DIASTOLIC);

        // Validate the Bloodpressure in Elasticsearch
        verify(mockBloodpressureSearchRepository, times(1)).save(testBloodpressure);
    }

    @Test
    @Transactional
    public void updateNonExistingBloodpressure() throws Exception {
        int databaseSizeBeforeUpdate = bloodpressureRepository.findAll().size();

        // Create the Bloodpressure
        BloodpressureDTO bloodpressureDTO = bloodpressureMapper.toDto(bloodpressure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodpressureMockMvc.perform(put("/api/bloodpressures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bloodpressureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bloodpressure in the database
        List<Bloodpressure> bloodpressureList = bloodpressureRepository.findAll();
        assertThat(bloodpressureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bloodpressure in Elasticsearch
        verify(mockBloodpressureSearchRepository, times(0)).save(bloodpressure);
    }

    @Test
    @Transactional
    public void deleteBloodpressure() throws Exception {
        // Initialize the database
        bloodpressureRepository.saveAndFlush(bloodpressure);

        int databaseSizeBeforeDelete = bloodpressureRepository.findAll().size();

        // Delete the bloodpressure
        restBloodpressureMockMvc.perform(delete("/api/bloodpressures/{id}", bloodpressure.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bloodpressure> bloodpressureList = bloodpressureRepository.findAll();
        assertThat(bloodpressureList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Bloodpressure in Elasticsearch
        verify(mockBloodpressureSearchRepository, times(1)).deleteById(bloodpressure.getId());
    }

    @Test
    @Transactional
    public void searchBloodpressure() throws Exception {
        // Initialize the database
        bloodpressureRepository.saveAndFlush(bloodpressure);
        when(mockBloodpressureSearchRepository.search(queryStringQuery("id:" + bloodpressure.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(bloodpressure), PageRequest.of(0, 1), 1));
        // Search the bloodpressure
        restBloodpressureMockMvc.perform(get("/api/_search/bloodpressures?query=id:" + bloodpressure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodpressure.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].systolic").value(hasItem(DEFAULT_SYSTOLIC)))
            .andExpect(jsonPath("$.[*].diastolic").value(hasItem(DEFAULT_DIASTOLIC)));
    }
}
