package com.weather.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.weather.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConditionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConditionDTO.class);
        ConditionDTO conditionDTO1 = new ConditionDTO();
        conditionDTO1.setId(1L);
        ConditionDTO conditionDTO2 = new ConditionDTO();
        assertThat(conditionDTO1).isNotEqualTo(conditionDTO2);
        conditionDTO2.setId(conditionDTO1.getId());
        assertThat(conditionDTO1).isEqualTo(conditionDTO2);
        conditionDTO2.setId(2L);
        assertThat(conditionDTO1).isNotEqualTo(conditionDTO2);
        conditionDTO1.setId(null);
        assertThat(conditionDTO1).isNotEqualTo(conditionDTO2);
    }
}
