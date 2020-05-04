package com.health.point.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.health.point.domain.Bloodpressure} entity.
 */
public class BloodpressureDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ZonedDateTime timestamp;

    @NotNull
    private Integer systolic;

    @NotNull
    private Integer diastolic;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BloodpressureDTO bloodpressureDTO = (BloodpressureDTO) o;
        if (bloodpressureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bloodpressureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BloodpressureDTO{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", systolic=" + getSystolic() +
            ", diastolic=" + getDiastolic() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
