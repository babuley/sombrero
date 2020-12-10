package com.babuley.sombrero.domain;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A problem fact
 */
@Entity
public class Subject {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private boolean needsLab;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }


    public Subject(String name, boolean needsLab) {
        this.name = name;
        this.needsLab = needsLab;
    }

    public Subject(Long id, String name, boolean needsLab) {
        this.id = id;
        this.name = name;
        this.needsLab = needsLab;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeedsLab() {
        return needsLab;
    }

    public void setNeedsLab(boolean needsLab) {
        this.needsLab = needsLab;
    }

    public String getName() {
        return name;
    }

    public boolean needsLab() {
        return needsLab;
    }
}
