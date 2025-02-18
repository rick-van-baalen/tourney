package com.tourney.models;

import java.util.List;
import java.util.stream.Collectors;

public class Group {

    private Integer id;
    private String name;
    private List<Participant> participants;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public String getParticipantsAsString() {
        String result = participants.stream()
            .map(Participant::getName)
            .collect(Collectors.joining(", "));
        return result;
    }
    
}