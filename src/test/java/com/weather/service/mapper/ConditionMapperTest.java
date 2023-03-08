package com.weather.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConditionMapperTest {

    private ConditionMapper conditionMapper;

    @BeforeEach
    public void setUp() {
        conditionMapper = new ConditionMapperImpl();
    }
}
