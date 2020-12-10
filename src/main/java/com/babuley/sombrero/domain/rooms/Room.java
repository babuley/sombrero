package com.babuley.sombrero.domain.rooms;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Random;

/**
 * A problem fact
 */
@Entity
public class Room {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int capacity = 10;

    private RoomType roomType = RoomType.room;

    private Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Room(String name, int capacity, RoomType roomType) {
        this.name = name;
        this.capacity = capacity;
        this.roomType = roomType;
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

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }


    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public RoomType getRoomType() {
        return roomType;
    }
}