package com.weather.service.mapper;

import com.weather.domain.Current;
import com.weather.domain.Location;
import com.weather.service.dto.CurrentDTO;
import com.weather.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Current} and its DTO {@link CurrentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CurrentMapper extends EntityMapper<CurrentDTO, Current> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    CurrentDTO toDto(Current s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
