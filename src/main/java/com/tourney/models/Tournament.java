package com.tourney.models;

import java.util.ArrayList;
import java.util.List;

public class Tournament {

    private Integer id;
    private String name;
    private List<Participant> participants = new ArrayList<Participant>();
    private List<Group> groups = new ArrayList<Group>();
    private List<Match> matches = new ArrayList<Match>();

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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}
