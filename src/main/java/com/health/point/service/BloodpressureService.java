package com.health.point.service;

import com.health.point.service.dto.BloodpressureDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.health.point.domain.Bloodpressure}.
 */
public interface BloodpressureService {

    /**
     * Save a bloodpressure.
     *
     * @param bloodpressureDTO the entity to save.
     * @return the persisted entity.
     */
    BloodpressureDTO save(BloodpressureDTO bloodpressureDTO);

    /**
     * Get all the bloodpressures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BloodpressureDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bloodpressure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BloodpressureDTO> findOne(Long id);

    /**
     * Delete the "id" bloodpressure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the bloodpressure corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BloodpressureDTO> search(String query, Pageable pageable);
}
