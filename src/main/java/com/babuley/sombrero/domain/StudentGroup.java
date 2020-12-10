package com.babuley.sombrero.domain;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A problem fact
 */
@Entity
public class StudentGroup {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int capacity;

    private StudentGroup(){}

    public StudentGroup(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public StudentGroup( String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public StudentGroup(Long id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
