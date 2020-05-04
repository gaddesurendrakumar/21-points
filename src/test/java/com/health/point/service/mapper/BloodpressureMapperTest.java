package com.health.point.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BloodpressureMapperTest {

    private BloodpressureMapper bloodpressureMapper;

    @BeforeEach
    public void setUp() {
        bloodpressureMapper = new BloodpressureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bloodpressureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bloodpressureMapper.fromId(null)).isNull();
    }
}
