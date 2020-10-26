package com.babuley.sombrero.domain.rooms;

import java.util.Random;

/**
 * A problem fact
 */
public class Room {

    private String name;

    private int capacity = 10;

    private RoomType roomType = RoomType.room;

    private Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public Room(String name, int capacity, RoomType roomType) {
        this.name = name;
        this.capacity = capacity;
        this.roomType = roomType;
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