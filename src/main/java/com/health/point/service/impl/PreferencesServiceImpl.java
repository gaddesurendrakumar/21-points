package com.health.point.service.impl;

import com.health.point.service.PreferencesService;
import com.health.point.domain.Preferences;
import com.health.point.repository.PreferencesRepository;
import com.health.point.repository.search.PreferencesSearchRepository;
import com.health.point.service.dto.PreferencesDTO;
import com.health.point.service.mapper.PreferencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Preferences}.
 */
@Service
@Transactional
public class PreferencesServiceImpl implements PreferencesService {

    private final Logger log = LoggerFactory.getLogger(PreferencesServiceImpl.class);

    private final PreferencesRepository preferencesRepository;

    private final PreferencesMapper preferencesMapper;

    private final PreferencesSearchRepository preferencesSearchRepository;

    public PreferencesServiceImpl(PreferencesRepository preferencesRepository, PreferencesMapper preferencesMapper, PreferencesSearchRepository preferencesSearchRepository) {
        this.preferencesRepository = preferencesRepository;
        this.preferencesMapper = preferencesMapper;
        this.preferencesSearchRepository = preferencesSearchRepository;
    }

    /**
     * Save a preferences.
     *
     * @param preferencesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PreferencesDTO save(PreferencesDTO preferencesDTO) {
        log.debug("Request to save Preferences : {}", preferencesDTO);
        Preferences preferences = preferencesMapper.toEntity(preferencesDTO);
        preferences = preferencesRepository.save(preferences);
        PreferencesDTO result = preferencesMapper.toDto(preferences);
        preferencesSearchRepository.save(preferences);
        return result;
    }

    /**
     * Get all the preferences.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PreferencesDTO> findAll() {
        log.debug("Request to get all Preferences");
        return preferencesRepository.findAll().stream()
            .map(preferencesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one preferences by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PreferencesDTO> findOne(Long id) {
        log.debug("Request to get Preferences : {}", id);
        return preferencesRepository.findById(id)
            .map(preferencesMapper::toDto);
    }

    /**
     * Delete the preferences by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Preferences : {}", id);
        preferencesRepository.deleteById(id);
        preferencesSearchRepository.deleteById(id);
    }

    /**
     * Search for the preferences corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PreferencesDTO> search(String query) {
        log.debug("Request to search Preferences for query {}", query);
        return StreamSupport
            .stream(preferencesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(preferencesMapper::toDto)
            .collect(Collectors.toList());
    }
}
