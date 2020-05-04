package com.health.point.service;

import com.health.point.service.dto.WeightDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.health.point.domain.Weight}.
 */
public interface WeightService {

    /**
     * Save a weight.
     *
     * @param weightDTO the entity to save.
     * @return the persisted entity.
     */
    WeightDTO save(WeightDTO weightDTO);

    /**
     * Get all the weights.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WeightDTO> findAll(Pageable pageable);

    /**
     * Get the "id" weight.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WeightDTO> findOne(Long id);

    /**
     * Delete the "id" weight.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the weight corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WeightDTO> search(String query, Pageable pageable);
}
