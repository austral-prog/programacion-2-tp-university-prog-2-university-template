package com.university.model;

public abstract class Entity implements com.university.Entity {
    private Integer id;

    public void setId(Integer id) {this.id = id;}

    public int getId() {return id;}

    public abstract String classString();

    public abstract String name();

    @Override
    public String toString() {
        return "name: " + name() + ", id: " + id;
    }
}
