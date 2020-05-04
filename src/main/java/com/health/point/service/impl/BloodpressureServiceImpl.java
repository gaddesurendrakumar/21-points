package com.health.point.service.impl;

import com.health.point.service.BloodpressureService;
import com.health.point.domain.Bloodpressure;
import com.health.point.repository.BloodpressureRepository;
import com.health.point.repository.search.BloodpressureSearchRepository;
import com.health.point.service.dto.BloodpressureDTO;
import com.health.point.service.mapper.BloodpressureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Bloodpressure}.
 */
@Service
@Transactional
public class BloodpressureServiceImpl implements BloodpressureService {

    private final Logger log = LoggerFactory.getLogger(BloodpressureServiceImpl.class);

    private final BloodpressureRepository bloodpressureRepository;

    private final BloodpressureMapper bloodpressureMapper;

    private final BloodpressureSearchRepository bloodpressureSearchRepository;

    public BloodpressureServiceImpl(BloodpressureRepository bloodpressureRepository, BloodpressureMapper bloodpressureMapper, BloodpressureSearchRepository bloodpressureSearchRepository) {
        this.bloodpressureRepository = bloodpressureRepository;
        this.bloodpressureMapper = bloodpressureMapper;
        this.bloodpressureSearchRepository = bloodpressureSearchRepository;
    }

    /**
     * Save a bloodpressure.
     *
     * @param bloodpressureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BloodpressureDTO save(BloodpressureDTO bloodpressureDTO) {
        log.debug("Request to save Bloodpressure : {}", bloodpressureDTO);
        Bloodpressure bloodpressure = bloodpressureMapper.toEntity(bloodpressureDTO);
        bloodpressure = bloodpressureRepository.save(bloodpressure);
        BloodpressureDTO result = bloodpressureMapper.toDto(bloodpressure);
        bloodpressureSearchRepository.save(bloodpressure);
        return result;
    }

    /**
     * Get all the bloodpressures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BloodpressureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bloodpressures");
        return bloodpressureRepository.findAll(pageable)
            .map(bloodpressureMapper::toDto);
    }

    /**
     * Get one bloodpressure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BloodpressureDTO> findOne(Long id) {
        log.debug("Request to get Bloodpressure : {}", id);
        return bloodpressureRepository.findById(id)
            .map(bloodpressureMapper::toDto);
    }

    /**
     * Delete the bloodpressure by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bloodpressure : {}", id);
        bloodpressureRepository.deleteById(id);
        bloodpressureSearchRepository.deleteById(id);
    }

    /**
     * Search for the bloodpressure corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BloodpressureDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Bloodpressures for query {}", query);
        return bloodpressureSearchRepository.search(queryStringQuery(query), pageable)
            .map(bloodpressureMapper::toDto);
    }
}
