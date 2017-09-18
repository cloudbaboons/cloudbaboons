package com.cloudbaboons.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Entities.
 */
@Entity
@Table(name = "entities")
public class Entities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orgname")
    private String orgname;

    @Column(name = "appname")
    private String appname;

    @Column(name = "fieldname")
    private String fieldname;

    @Column(name = "fieldtype")
    private String fieldtype;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgname() {
        return orgname;
    }

    public Entities orgname(String orgname) {
        this.orgname = orgname;
        return this;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getAppname() {
        return appname;
    }

    public Entities appname(String appname) {
        this.appname = appname;
        return this;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getFieldname() {
        return fieldname;
    }

    public Entities fieldname(String fieldname) {
        this.fieldname = fieldname;
        return this;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public Entities fieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
        return this;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entities entities = (Entities) o;
        if (entities.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entities.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entities{" +
            "id=" + getId() +
            ", orgname='" + getOrgname() + "'" +
            ", appname='" + getAppname() + "'" +
            ", fieldname='" + getFieldname() + "'" +
            ", fieldtype='" + getFieldtype() + "'" +
            "}";
    }
}
