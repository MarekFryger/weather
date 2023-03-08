package com.weather.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Condition.
 */
@Entity
@Table(name = "condition")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Condition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "icon_link")
    private String iconLink;

    @Column(name = "code")
    private Integer code;

    @JsonIgnoreProperties(value = { "location" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Current current;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Condition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Condition description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconLink() {
        return this.iconLink;
    }

    public Condition iconLink(String iconLink) {
        this.setIconLink(iconLink);
        return this;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public Integer getCode() {
        return this.code;
    }

    public Condition code(Integer code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Current getCurrent() {
        return this.current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Condition current(Current current) {
        this.setCurrent(current);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Condition)) {
            return false;
        }
        return id != null && id.equals(((Condition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Condition{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", iconLink='" + getIconLink() + "'" +
            ", code=" + getCode() +
            "}";
    }
}
