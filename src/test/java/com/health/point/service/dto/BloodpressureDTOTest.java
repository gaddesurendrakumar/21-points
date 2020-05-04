package com.health.point.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.health.point.web.rest.TestUtil;

public class BloodpressureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloodpressureDTO.class);
        BloodpressureDTO bloodpressureDTO1 = new BloodpressureDTO();
        bloodpressureDTO1.setId(1L);
        BloodpressureDTO bloodpressureDTO2 = new BloodpressureDTO();
        assertThat(bloodpressureDTO1).isNotEqualTo(bloodpressureDTO2);
        bloodpressureDTO2.setId(bloodpressureDTO1.getId());
        assertThat(bloodpressureDTO1).isEqualTo(bloodpressureDTO2);
        bloodpressureDTO2.setId(2L);
        assertThat(bloodpressureDTO1).isNotEqualTo(bloodpressureDTO2);
        bloodpressureDTO1.setId(null);
        assertThat(bloodpressureDTO1).isNotEqualTo(bloodpressureDTO2);
    }
}
