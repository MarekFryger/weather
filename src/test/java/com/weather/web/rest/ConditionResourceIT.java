package com.weather.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.weather.IntegrationTest;
import com.weather.domain.Condition;
import com.weather.repository.ConditionRepository;
import com.weather.service.dto.ConditionDTO;
import com.weather.service.mapper.ConditionMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ConditionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConditionResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_LINK = "AAAAAAAAAA";
    private static final String UPDATED_ICON_LINK = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String ENTITY_API_URL = "/api/conditions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private ConditionMapper conditionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConditionMockMvc;

    private Condition condition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createEntity(EntityManager em) {
        Condition condition = new Condition().description(DEFAULT_DESCRIPTION).iconLink(DEFAULT_ICON_LINK).code(DEFAULT_CODE);
        return condition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createUpdatedEntity(EntityManager em) {
        Condition condition = new Condition().description(UPDATED_DESCRIPTION).iconLink(UPDATED_ICON_LINK).code(UPDATED_CODE);
        return condition;
    }

    @BeforeEach
    public void initTest() {
        condition = createEntity(em);
    }

    @Test
    @Transactional
    void createCondition() throws Exception {
        int databaseSizeBeforeCreate = conditionRepository.findAll().size();
        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);
        restConditionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate + 1);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCondition.getIconLink()).isEqualTo(DEFAULT_ICON_LINK);
        assertThat(testCondition.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createConditionWithExistingId() throws Exception {
        // Create the Condition with an existing ID
        condition.setId(1L);
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        int databaseSizeBeforeCreate = conditionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConditions() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList
        restConditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].iconLink").value(hasItem(DEFAULT_ICON_LINK)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get the condition
        restConditionMockMvc
            .perform(get(ENTITY_API_URL_ID, condition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(condition.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.iconLink").value(DEFAULT_ICON_LINK))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingCondition() throws Exception {
        // Get the condition
        restConditionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition
        Condition updatedCondition = conditionRepository.findById(condition.getId()).get();
        // Disconnect from session so that the updates on updatedCondition are not directly saved in db
        em.detach(updatedCondition);
        updatedCondition.description(UPDATED_DESCRIPTION).iconLink(UPDATED_ICON_LINK).code(UPDATED_CODE);
        ConditionDTO conditionDTO = conditionMapper.toDto(updatedCondition);

        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conditionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCondition.getIconLink()).isEqualTo(UPDATED_ICON_LINK);
        assertThat(testCondition.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conditionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConditionWithPatch() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition using partial update
        Condition partialUpdatedCondition = new Condition();
        partialUpdatedCondition.setId(condition.getId());

        partialUpdatedCondition.description(UPDATED_DESCRIPTION);

        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCondition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCondition.getIconLink()).isEqualTo(DEFAULT_ICON_LINK);
        assertThat(testCondition.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateConditionWithPatch() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition using partial update
        Condition partialUpdatedCondition = new Condition();
        partialUpdatedCondition.setId(condition.getId());

        partialUpdatedCondition.description(UPDATED_DESCRIPTION).iconLink(UPDATED_ICON_LINK).code(UPDATED_CODE);

        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCondition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCondition.getIconLink()).isEqualTo(UPDATED_ICON_LINK);
        assertThat(testCondition.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conditionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeDelete = conditionRepository.findAll().size();

        // Delete the condition
        restConditionMockMvc
            .perform(delete(ENTITY_API_URL_ID, condition.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
