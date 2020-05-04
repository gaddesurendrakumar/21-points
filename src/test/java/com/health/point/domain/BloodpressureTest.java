package com.health.point.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.health.point.web.rest.TestUtil;

public class BloodpressureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bloodpressure.class);
        Bloodpressure bloodpressure1 = new Bloodpressure();
        bloodpressure1.setId(1L);
        Bloodpressure bloodpressure2 = new Bloodpressure();
        bloodpressure2.setId(bloodpressure1.getId());
        assertThat(bloodpressure1).isEqualTo(bloodpressure2);
        bloodpressure2.setId(2L);
        assertThat(bloodpressure1).isNotEqualTo(bloodpressure2);
        bloodpressure1.setId(null);
        assertThat(bloodpressure1).isNotEqualTo(bloodpressure2);
    }
}
