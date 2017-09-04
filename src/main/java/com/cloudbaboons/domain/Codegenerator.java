package com.cloudbaboons.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Codegenerator.
 */
@Entity
@Table(name = "codegenerator")
public class Codegenerator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_name")
    private String application_name;

    @Column(name = "package_name")
    private String package_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplication_name() {
        return application_name;
    }

    public Codegenerator application_name(String application_name) {
        this.application_name = application_name;
        return this;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public Codegenerator package_name(String package_name) {
        this.package_name = package_name;
        return this;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Codegenerator codegenerator = (Codegenerator) o;
        if (codegenerator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), codegenerator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Codegenerator{" +
            "id=" + getId() +
            ", application_name='" + getApplication_name() + "'" +
            ", package_name='" + getPackage_name() + "'" +
            "}";
    }
}
