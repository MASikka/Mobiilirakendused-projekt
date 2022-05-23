package com.example.androidreviewapp.model;

public class Game {

    private final String id;
    private final String name;

    public Game(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }
}
