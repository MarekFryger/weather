package com.weather.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.weather.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CurrentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrentDTO.class);
        CurrentDTO currentDTO1 = new CurrentDTO();
        currentDTO1.setId(1L);
        CurrentDTO currentDTO2 = new CurrentDTO();
        assertThat(currentDTO1).isNotEqualTo(currentDTO2);
        currentDTO2.setId(currentDTO1.getId());
        assertThat(currentDTO1).isEqualTo(currentDTO2);
        currentDTO2.setId(2L);
        assertThat(currentDTO1).isNotEqualTo(currentDTO2);
        currentDTO1.setId(null);
        assertThat(currentDTO1).isNotEqualTo(currentDTO2);
    }
}
