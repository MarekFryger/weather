package com.weather.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrentMapperTest {

    private CurrentMapper currentMapper;

    @BeforeEach
    public void setUp() {
        currentMapper = new CurrentMapperImpl();
    }
}
