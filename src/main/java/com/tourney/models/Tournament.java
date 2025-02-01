package com.tourney.models;

public class Tournament {

    private int id;
    private String name;
    private Double numberOfParticipants;

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

    public Double getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Double numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

}
