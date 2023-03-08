package com.weather.service;

import com.weather.domain.Current;
import com.weather.repository.CurrentRepository;
import com.weather.service.dto.CurrentDTO;
import com.weather.service.mapper.CurrentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Current}.
 */
@Service
@Transactional
public class CurrentService {

    private final Logger log = LoggerFactory.getLogger(CurrentService.class);

    private final CurrentRepository currentRepository;

    private final CurrentMapper currentMapper;

    public CurrentService(CurrentRepository currentRepository, CurrentMapper currentMapper) {
        this.currentRepository = currentRepository;
        this.currentMapper = currentMapper;
    }

    /**
     * Save a current.
     *
     * @param currentDTO the entity to save.
     * @return the persisted entity.
     */
    public CurrentDTO save(CurrentDTO currentDTO) {
        log.debug("Request to save Current : {}", currentDTO);
        Current current = currentMapper.toEntity(currentDTO);
        current = currentRepository.save(current);
        return currentMapper.toDto(current);
    }

    /**
     * Update a current.
     *
     * @param currentDTO the entity to save.
     * @return the persisted entity.
     */
    public CurrentDTO update(CurrentDTO currentDTO) {
        log.debug("Request to update Current : {}", currentDTO);
        Current current = currentMapper.toEntity(currentDTO);
        current = currentRepository.save(current);
        return currentMapper.toDto(current);
    }

    /**
     * Partially update a current.
     *
     * @param currentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CurrentDTO> partialUpdate(CurrentDTO currentDTO) {
        log.debug("Request to partially update Current : {}", currentDTO);

        return currentRepository
            .findById(currentDTO.getId())
            .map(existingCurrent -> {
                currentMapper.partialUpdate(existingCurrent, currentDTO);

                return existingCurrent;
            })
            .map(currentRepository::save)
            .map(currentMapper::toDto);
    }

    /**
     * Get all the currents.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CurrentDTO> findAll() {
        log.debug("Request to get all Currents");
        return currentRepository.findAll().stream().map(currentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one current by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CurrentDTO> findOne(Long id) {
        log.debug("Request to get Current : {}", id);
        return currentRepository.findById(id).map(currentMapper::toDto);
    }

    /**
     * Delete the current by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Current : {}", id);
        currentRepository.deleteById(id);
    }
}
