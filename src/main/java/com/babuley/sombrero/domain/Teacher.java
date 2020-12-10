package com.babuley.sombrero.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Teacher {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    private Subject subject;

    private Teacher() {
    }

    public Teacher(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }


    public Teacher(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Teacher(Long id, String name, Subject subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }
}
