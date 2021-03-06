package com.health.point.web.rest;

import com.health.point.service.PointsService;
import com.health.point.web.rest.errors.BadRequestAlertException;
import com.health.point.service.dto.PointsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.health.point.domain.Points}.
 */
@RestController
@RequestMapping("/api")
public class PointsResource {

    private final Logger log = LoggerFactory.getLogger(PointsResource.class);

    private static final String ENTITY_NAME = "points";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointsService pointsService;

    public PointsResource(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    /**
     * {@code POST  /points} : Create a new points.
     *
     * @param pointsDTO the pointsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointsDTO, or with status {@code 400 (Bad Request)} if the points has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/points")
    public ResponseEntity<PointsDTO> createPoints(@Valid @RequestBody PointsDTO pointsDTO) throws URISyntaxException {
        log.debug("REST request to save Points : {}", pointsDTO);
        if (pointsDTO.getId() != null) {
            throw new BadRequestAlertException("A new points cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PointsDTO result = pointsService.save(pointsDTO);
        return ResponseEntity.created(new URI("/api/points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /points} : Updates an existing points.
     *
     * @param pointsDTO the pointsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointsDTO,
     * or with status {@code 400 (Bad Request)} if the pointsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/points")
    public ResponseEntity<PointsDTO> updatePoints(@Valid @RequestBody PointsDTO pointsDTO) throws URISyntaxException {
        log.debug("REST request to update Points : {}", pointsDTO);
        if (pointsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PointsDTO result = pointsService.save(pointsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /points} : get all the points.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of points in body.
     */
    @GetMapping("/points")
    public ResponseEntity<List<PointsDTO>> getAllPoints(Pageable pageable) {
        log.debug("REST request to get a page of Points");
        Page<PointsDTO> page = pointsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /points/:id} : get the "id" points.
     *
     * @param id the id of the pointsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/points/{id}")
    public ResponseEntity<PointsDTO> getPoints(@PathVariable Long id) {
        log.debug("REST request to get Points : {}", id);
        Optional<PointsDTO> pointsDTO = pointsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointsDTO);
    }

    /**
     * {@code DELETE  /points/:id} : delete the "id" points.
     *
     * @param id the id of the pointsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/points/{id}")
    public ResponseEntity<Void> deletePoints(@PathVariable Long id) {
        log.debug("REST request to delete Points : {}", id);
        pointsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/points?query=:query} : search for the points corresponding
     * to the query.
     *
     * @param query the query of the points search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/points")
    public ResponseEntity<List<PointsDTO>> searchPoints(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Points for query {}", query);
        Page<PointsDTO> page = pointsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
