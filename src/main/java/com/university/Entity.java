package com.university;

public interface Entity {
    int getId();

    default void setId(int id) {
    }
}
