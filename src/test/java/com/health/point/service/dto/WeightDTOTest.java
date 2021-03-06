package com.health.point.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.health.point.web.rest.TestUtil;

public class WeightDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeightDTO.class);
        WeightDTO weightDTO1 = new WeightDTO();
        weightDTO1.setId(1L);
        WeightDTO weightDTO2 = new WeightDTO();
        assertThat(weightDTO1).isNotEqualTo(weightDTO2);
        weightDTO2.setId(weightDTO1.getId());
        assertThat(weightDTO1).isEqualTo(weightDTO2);
        weightDTO2.setId(2L);
        assertThat(weightDTO1).isNotEqualTo(weightDTO2);
        weightDTO1.setId(null);
        assertThat(weightDTO1).isNotEqualTo(weightDTO2);
    }
}
