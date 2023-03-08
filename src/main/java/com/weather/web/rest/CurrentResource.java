package com.weather.web.rest;

import com.weather.repository.CurrentRepository;
import com.weather.service.CurrentService;
import com.weather.service.dto.CurrentDTO;
import com.weather.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.weather.domain.Current}.
 */
@RestController
@RequestMapping("/api")
public class CurrentResource {

    private final Logger log = LoggerFactory.getLogger(CurrentResource.class);

    private static final String ENTITY_NAME = "current";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrentService currentService;

    private final CurrentRepository currentRepository;

    public CurrentResource(CurrentService currentService, CurrentRepository currentRepository) {
        this.currentService = currentService;
        this.currentRepository = currentRepository;
    }

    /**
     * {@code POST  /currents} : Create a new current.
     *
     * @param currentDTO the currentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currentDTO, or with status {@code 400 (Bad Request)} if the current has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/currents")
    public ResponseEntity<CurrentDTO> createCurrent(@RequestBody CurrentDTO currentDTO) throws URISyntaxException {
        log.debug("REST request to save Current : {}", currentDTO);
        if (currentDTO.getId() != null) {
            throw new BadRequestAlertException("A new current cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrentDTO result = currentService.save(currentDTO);
        return ResponseEntity
            .created(new URI("/api/currents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /currents/:id} : Updates an existing current.
     *
     * @param id the id of the currentDTO to save.
     * @param currentDTO the currentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentDTO,
     * or with status {@code 400 (Bad Request)} if the currentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/currents/{id}")
    public ResponseEntity<CurrentDTO> updateCurrent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CurrentDTO currentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Current : {}, {}", id, currentDTO);
        if (currentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CurrentDTO result = currentService.update(currentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /currents/:id} : Partial updates given fields of an existing current, field will ignore if it is null
     *
     * @param id the id of the currentDTO to save.
     * @param currentDTO the currentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentDTO,
     * or with status {@code 400 (Bad Request)} if the currentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the currentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the currentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/currents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CurrentDTO> partialUpdateCurrent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CurrentDTO currentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Current partially : {}, {}", id, currentDTO);
        if (currentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CurrentDTO> result = currentService.partialUpdate(currentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /currents} : get all the currents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currents in body.
     */
    @GetMapping("/currents")
    public List<CurrentDTO> getAllCurrents() {
        log.debug("REST request to get all Currents");
        return currentService.findAll();
    }

    /**
     * {@code GET  /currents/:id} : get the "id" current.
     *
     * @param id the id of the currentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/currents/{id}")
    public ResponseEntity<CurrentDTO> getCurrent(@PathVariable Long id) {
        log.debug("REST request to get Current : {}", id);
        Optional<CurrentDTO> currentDTO = currentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currentDTO);
    }

    /**
     * {@code DELETE  /currents/:id} : delete the "id" current.
     *
     * @param id the id of the currentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/currents/{id}")
    public ResponseEntity<Void> deleteCurrent(@PathVariable Long id) {
        log.debug("REST request to delete Current : {}", id);
        currentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
