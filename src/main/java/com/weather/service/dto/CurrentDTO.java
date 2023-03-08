package com.weather.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.weather.domain.Current} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CurrentDTO implements Serializable {

    private Long id;

    private Long lastUpdateLong;

    private String lastUpdateTime;

    private Double tempC;

    private Double tempF;

    private Boolean isDay;

    private Double windMph;

    private Double windKph;

    private Integer windDegree;

    private String windDirection;

    private Integer pressureMb;

    private Integer pressureIn;

    private Double precipMm;

    private Double precipIn;

    private Double humidity;

    private Integer cloud;

    private Double feelslikeC;

    private Double feelslikeF;

    private Integer uv;

    private Double gustMph;

    private Double gustKph;

    private LocationDTO location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastUpdateLong() {
        return lastUpdateLong;
    }

    public void setLastUpdateLong(Long lastUpdateLong) {
        this.lastUpdateLong = lastUpdateLong;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Double getTempC() {
        return tempC;
    }

    public void setTempC(Double tempC) {
        this.tempC = tempC;
    }

    public Double getTempF() {
        return tempF;
    }

    public void setTempF(Double tempF) {
        this.tempF = tempF;
    }

    public Boolean getIsDay() {
        return isDay;
    }

    public void setIsDay(Boolean isDay) {
        this.isDay = isDay;
    }

    public Double getWindMph() {
        return windMph;
    }

    public void setWindMph(Double windMph) {
        this.windMph = windMph;
    }

    public Double getWindKph() {
        return windKph;
    }

    public void setWindKph(Double windKph) {
        this.windKph = windKph;
    }

    public Integer getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(Integer windDegree) {
        this.windDegree = windDegree;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Integer getPressureMb() {
        return pressureMb;
    }

    public void setPressureMb(Integer pressureMb) {
        this.pressureMb = pressureMb;
    }

    public Integer getPressureIn() {
        return pressureIn;
    }

    public void setPressureIn(Integer pressureIn) {
        this.pressureIn = pressureIn;
    }

    public Double getPrecipMm() {
        return precipMm;
    }

    public void setPrecipMm(Double precipMm) {
        this.precipMm = precipMm;
    }

    public Double getPrecipIn() {
        return precipIn;
    }

    public void setPrecipIn(Double precipIn) {
        this.precipIn = precipIn;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Integer getCloud() {
        return cloud;
    }

    public void setCloud(Integer cloud) {
        this.cloud = cloud;
    }

    public Double getFeelslikeC() {
        return feelslikeC;
    }

    public void setFeelslikeC(Double feelslikeC) {
        this.feelslikeC = feelslikeC;
    }

    public Double getFeelslikeF() {
        return feelslikeF;
    }

    public void setFeelslikeF(Double feelslikeF) {
        this.feelslikeF = feelslikeF;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Double getGustMph() {
        return gustMph;
    }

    public void setGustMph(Double gustMph) {
        this.gustMph = gustMph;
    }

    public Double getGustKph() {
        return gustKph;
    }

    public void setGustKph(Double gustKph) {
        this.gustKph = gustKph;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrentDTO)) {
            return false;
        }

        CurrentDTO currentDTO = (CurrentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, currentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrentDTO{" +
            "id=" + getId() +
            ", lastUpdateLong=" + getLastUpdateLong() +
            ", lastUpdateTime='" + getLastUpdateTime() + "'" +
            ", tempC=" + getTempC() +
            ", tempF=" + getTempF() +
            ", isDay='" + getIsDay() + "'" +
            ", windMph=" + getWindMph() +
            ", windKph=" + getWindKph() +
            ", windDegree=" + getWindDegree() +
            ", windDirection='" + getWindDirection() + "'" +
            ", pressureMb=" + getPressureMb() +
            ", pressureIn=" + getPressureIn() +
            ", precipMm=" + getPrecipMm() +
            ", precipIn=" + getPrecipIn() +
            ", humidity=" + getHumidity() +
            ", cloud=" + getCloud() +
            ", feelslikeC=" + getFeelslikeC() +
            ", feelslikeF=" + getFeelslikeF() +
            ", uv=" + getUv() +
            ", gustMph=" + getGustMph() +
            ", gustKph=" + getGustKph() +
            ", location=" + getLocation() +
            "}";
    }
}
