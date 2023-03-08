package com.weather.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Current.
 */
@Entity
@Table(name = "current")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Current implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "last_update_long")
    private Long lastUpdateLong;

    @Column(name = "last_update_time")
    private String lastUpdateTime;

    @Column(name = "temp_c")
    private Double tempC;

    @Column(name = "temp_f")
    private Double tempF;

    @Column(name = "is_day")
    private Boolean isDay;

    @Column(name = "wind_mph")
    private Double windMph;

    @Column(name = "wind_kph")
    private Double windKph;

    @Column(name = "wind_degree")
    private Integer windDegree;

    @Column(name = "wind_direction")
    private String windDirection;

    @Column(name = "pressure_mb")
    private Integer pressureMb;

    @Column(name = "pressure_in")
    private Integer pressureIn;

    @Column(name = "precip_mm")
    private Double precipMm;

    @Column(name = "precip_in")
    private Double precipIn;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "cloud")
    private Integer cloud;

    @Column(name = "feelslike_c")
    private Double feelslikeC;

    @Column(name = "feelslike_f")
    private Double feelslikeF;

    @Column(name = "uv")
    private Integer uv;

    @Column(name = "gust_mph")
    private Double gustMph;

    @Column(name = "gust_kph")
    private Double gustKph;

    @ManyToOne
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Current id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastUpdateLong() {
        return this.lastUpdateLong;
    }

    public Current lastUpdateLong(Long lastUpdateLong) {
        this.setLastUpdateLong(lastUpdateLong);
        return this;
    }

    public void setLastUpdateLong(Long lastUpdateLong) {
        this.lastUpdateLong = lastUpdateLong;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public Current lastUpdateTime(String lastUpdateTime) {
        this.setLastUpdateTime(lastUpdateTime);
        return this;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Double getTempC() {
        return this.tempC;
    }

    public Current tempC(Double tempC) {
        this.setTempC(tempC);
        return this;
    }

    public void setTempC(Double tempC) {
        this.tempC = tempC;
    }

    public Double getTempF() {
        return this.tempF;
    }

    public Current tempF(Double tempF) {
        this.setTempF(tempF);
        return this;
    }

    public void setTempF(Double tempF) {
        this.tempF = tempF;
    }

    public Boolean getIsDay() {
        return this.isDay;
    }

    public Current isDay(Boolean isDay) {
        this.setIsDay(isDay);
        return this;
    }

    public void setIsDay(Boolean isDay) {
        this.isDay = isDay;
    }

    public Double getWindMph() {
        return this.windMph;
    }

    public Current windMph(Double windMph) {
        this.setWindMph(windMph);
        return this;
    }

    public void setWindMph(Double windMph) {
        this.windMph = windMph;
    }

    public Double getWindKph() {
        return this.windKph;
    }

    public Current windKph(Double windKph) {
        this.setWindKph(windKph);
        return this;
    }

    public void setWindKph(Double windKph) {
        this.windKph = windKph;
    }

    public Integer getWindDegree() {
        return this.windDegree;
    }

    public Current windDegree(Integer windDegree) {
        this.setWindDegree(windDegree);
        return this;
    }

    public void setWindDegree(Integer windDegree) {
        this.windDegree = windDegree;
    }

    public String getWindDirection() {
        return this.windDirection;
    }

    public Current windDirection(String windDirection) {
        this.setWindDirection(windDirection);
        return this;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Integer getPressureMb() {
        return this.pressureMb;
    }

    public Current pressureMb(Integer pressureMb) {
        this.setPressureMb(pressureMb);
        return this;
    }

    public void setPressureMb(Integer pressureMb) {
        this.pressureMb = pressureMb;
    }

    public Integer getPressureIn() {
        return this.pressureIn;
    }

    public Current pressureIn(Integer pressureIn) {
        this.setPressureIn(pressureIn);
        return this;
    }

    public void setPressureIn(Integer pressureIn) {
        this.pressureIn = pressureIn;
    }

    public Double getPrecipMm() {
        return this.precipMm;
    }

    public Current precipMm(Double precipMm) {
        this.setPrecipMm(precipMm);
        return this;
    }

    public void setPrecipMm(Double precipMm) {
        this.precipMm = precipMm;
    }

    public Double getPrecipIn() {
        return this.precipIn;
    }

    public Current precipIn(Double precipIn) {
        this.setPrecipIn(precipIn);
        return this;
    }

    public void setPrecipIn(Double precipIn) {
        this.precipIn = precipIn;
    }

    public Double getHumidity() {
        return this.humidity;
    }

    public Current humidity(Double humidity) {
        this.setHumidity(humidity);
        return this;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Integer getCloud() {
        return this.cloud;
    }

    public Current cloud(Integer cloud) {
        this.setCloud(cloud);
        return this;
    }

    public void setCloud(Integer cloud) {
        this.cloud = cloud;
    }

    public Double getFeelslikeC() {
        return this.feelslikeC;
    }

    public Current feelslikeC(Double feelslikeC) {
        this.setFeelslikeC(feelslikeC);
        return this;
    }

    public void setFeelslikeC(Double feelslikeC) {
        this.feelslikeC = feelslikeC;
    }

    public Double getFeelslikeF() {
        return this.feelslikeF;
    }

    public Current feelslikeF(Double feelslikeF) {
        this.setFeelslikeF(feelslikeF);
        return this;
    }

    public void setFeelslikeF(Double feelslikeF) {
        this.feelslikeF = feelslikeF;
    }

    public Integer getUv() {
        return this.uv;
    }

    public Current uv(Integer uv) {
        this.setUv(uv);
        return this;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Double getGustMph() {
        return this.gustMph;
    }

    public Current gustMph(Double gustMph) {
        this.setGustMph(gustMph);
        return this;
    }

    public void setGustMph(Double gustMph) {
        this.gustMph = gustMph;
    }

    public Double getGustKph() {
        return this.gustKph;
    }

    public Current gustKph(Double gustKph) {
        this.setGustKph(gustKph);
        return this;
    }

    public void setGustKph(Double gustKph) {
        this.gustKph = gustKph;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current location(Location location) {
        this.setLocation(location);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Current)) {
            return false;
        }
        return id != null && id.equals(((Current) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Current{" +
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
            "}";
    }
}
