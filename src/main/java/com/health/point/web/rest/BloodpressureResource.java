package com.health.point.web.rest;

import com.health.point.service.BloodpressureService;
import com.health.point.web.rest.errors.BadRequestAlertException;
import com.health.point.service.dto.BloodpressureDTO;

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
 * REST controller for managing {@link com.health.point.domain.Bloodpressure}.
 */
@RestController
@RequestMapping("/api")
public class BloodpressureResource {

    private final Logger log = LoggerFactory.getLogger(BloodpressureResource.class);

    private static final String ENTITY_NAME = "bloodpressure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BloodpressureService bloodpressureService;

    public BloodpressureResource(BloodpressureService bloodpressureService) {
        this.bloodpressureService = bloodpressureService;
    }

    /**
     * {@code POST  /bloodpressures} : Create a new bloodpressure.
     *
     * @param bloodpressureDTO the bloodpressureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bloodpressureDTO, or with status {@code 400 (Bad Request)} if the bloodpressure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bloodpressures")
    public ResponseEntity<BloodpressureDTO> createBloodpressure(@Valid @RequestBody BloodpressureDTO bloodpressureDTO) throws URISyntaxException {
        log.debug("REST request to save Bloodpressure : {}", bloodpressureDTO);
        if (bloodpressureDTO.getId() != null) {
            throw new BadRequestAlertException("A new bloodpressure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BloodpressureDTO result = bloodpressureService.save(bloodpressureDTO);
        return ResponseEntity.created(new URI("/api/bloodpressures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bloodpressures} : Updates an existing bloodpressure.
     *
     * @param bloodpressureDTO the bloodpressureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bloodpressureDTO,
     * or with status {@code 400 (Bad Request)} if the bloodpressureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bloodpressureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bloodpressures")
    public ResponseEntity<BloodpressureDTO> updateBloodpressure(@Valid @RequestBody BloodpressureDTO bloodpressureDTO) throws URISyntaxException {
        log.debug("REST request to update Bloodpressure : {}", bloodpressureDTO);
        if (bloodpressureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BloodpressureDTO result = bloodpressureService.save(bloodpressureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bloodpressureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bloodpressures} : get all the bloodpressures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bloodpressures in body.
     */
    @GetMapping("/bloodpressures")
    public ResponseEntity<List<BloodpressureDTO>> getAllBloodpressures(Pageable pageable) {
        log.debug("REST request to get a page of Bloodpressures");
        Page<BloodpressureDTO> page = bloodpressureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bloodpressures/:id} : get the "id" bloodpressure.
     *
     * @param id the id of the bloodpressureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bloodpressureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bloodpressures/{id}")
    public ResponseEntity<BloodpressureDTO> getBloodpressure(@PathVariable Long id) {
        log.debug("REST request to get Bloodpressure : {}", id);
        Optional<BloodpressureDTO> bloodpressureDTO = bloodpressureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bloodpressureDTO);
    }

    /**
     * {@code DELETE  /bloodpressures/:id} : delete the "id" bloodpressure.
     *
     * @param id the id of the bloodpressureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bloodpressures/{id}")
    public ResponseEntity<Void> deleteBloodpressure(@PathVariable Long id) {
        log.debug("REST request to delete Bloodpressure : {}", id);
        bloodpressureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/bloodpressures?query=:query} : search for the bloodpressure corresponding
     * to the query.
     *
     * @param query the query of the bloodpressure search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/bloodpressures")
    public ResponseEntity<List<BloodpressureDTO>> searchBloodpressures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Bloodpressures for query {}", query);
        Page<BloodpressureDTO> page = bloodpressureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
