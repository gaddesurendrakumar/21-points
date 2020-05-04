package com.health.point.web.rest;

import com.health.point.service.PreferencesService;
import com.health.point.web.rest.errors.BadRequestAlertException;
import com.health.point.service.dto.PreferencesDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link com.health.point.domain.Preferences}.
 */
@RestController
@RequestMapping("/api")
public class PreferencesResource {

    private final Logger log = LoggerFactory.getLogger(PreferencesResource.class);

    private final PreferencesService preferencesService;

    public PreferencesResource(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }

    /**
     * {@code GET  /preferences} : get all the preferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferences in body.
     */
    @GetMapping("/preferences")
    public List<PreferencesDTO> getAllPreferences() {
        log.debug("REST request to get all Preferences");
        return preferencesService.findAll();
    }

    /**
     * {@code GET  /preferences/:id} : get the "id" preferences.
     *
     * @param id the id of the preferencesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferencesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferences/{id}")
    public ResponseEntity<PreferencesDTO> getPreferences(@PathVariable Long id) {
        log.debug("REST request to get Preferences : {}", id);
        Optional<PreferencesDTO> preferencesDTO = preferencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferencesDTO);
    }

    /**
     * {@code SEARCH  /_search/preferences?query=:query} : search for the preferences corresponding
     * to the query.
     *
     * @param query the query of the preferences search.
     * @return the result of the search.
     */
    @GetMapping("/_search/preferences")
    public List<PreferencesDTO> searchPreferences(@RequestParam String query) {
        log.debug("REST request to search Preferences for query {}", query);
        return preferencesService.search(query);
    }
}
