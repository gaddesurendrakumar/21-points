package com.health.point.service.impl;

import com.health.point.service.WeightService;
import com.health.point.domain.Weight;
import com.health.point.repository.WeightRepository;
import com.health.point.repository.search.WeightSearchRepository;
import com.health.point.service.dto.WeightDTO;
import com.health.point.service.mapper.WeightMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Weight}.
 */
@Service
@Transactional
public class WeightServiceImpl implements WeightService {

    private final Logger log = LoggerFactory.getLogger(WeightServiceImpl.class);

    private final WeightRepository weightRepository;

    private final WeightMapper weightMapper;

    private final WeightSearchRepository weightSearchRepository;

    public WeightServiceImpl(WeightRepository weightRepository, WeightMapper weightMapper, WeightSearchRepository weightSearchRepository) {
        this.weightRepository = weightRepository;
        this.weightMapper = weightMapper;
        this.weightSearchRepository = weightSearchRepository;
    }

    /**
     * Save a weight.
     *
     * @param weightDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WeightDTO save(WeightDTO weightDTO) {
        log.debug("Request to save Weight : {}", weightDTO);
        Weight weight = weightMapper.toEntity(weightDTO);
        weight = weightRepository.save(weight);
        WeightDTO result = weightMapper.toDto(weight);
        weightSearchRepository.save(weight);
        return result;
    }

    /**
     * Get all the weights.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WeightDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Weights");
        return weightRepository.findAll(pageable)
            .map(weightMapper::toDto);
    }

    /**
     * Get one weight by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WeightDTO> findOne(Long id) {
        log.debug("Request to get Weight : {}", id);
        return weightRepository.findById(id)
            .map(weightMapper::toDto);
    }

    /**
     * Delete the weight by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Weight : {}", id);
        weightRepository.deleteById(id);
        weightSearchRepository.deleteById(id);
    }

    /**
     * Search for the weight corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WeightDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Weights for query {}", query);
        return weightSearchRepository.search(queryStringQuery(query), pageable)
            .map(weightMapper::toDto);
    }
}
