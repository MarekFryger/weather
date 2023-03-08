package com.weather.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.weather.domain.Location} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String country;

    @NotNull
    private Float lat;

    @NotNull
    private Float lon;

    private String timeZone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationDTO)) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", country='" + getCountry() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", timeZone='" + getTimeZone() + "'" +
            "}";
    }
}
