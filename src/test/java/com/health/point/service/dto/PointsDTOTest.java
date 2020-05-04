package com.health.point.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.health.point.web.rest.TestUtil;

public class PointsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointsDTO.class);
        PointsDTO pointsDTO1 = new PointsDTO();
        pointsDTO1.setId(1L);
        PointsDTO pointsDTO2 = new PointsDTO();
        assertThat(pointsDTO1).isNotEqualTo(pointsDTO2);
        pointsDTO2.setId(pointsDTO1.getId());
        assertThat(pointsDTO1).isEqualTo(pointsDTO2);
        pointsDTO2.setId(2L);
        assertThat(pointsDTO1).isNotEqualTo(pointsDTO2);
        pointsDTO1.setId(null);
        assertThat(pointsDTO1).isNotEqualTo(pointsDTO2);
    }
}
