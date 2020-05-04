package com.health.point.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PointsMapperTest {

    private PointsMapper pointsMapper;

    @BeforeEach
    public void setUp() {
        pointsMapper = new PointsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pointsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pointsMapper.fromId(null)).isNull();
    }
}
