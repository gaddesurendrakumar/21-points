package com.health.point.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.health.point.web.rest.TestUtil;

public class PointsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Points.class);
        Points points1 = new Points();
        points1.setId(1L);
        Points points2 = new Points();
        points2.setId(points1.getId());
        assertThat(points1).isEqualTo(points2);
        points2.setId(2L);
        assertThat(points1).isNotEqualTo(points2);
        points1.setId(null);
        assertThat(points1).isNotEqualTo(points2);
    }
}
