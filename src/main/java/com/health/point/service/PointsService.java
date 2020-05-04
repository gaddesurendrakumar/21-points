package com.health.point.service;

import com.health.point.service.dto.PointsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.health.point.domain.Points}.
 */
public interface PointsService {

    /**
     * Save a points.
     *
     * @param pointsDTO the entity to save.
     * @return the persisted entity.
     */
    PointsDTO save(PointsDTO pointsDTO);

    /**
     * Get all the points.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PointsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" points.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PointsDTO> findOne(Long id);

    /**
     * Delete the "id" points.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the points corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PointsDTO> search(String query, Pageable pageable);
}
