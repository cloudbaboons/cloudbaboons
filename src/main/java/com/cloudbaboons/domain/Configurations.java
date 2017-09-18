package com.cloudbaboons.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Configurations.
 */
@Entity
@Table(name = "configurations")
public class Configurations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "giturl")
    private String giturl;

    @Column(name = "gitusername")
    private String gitusername;

    @Column(name = "gitpassword")
    private String gitpassword;

    @Column(name = "jenkinsurl")
    private String jenkinsurl;

    @Column(name = "jenkinsusername")
    private String jenkinsusername;

    @Column(name = "jenkinspassword")
    private String jenkinspassword;

    @Column(name = "appname")
    private String appname;

    @Column(name = "orgname")
    private String orgname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGiturl() {
        return giturl;
    }

    public Configurations giturl(String giturl) {
        this.giturl = giturl;
        return this;
    }

    public void setGiturl(String giturl) {
        this.giturl = giturl;
    }

    public String getGitusername() {
        return gitusername;
    }

    public Configurations gitusername(String gitusername) {
        this.gitusername = gitusername;
        return this;
    }

    public void setGitusername(String gitusername) {
        this.gitusername = gitusername;
    }

    public String getGitpassword() {
        return gitpassword;
    }

    public Configurations gitpassword(String gitpassword) {
        this.gitpassword = gitpassword;
        return this;
    }

    public void setGitpassword(String gitpassword) {
        this.gitpassword = gitpassword;
    }

    public String getJenkinsurl() {
        return jenkinsurl;
    }

    public Configurations jenkinsurl(String jenkinsurl) {
        this.jenkinsurl = jenkinsurl;
        return this;
    }

    public void setJenkinsurl(String jenkinsurl) {
        this.jenkinsurl = jenkinsurl;
    }

    public String getJenkinsusername() {
        return jenkinsusername;
    }

    public Configurations jenkinsusername(String jenkinsusername) {
        this.jenkinsusername = jenkinsusername;
        return this;
    }

    public void setJenkinsusername(String jenkinsusername) {
        this.jenkinsusername = jenkinsusername;
    }

    public String getJenkinspassword() {
        return jenkinspassword;
    }

    public Configurations jenkinspassword(String jenkinspassword) {
        this.jenkinspassword = jenkinspassword;
        return this;
    }

    public void setJenkinspassword(String jenkinspassword) {
        this.jenkinspassword = jenkinspassword;
    }

    public String getAppname() {
        return appname;
    }

    public Configurations appname(String appname) {
        this.appname = appname;
        return this;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getOrgname() {
        return orgname;
    }

    public Configurations orgname(String orgname) {
        this.orgname = orgname;
        return this;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Configurations configurations = (Configurations) o;
        if (configurations.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), configurations.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Configurations{" +
            "id=" + getId() +
            ", giturl='" + getGiturl() + "'" +
            ", gitusername='" + getGitusername() + "'" +
            ", gitpassword='" + getGitpassword() + "'" +
            ", jenkinsurl='" + getJenkinsurl() + "'" +
            ", jenkinsusername='" + getJenkinsusername() + "'" +
            ", jenkinspassword='" + getJenkinspassword() + "'" +
            ", appname='" + getAppname() + "'" +
            ", orgname='" + getOrgname() + "'" +
            "}";
    }
}
