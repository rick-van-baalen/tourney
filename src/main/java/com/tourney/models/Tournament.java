package com.tourney.models;

import java.util.UUID;

public class Tournament {

    private String uuid;
    private String name;
    private int numberOfParticipants;

    public Tournament(String name, int numberOfParticipants) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
    }

    public String getId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

}
