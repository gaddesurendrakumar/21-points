package com.health.point.service.mapper;


import com.health.point.domain.*;
import com.health.point.service.dto.BloodpressureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bloodpressure} and its DTO {@link BloodpressureDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BloodpressureMapper extends EntityMapper<BloodpressureDTO, Bloodpressure> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    BloodpressureDTO toDto(Bloodpressure bloodpressure);

    @Mapping(source = "userId", target = "user")
    Bloodpressure toEntity(BloodpressureDTO bloodpressureDTO);

    default Bloodpressure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bloodpressure bloodpressure = new Bloodpressure();
        bloodpressure.setId(id);
        return bloodpressure;
    }
}
