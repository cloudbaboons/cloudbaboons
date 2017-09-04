package com.cloudbaboons.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Genapp.
 */
@Entity
@Table(name = "genapp")
public class Genapp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_name")
    private String application_name;

    @Column(name = "package_name")
    private String package_name;

    @Column(name = "package_folder")
    private String package_folder;

    @Column(name = "server_port")
    private String server_port;

    @Column(name = "database_type")
    private String database_type;

    @Column(name = "application_prefix")
    private String application_prefix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplication_name() {
        return application_name;
    }

    public Genapp application_name(String application_name) {
        this.application_name = application_name;
        return this;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public Genapp package_name(String package_name) {
        this.package_name = package_name;
        return this;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_folder() {
        return package_folder;
    }

    public Genapp package_folder(String package_folder) {
        this.package_folder = package_folder;
        return this;
    }

    public void setPackage_folder(String package_folder) {
        this.package_folder = package_folder;
    }

    public String getServer_port() {
        return server_port;
    }

    public Genapp server_port(String server_port) {
        this.server_port = server_port;
        return this;
    }

    public void setServer_port(String server_port) {
        this.server_port = server_port;
    }

    public String getDatabase_type() {
        return database_type;
    }

    public Genapp database_type(String database_type) {
        this.database_type = database_type;
        return this;
    }

    public void setDatabase_type(String database_type) {
        this.database_type = database_type;
    }

    public String getApplication_prefix() {
        return application_prefix;
    }

    public Genapp application_prefix(String application_prefix) {
        this.application_prefix = application_prefix;
        return this;
    }

    public void setApplication_prefix(String application_prefix) {
        this.application_prefix = application_prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genapp genapp = (Genapp) o;
        if (genapp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), genapp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Genapp{" +
            "id=" + getId() +
            ", application_name='" + getApplication_name() + "'" +
            ", package_name='" + getPackage_name() + "'" +
            ", package_folder='" + getPackage_folder() + "'" +
            ", server_port='" + getServer_port() + "'" +
            ", database_type='" + getDatabase_type() + "'" +
            ", application_prefix='" + getApplication_prefix() + "'" +
            "}";
    }
}
