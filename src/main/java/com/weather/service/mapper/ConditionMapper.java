package com.weather.service.mapper;

import com.weather.domain.Condition;
import com.weather.domain.Current;
import com.weather.service.dto.ConditionDTO;
import com.weather.service.dto.CurrentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Condition} and its DTO {@link ConditionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConditionMapper extends EntityMapper<ConditionDTO, Condition> {
    @Mapping(target = "current", source = "current", qualifiedByName = "currentId")
    ConditionDTO toDto(Condition s);

    @Named("currentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrentDTO toDtoCurrentId(Current current);
}
