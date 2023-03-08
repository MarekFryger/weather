package com.weather.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.weather.IntegrationTest;
import com.weather.domain.Current;
import com.weather.repository.CurrentRepository;
import com.weather.service.dto.CurrentDTO;
import com.weather.service.mapper.CurrentMapper;
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
 * Integration tests for the {@link CurrentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CurrentResourceIT {

    private static final Long DEFAULT_LAST_UPDATE_LONG = 1L;
    private static final Long UPDATED_LAST_UPDATE_LONG = 2L;

    private static final String DEFAULT_LAST_UPDATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATE_TIME = "BBBBBBBBBB";

    private static final Double DEFAULT_TEMP_C = 1D;
    private static final Double UPDATED_TEMP_C = 2D;

    private static final Double DEFAULT_TEMP_F = 1D;
    private static final Double UPDATED_TEMP_F = 2D;

    private static final Boolean DEFAULT_IS_DAY = false;
    private static final Boolean UPDATED_IS_DAY = true;

    private static final Double DEFAULT_WIND_MPH = 1D;
    private static final Double UPDATED_WIND_MPH = 2D;

    private static final Double DEFAULT_WIND_KPH = 1D;
    private static final Double UPDATED_WIND_KPH = 2D;

    private static final Integer DEFAULT_WIND_DEGREE = 1;
    private static final Integer UPDATED_WIND_DEGREE = 2;

    private static final String DEFAULT_WIND_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_WIND_DIRECTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRESSURE_MB = 1;
    private static final Integer UPDATED_PRESSURE_MB = 2;

    private static final Integer DEFAULT_PRESSURE_IN = 1;
    private static final Integer UPDATED_PRESSURE_IN = 2;

    private static final Double DEFAULT_PRECIP_MM = 1D;
    private static final Double UPDATED_PRECIP_MM = 2D;

    private static final Double DEFAULT_PRECIP_IN = 1D;
    private static final Double UPDATED_PRECIP_IN = 2D;

    private static final Double DEFAULT_HUMIDITY = 1D;
    private static final Double UPDATED_HUMIDITY = 2D;

    private static final Integer DEFAULT_CLOUD = 1;
    private static final Integer UPDATED_CLOUD = 2;

    private static final Double DEFAULT_FEELSLIKE_C = 1D;
    private static final Double UPDATED_FEELSLIKE_C = 2D;

    private static final Double DEFAULT_FEELSLIKE_F = 1D;
    private static final Double UPDATED_FEELSLIKE_F = 2D;

    private static final Integer DEFAULT_UV = 1;
    private static final Integer UPDATED_UV = 2;

    private static final Double DEFAULT_GUST_MPH = 1D;
    private static final Double UPDATED_GUST_MPH = 2D;

    private static final Double DEFAULT_GUST_KPH = 1D;
    private static final Double UPDATED_GUST_KPH = 2D;

    private static final String ENTITY_API_URL = "/api/currents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CurrentRepository currentRepository;

    @Autowired
    private CurrentMapper currentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrentMockMvc;

    private Current current;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Current createEntity(EntityManager em) {
        Current current = new Current()
            .lastUpdateLong(DEFAULT_LAST_UPDATE_LONG)
            .lastUpdateTime(DEFAULT_LAST_UPDATE_TIME)
            .tempC(DEFAULT_TEMP_C)
            .tempF(DEFAULT_TEMP_F)
            .isDay(DEFAULT_IS_DAY)
            .windMph(DEFAULT_WIND_MPH)
            .windKph(DEFAULT_WIND_KPH)
            .windDegree(DEFAULT_WIND_DEGREE)
            .windDirection(DEFAULT_WIND_DIRECTION)
            .pressureMb(DEFAULT_PRESSURE_MB)
            .pressureIn(DEFAULT_PRESSURE_IN)
            .precipMm(DEFAULT_PRECIP_MM)
            .precipIn(DEFAULT_PRECIP_IN)
            .humidity(DEFAULT_HUMIDITY)
            .cloud(DEFAULT_CLOUD)
            .feelslikeC(DEFAULT_FEELSLIKE_C)
            .feelslikeF(DEFAULT_FEELSLIKE_F)
            .uv(DEFAULT_UV)
            .gustMph(DEFAULT_GUST_MPH)
            .gustKph(DEFAULT_GUST_KPH);
        return current;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Current createUpdatedEntity(EntityManager em) {
        Current current = new Current()
            .lastUpdateLong(UPDATED_LAST_UPDATE_LONG)
            .lastUpdateTime(UPDATED_LAST_UPDATE_TIME)
            .tempC(UPDATED_TEMP_C)
            .tempF(UPDATED_TEMP_F)
            .isDay(UPDATED_IS_DAY)
            .windMph(UPDATED_WIND_MPH)
            .windKph(UPDATED_WIND_KPH)
            .windDegree(UPDATED_WIND_DEGREE)
            .windDirection(UPDATED_WIND_DIRECTION)
            .pressureMb(UPDATED_PRESSURE_MB)
            .pressureIn(UPDATED_PRESSURE_IN)
            .precipMm(UPDATED_PRECIP_MM)
            .precipIn(UPDATED_PRECIP_IN)
            .humidity(UPDATED_HUMIDITY)
            .cloud(UPDATED_CLOUD)
            .feelslikeC(UPDATED_FEELSLIKE_C)
            .feelslikeF(UPDATED_FEELSLIKE_F)
            .uv(UPDATED_UV)
            .gustMph(UPDATED_GUST_MPH)
            .gustKph(UPDATED_GUST_KPH);
        return current;
    }

    @BeforeEach
    public void initTest() {
        current = createEntity(em);
    }

    @Test
    @Transactional
    void createCurrent() throws Exception {
        int databaseSizeBeforeCreate = currentRepository.findAll().size();
        // Create the Current
        CurrentDTO currentDTO = currentMapper.toDto(current);
        restCurrentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeCreate + 1);
        Current testCurrent = currentList.get(currentList.size() - 1);
        assertThat(testCurrent.getLastUpdateLong()).isEqualTo(DEFAULT_LAST_UPDATE_LONG);
        assertThat(testCurrent.getLastUpdateTime()).isEqualTo(DEFAULT_LAST_UPDATE_TIME);
        assertThat(testCurrent.getTempC()).isEqualTo(DEFAULT_TEMP_C);
        assertThat(testCurrent.getTempF()).isEqualTo(DEFAULT_TEMP_F);
        assertThat(testCurrent.getIsDay()).isEqualTo(DEFAULT_IS_DAY);
        assertThat(testCurrent.getWindMph()).isEqualTo(DEFAULT_WIND_MPH);
        assertThat(testCurrent.getWindKph()).isEqualTo(DEFAULT_WIND_KPH);
        assertThat(testCurrent.getWindDegree()).isEqualTo(DEFAULT_WIND_DEGREE);
        assertThat(testCurrent.getWindDirection()).isEqualTo(DEFAULT_WIND_DIRECTION);
        assertThat(testCurrent.getPressureMb()).isEqualTo(DEFAULT_PRESSURE_MB);
        assertThat(testCurrent.getPressureIn()).isEqualTo(DEFAULT_PRESSURE_IN);
        assertThat(testCurrent.getPrecipMm()).isEqualTo(DEFAULT_PRECIP_MM);
        assertThat(testCurrent.getPrecipIn()).isEqualTo(DEFAULT_PRECIP_IN);
        assertThat(testCurrent.getHumidity()).isEqualTo(DEFAULT_HUMIDITY);
        assertThat(testCurrent.getCloud()).isEqualTo(DEFAULT_CLOUD);
        assertThat(testCurrent.getFeelslikeC()).isEqualTo(DEFAULT_FEELSLIKE_C);
        assertThat(testCurrent.getFeelslikeF()).isEqualTo(DEFAULT_FEELSLIKE_F);
        assertThat(testCurrent.getUv()).isEqualTo(DEFAULT_UV);
        assertThat(testCurrent.getGustMph()).isEqualTo(DEFAULT_GUST_MPH);
        assertThat(testCurrent.getGustKph()).isEqualTo(DEFAULT_GUST_KPH);
    }

    @Test
    @Transactional
    void createCurrentWithExistingId() throws Exception {
        // Create the Current with an existing ID
        current.setId(1L);
        CurrentDTO currentDTO = currentMapper.toDto(current);

        int databaseSizeBeforeCreate = currentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCurrents() throws Exception {
        // Initialize the database
        currentRepository.saveAndFlush(current);

        // Get all the currentList
        restCurrentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(current.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastUpdateLong").value(hasItem(DEFAULT_LAST_UPDATE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].lastUpdateTime").value(hasItem(DEFAULT_LAST_UPDATE_TIME)))
            .andExpect(jsonPath("$.[*].tempC").value(hasItem(DEFAULT_TEMP_C.doubleValue())))
            .andExpect(jsonPath("$.[*].tempF").value(hasItem(DEFAULT_TEMP_F.doubleValue())))
            .andExpect(jsonPath("$.[*].isDay").value(hasItem(DEFAULT_IS_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].windMph").value(hasItem(DEFAULT_WIND_MPH.doubleValue())))
            .andExpect(jsonPath("$.[*].windKph").value(hasItem(DEFAULT_WIND_KPH.doubleValue())))
            .andExpect(jsonPath("$.[*].windDegree").value(hasItem(DEFAULT_WIND_DEGREE)))
            .andExpect(jsonPath("$.[*].windDirection").value(hasItem(DEFAULT_WIND_DIRECTION)))
            .andExpect(jsonPath("$.[*].pressureMb").value(hasItem(DEFAULT_PRESSURE_MB)))
            .andExpect(jsonPath("$.[*].pressureIn").value(hasItem(DEFAULT_PRESSURE_IN)))
            .andExpect(jsonPath("$.[*].precipMm").value(hasItem(DEFAULT_PRECIP_MM.doubleValue())))
            .andExpect(jsonPath("$.[*].precipIn").value(hasItem(DEFAULT_PRECIP_IN.doubleValue())))
            .andExpect(jsonPath("$.[*].humidity").value(hasItem(DEFAULT_HUMIDITY.doubleValue())))
            .andExpect(jsonPath("$.[*].cloud").value(hasItem(DEFAULT_CLOUD)))
            .andExpect(jsonPath("$.[*].feelslikeC").value(hasItem(DEFAULT_FEELSLIKE_C.doubleValue())))
            .andExpect(jsonPath("$.[*].feelslikeF").value(hasItem(DEFAULT_FEELSLIKE_F.doubleValue())))
            .andExpect(jsonPath("$.[*].uv").value(hasItem(DEFAULT_UV)))
            .andExpect(jsonPath("$.[*].gustMph").value(hasItem(DEFAULT_GUST_MPH.doubleValue())))
            .andExpect(jsonPath("$.[*].gustKph").value(hasItem(DEFAULT_GUST_KPH.doubleValue())));
    }

    @Test
    @Transactional
    void getCurrent() throws Exception {
        // Initialize the database
        currentRepository.saveAndFlush(current);

        // Get the current
        restCurrentMockMvc
            .perform(get(ENTITY_API_URL_ID, current.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(current.getId().intValue()))
            .andExpect(jsonPath("$.lastUpdateLong").value(DEFAULT_LAST_UPDATE_LONG.intValue()))
            .andExpect(jsonPath("$.lastUpdateTime").value(DEFAULT_LAST_UPDATE_TIME))
            .andExpect(jsonPath("$.tempC").value(DEFAULT_TEMP_C.doubleValue()))
            .andExpect(jsonPath("$.tempF").value(DEFAULT_TEMP_F.doubleValue()))
            .andExpect(jsonPath("$.isDay").value(DEFAULT_IS_DAY.booleanValue()))
            .andExpect(jsonPath("$.windMph").value(DEFAULT_WIND_MPH.doubleValue()))
            .andExpect(jsonPath("$.windKph").value(DEFAULT_WIND_KPH.doubleValue()))
            .andExpect(jsonPath("$.windDegree").value(DEFAULT_WIND_DEGREE))
            .andExpect(jsonPath("$.windDirection").value(DEFAULT_WIND_DIRECTION))
            .andExpect(jsonPath("$.pressureMb").value(DEFAULT_PRESSURE_MB))
            .andExpect(jsonPath("$.pressureIn").value(DEFAULT_PRESSURE_IN))
            .andExpect(jsonPath("$.precipMm").value(DEFAULT_PRECIP_MM.doubleValue()))
            .andExpect(jsonPath("$.precipIn").value(DEFAULT_PRECIP_IN.doubleValue()))
            .andExpect(jsonPath("$.humidity").value(DEFAULT_HUMIDITY.doubleValue()))
            .andExpect(jsonPath("$.cloud").value(DEFAULT_CLOUD))
            .andExpect(jsonPath("$.feelslikeC").value(DEFAULT_FEELSLIKE_C.doubleValue()))
            .andExpect(jsonPath("$.feelslikeF").value(DEFAULT_FEELSLIKE_F.doubleValue()))
            .andExpect(jsonPath("$.uv").value(DEFAULT_UV))
            .andExpect(jsonPath("$.gustMph").value(DEFAULT_GUST_MPH.doubleValue()))
            .andExpect(jsonPath("$.gustKph").value(DEFAULT_GUST_KPH.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingCurrent() throws Exception {
        // Get the current
        restCurrentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCurrent() throws Exception {
        // Initialize the database
        currentRepository.saveAndFlush(current);

        int databaseSizeBeforeUpdate = currentRepository.findAll().size();

        // Update the current
        Current updatedCurrent = currentRepository.findById(current.getId()).get();
        // Disconnect from session so that the updates on updatedCurrent are not directly saved in db
        em.detach(updatedCurrent);
        updatedCurrent
            .lastUpdateLong(UPDATED_LAST_UPDATE_LONG)
            .lastUpdateTime(UPDATED_LAST_UPDATE_TIME)
            .tempC(UPDATED_TEMP_C)
            .tempF(UPDATED_TEMP_F)
            .isDay(UPDATED_IS_DAY)
            .windMph(UPDATED_WIND_MPH)
            .windKph(UPDATED_WIND_KPH)
            .windDegree(UPDATED_WIND_DEGREE)
            .windDirection(UPDATED_WIND_DIRECTION)
            .pressureMb(UPDATED_PRESSURE_MB)
            .pressureIn(UPDATED_PRESSURE_IN)
            .precipMm(UPDATED_PRECIP_MM)
            .precipIn(UPDATED_PRECIP_IN)
            .humidity(UPDATED_HUMIDITY)
            .cloud(UPDATED_CLOUD)
            .feelslikeC(UPDATED_FEELSLIKE_C)
            .feelslikeF(UPDATED_FEELSLIKE_F)
            .uv(UPDATED_UV)
            .gustMph(UPDATED_GUST_MPH)
            .gustKph(UPDATED_GUST_KPH);
        CurrentDTO currentDTO = currentMapper.toDto(updatedCurrent);

        restCurrentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeUpdate);
        Current testCurrent = currentList.get(currentList.size() - 1);
        assertThat(testCurrent.getLastUpdateLong()).isEqualTo(UPDATED_LAST_UPDATE_LONG);
        assertThat(testCurrent.getLastUpdateTime()).isEqualTo(UPDATED_LAST_UPDATE_TIME);
        assertThat(testCurrent.getTempC()).isEqualTo(UPDATED_TEMP_C);
        assertThat(testCurrent.getTempF()).isEqualTo(UPDATED_TEMP_F);
        assertThat(testCurrent.getIsDay()).isEqualTo(UPDATED_IS_DAY);
        assertThat(testCurrent.getWindMph()).isEqualTo(UPDATED_WIND_MPH);
        assertThat(testCurrent.getWindKph()).isEqualTo(UPDATED_WIND_KPH);
        assertThat(testCurrent.getWindDegree()).isEqualTo(UPDATED_WIND_DEGREE);
        assertThat(testCurrent.getWindDirection()).isEqualTo(UPDATED_WIND_DIRECTION);
        assertThat(testCurrent.getPressureMb()).isEqualTo(UPDATED_PRESSURE_MB);
        assertThat(testCurrent.getPressureIn()).isEqualTo(UPDATED_PRESSURE_IN);
        assertThat(testCurrent.getPrecipMm()).isEqualTo(UPDATED_PRECIP_MM);
        assertThat(testCurrent.getPrecipIn()).isEqualTo(UPDATED_PRECIP_IN);
        assertThat(testCurrent.getHumidity()).isEqualTo(UPDATED_HUMIDITY);
        assertThat(testCurrent.getCloud()).isEqualTo(UPDATED_CLOUD);
        assertThat(testCurrent.getFeelslikeC()).isEqualTo(UPDATED_FEELSLIKE_C);
        assertThat(testCurrent.getFeelslikeF()).isEqualTo(UPDATED_FEELSLIKE_F);
        assertThat(testCurrent.getUv()).isEqualTo(UPDATED_UV);
        assertThat(testCurrent.getGustMph()).isEqualTo(UPDATED_GUST_MPH);
        assertThat(testCurrent.getGustKph()).isEqualTo(UPDATED_GUST_KPH);
    }

    @Test
    @Transactional
    void putNonExistingCurrent() throws Exception {
        int databaseSizeBeforeUpdate = currentRepository.findAll().size();
        current.setId(count.incrementAndGet());

        // Create the Current
        CurrentDTO currentDTO = currentMapper.toDto(current);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCurrent() throws Exception {
        int databaseSizeBeforeUpdate = currentRepository.findAll().size();
        current.setId(count.incrementAndGet());

        // Create the Current
        CurrentDTO currentDTO = currentMapper.toDto(current);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCurrent() throws Exception {
        int databaseSizeBeforeUpdate = currentRepository.findAll().size();
        current.setId(count.incrementAndGet());

        // Create the Current
        CurrentDTO currentDTO = currentMapper.toDto(current);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCurrentWithPatch() throws Exception {
        // Initialize the database
        currentRepository.saveAndFlush(current);

        int databaseSizeBeforeUpdate = currentRepository.findAll().size();

        // Update the current using partial update
        Current partialUpdatedCurrent = new Current();
        partialUpdatedCurrent.setId(current.getId());

        partialUpdatedCurrent
            .lastUpdateTime(UPDATED_LAST_UPDATE_TIME)
            .windMph(UPDATED_WIND_MPH)
            .precipMm(UPDATED_PRECIP_MM)
            .precipIn(UPDATED_PRECIP_IN)
            .cloud(UPDATED_CLOUD)
            .feelslikeC(UPDATED_FEELSLIKE_C)
            .gustMph(UPDATED_GUST_MPH);

        restCurrentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrent))
            )
            .andExpect(status().isOk());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeUpdate);
        Current testCurrent = currentList.get(currentList.size() - 1);
        assertThat(testCurrent.getLastUpdateLong()).isEqualTo(DEFAULT_LAST_UPDATE_LONG);
        assertThat(testCurrent.getLastUpdateTime()).isEqualTo(UPDATED_LAST_UPDATE_TIME);
        assertThat(testCurrent.getTempC()).isEqualTo(DEFAULT_TEMP_C);
        assertThat(testCurrent.getTempF()).isEqualTo(DEFAULT_TEMP_F);
        assertThat(testCurrent.getIsDay()).isEqualTo(DEFAULT_IS_DAY);
        assertThat(testCurrent.getWindMph()).isEqualTo(UPDATED_WIND_MPH);
        assertThat(testCurrent.getWindKph()).isEqualTo(DEFAULT_WIND_KPH);
        assertThat(testCurrent.getWindDegree()).isEqualTo(DEFAULT_WIND_DEGREE);
        assertThat(testCurrent.getWindDirection()).isEqualTo(DEFAULT_WIND_DIRECTION);
        assertThat(testCurrent.getPressureMb()).isEqualTo(DEFAULT_PRESSURE_MB);
        assertThat(testCurrent.getPressureIn()).isEqualTo(DEFAULT_PRESSURE_IN);
        assertThat(testCurrent.getPrecipMm()).isEqualTo(UPDATED_PRECIP_MM);
        assertThat(testCurrent.getPrecipIn()).isEqualTo(UPDATED_PRECIP_IN);
        assertThat(testCurrent.getHumidity()).isEqualTo(DEFAULT_HUMIDITY);
        assertThat(testCurrent.getCloud()).isEqualTo(UPDATED_CLOUD);
        assertThat(testCurrent.getFeelslikeC()).isEqualTo(UPDATED_FEELSLIKE_C);
        assertThat(testCurrent.getFeelslikeF()).isEqualTo(DEFAULT_FEELSLIKE_F);
        assertThat(testCurrent.getUv()).isEqualTo(DEFAULT_UV);
        assertThat(testCurrent.getGustMph()).isEqualTo(UPDATED_GUST_MPH);
        assertThat(testCurrent.getGustKph()).isEqualTo(DEFAULT_GUST_KPH);
    }

    @Test
    @Transactional
    void fullUpdateCurrentWithPatch() throws Exception {
        // Initialize the database
        currentRepository.saveAndFlush(current);

        int databaseSizeBeforeUpdate = currentRepository.findAll().size();

        // Update the current using partial update
        Current partialUpdatedCurrent = new Current();
        partialUpdatedCurrent.setId(current.getId());

        partialUpdatedCurrent
            .lastUpdateLong(UPDATED_LAST_UPDATE_LONG)
            .lastUpdateTime(UPDATED_LAST_UPDATE_TIME)
            .tempC(UPDATED_TEMP_C)
            .tempF(UPDATED_TEMP_F)
            .isDay(UPDATED_IS_DAY)
            .windMph(UPDATED_WIND_MPH)
            .windKph(UPDATED_WIND_KPH)
            .windDegree(UPDATED_WIND_DEGREE)
            .windDirection(UPDATED_WIND_DIRECTION)
            .pressureMb(UPDATED_PRESSURE_MB)
            .pressureIn(UPDATED_PRESSURE_IN)
            .precipMm(UPDATED_PRECIP_MM)
            .precipIn(UPDATED_PRECIP_IN)
            .humidity(UPDATED_HUMIDITY)
            .cloud(UPDATED_CLOUD)
            .feelslikeC(UPDATED_FEELSLIKE_C)
            .feelslikeF(UPDATED_FEELSLIKE_F)
            .uv(UPDATED_UV)
            .gustMph(UPDATED_GUST_MPH)
            .gustKph(UPDATED_GUST_KPH);

        restCurrentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrent))
            )
            .andExpect(status().isOk());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeUpdate);
        Current testCurrent = currentList.get(currentList.size() - 1);
        assertThat(testCurrent.getLastUpdateLong()).isEqualTo(UPDATED_LAST_UPDATE_LONG);
        assertThat(testCurrent.getLastUpdateTime()).isEqualTo(UPDATED_LAST_UPDATE_TIME);
        assertThat(testCurrent.getTempC()).isEqualTo(UPDATED_TEMP_C);
        assertThat(testCurrent.getTempF()).isEqualTo(UPDATED_TEMP_F);
        assertThat(testCurrent.getIsDay()).isEqualTo(UPDATED_IS_DAY);
        assertThat(testCurrent.getWindMph()).isEqualTo(UPDATED_WIND_MPH);
        assertThat(testCurrent.getWindKph()).isEqualTo(UPDATED_WIND_KPH);
        assertThat(testCurrent.getWindDegree()).isEqualTo(UPDATED_WIND_DEGREE);
        assertThat(testCurrent.getWindDirection()).isEqualTo(UPDATED_WIND_DIRECTION);
        assertThat(testCurrent.getPressureMb()).isEqualTo(UPDATED_PRESSURE_MB);
        assertThat(testCurrent.getPressureIn()).isEqualTo(UPDATED_PRESSURE_IN);
        assertThat(testCurrent.getPrecipMm()).isEqualTo(UPDATED_PRECIP_MM);
        assertThat(testCurrent.getPrecipIn()).isEqualTo(UPDATED_PRECIP_IN);
        assertThat(testCurrent.getHumidity()).isEqualTo(UPDATED_HUMIDITY);
        assertThat(testCurrent.getCloud()).isEqualTo(UPDATED_CLOUD);
        assertThat(testCurrent.getFeelslikeC()).isEqualTo(UPDATED_FEELSLIKE_C);
        assertThat(testCurrent.getFeelslikeF()).isEqualTo(UPDATED_FEELSLIKE_F);
        assertThat(testCurrent.getUv()).isEqualTo(UPDATED_UV);
        assertThat(testCurrent.getGustMph()).isEqualTo(UPDATED_GUST_MPH);
        assertThat(testCurrent.getGustKph()).isEqualTo(UPDATED_GUST_KPH);
    }

    @Test
    @Transactional
    void patchNonExistingCurrent() throws Exception {
        int databaseSizeBeforeUpdate = currentRepository.findAll().size();
        current.setId(count.incrementAndGet());

        // Create the Current
        CurrentDTO currentDTO = currentMapper.toDto(current);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, currentDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCurrent() throws Exception {
        int databaseSizeBeforeUpdate = currentRepository.findAll().size();
        current.setId(count.incrementAndGet());

        // Create the Current
        CurrentDTO currentDTO = currentMapper.toDto(current);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCurrent() throws Exception {
        int databaseSizeBeforeUpdate = currentRepository.findAll().size();
        current.setId(count.incrementAndGet());

        // Create the Current
        CurrentDTO currentDTO = currentMapper.toDto(current);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Current in the database
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCurrent() throws Exception {
        // Initialize the database
        currentRepository.saveAndFlush(current);

        int databaseSizeBeforeDelete = currentRepository.findAll().size();

        // Delete the current
        restCurrentMockMvc
            .perform(delete(ENTITY_API_URL_ID, current.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Current> currentList = currentRepository.findAll();
        assertThat(currentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
