package com.tourney.models;

import java.util.ArrayList;
import java.util.List;

public class Tournament {

    private int id;
    private String name;
    private List<Participant> participants = new ArrayList<Participant>();
    private List<Group> groups = new ArrayList<Group>();
    private List<Match> matches = new ArrayList<Match>();

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

    public List<Participant> getParticipants() {
        return participants;
    }

}
