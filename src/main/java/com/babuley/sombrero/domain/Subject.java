package com.babuley.sombrero.domain;

/**
 * A problem fact
 */
public class Subject {

    private String name;

    private boolean needsLab;

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, boolean needsLab) {
        this.name = name;
        this.needsLab = needsLab;
    }

    public String getName() {
        return name;
    }

    public boolean needsLab() {
        return needsLab;
    }
}
