package com.weather.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.weather.domain.Condition} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConditionDTO implements Serializable {

    private Long id;

    private String description;

    private String iconLink;

    private Integer code;

    private CurrentDTO current;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public CurrentDTO getCurrent() {
        return current;
    }

    public void setCurrent(CurrentDTO current) {
        this.current = current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionDTO)) {
            return false;
        }

        ConditionDTO conditionDTO = (ConditionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, conditionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", iconLink='" + getIconLink() + "'" +
            ", code=" + getCode() +
            ", current=" + getCurrent() +
            "}";
    }
}
