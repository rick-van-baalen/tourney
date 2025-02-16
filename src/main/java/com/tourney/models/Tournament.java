package com.tourney.models;

import java.util.ArrayList;
import java.util.List;

public class Tournament {

    private String uuid = "";
    private String name;
    private List<Participant> participants = new ArrayList<Participant>();
    private List<Group> groups = new ArrayList<Group>();
    private List<Match> matches = new ArrayList<Match>();

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
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
