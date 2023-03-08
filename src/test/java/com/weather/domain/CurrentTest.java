package com.weather.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.weather.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CurrentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Current.class);
        Current current1 = new Current();
        current1.setId(1L);
        Current current2 = new Current();
        current2.setId(current1.getId());
        assertThat(current1).isEqualTo(current2);
        current2.setId(2L);
        assertThat(current1).isNotEqualTo(current2);
        current1.setId(null);
        assertThat(current1).isNotEqualTo(current2);
    }
}
