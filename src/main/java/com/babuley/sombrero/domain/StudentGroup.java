package com.babuley.sombrero.domain;

/**
 * A problem fact
 */
public class StudentGroup {

    private Long id;
    private String name;
    private int capacity;

    private StudentGroup(){}

    public StudentGroup(Long id, String name) {
        this.id = id;
        this.name = name;
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
