package com.main.model;

public class service {
    private int id;
    private String name;
    private String description;
    private int estimatedDuration;
    private double cost;

    // No-argument constructor
    public service() {
    }

    // Parameterized constructor
    public service(String name, String description, int estimatedDuration, double cost) {
        this.name = name;
        this.description = description;
        this.estimatedDuration = estimatedDuration;
        this.cost = cost;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    @Override
    public String toString() {
        return name;
    }
}
