package com.example.leroymerlin.models;

import java.util.ArrayList;
import java.util.List;

public class Ad {
    private int id;
    private String title;
    private String description;
    private List<String> tasks;

    public Ad(int id){
        this.id = id;
        this.tasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTasks() {
        return this.tasks;
    }

    public void addTask(String task) {
        this.tasks.add(task);
    }
}
