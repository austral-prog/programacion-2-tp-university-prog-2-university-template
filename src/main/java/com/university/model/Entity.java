package com.university.model;

public abstract class Entity implements com.university.Entity {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract String classString();
}
