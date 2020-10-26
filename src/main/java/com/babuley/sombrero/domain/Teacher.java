package com.babuley.sombrero.domain;

public class Teacher {

    private Long id;
    private String name;
    private Subject subject;

    private Teacher() {
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
