package com.weather.service;

import com.weather.domain.Condition;
import com.weather.repository.ConditionRepository;
import com.weather.service.dto.ConditionDTO;
import com.weather.service.mapper.ConditionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Condition}.
 */
@Service
@Transactional
public class ConditionService {

    private final Logger log = LoggerFactory.getLogger(ConditionService.class);

    private final ConditionRepository conditionRepository;

    private final ConditionMapper conditionMapper;

    public ConditionService(ConditionRepository conditionRepository, ConditionMapper conditionMapper) {
        this.conditionRepository = conditionRepository;
        this.conditionMapper = conditionMapper;
    }

    /**
     * Save a condition.
     *
     * @param conditionDTO the entity to save.
     * @return the persisted entity.
     */
    public ConditionDTO save(ConditionDTO conditionDTO) {
        log.debug("Request to save Condition : {}", conditionDTO);
        Condition condition = conditionMapper.toEntity(conditionDTO);
        condition = conditionRepository.save(condition);
        return conditionMapper.toDto(condition);
    }

    /**
     * Update a condition.
     *
     * @param conditionDTO the entity to save.
     * @return the persisted entity.
     */
    public ConditionDTO update(ConditionDTO conditionDTO) {
        log.debug("Request to update Condition : {}", conditionDTO);
        Condition condition = conditionMapper.toEntity(conditionDTO);
        condition = conditionRepository.save(condition);
        return conditionMapper.toDto(condition);
    }

    /**
     * Partially update a condition.
     *
     * @param conditionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConditionDTO> partialUpdate(ConditionDTO conditionDTO) {
        log.debug("Request to partially update Condition : {}", conditionDTO);

        return conditionRepository
            .findById(conditionDTO.getId())
            .map(existingCondition -> {
                conditionMapper.partialUpdate(existingCondition, conditionDTO);

                return existingCondition;
            })
            .map(conditionRepository::save)
            .map(conditionMapper::toDto);
    }

    /**
     * Get all the conditions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConditionDTO> findAll() {
        log.debug("Request to get all Conditions");
        return conditionRepository.findAll().stream().map(conditionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one condition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConditionDTO> findOne(Long id) {
        log.debug("Request to get Condition : {}", id);
        return conditionRepository.findById(id).map(conditionMapper::toDto);
    }

    /**
     * Delete the condition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Condition : {}", id);
        conditionRepository.deleteById(id);
    }
}
