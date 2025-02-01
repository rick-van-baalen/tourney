package com.tourney.services;

import java.util.ArrayList;
import java.util.List;

import com.tourney.models.Tournament;
import com.webforj.environment.ObjectTable;

public class TournamentService {
    
    private int serial = 1;

    public void add(Tournament tournament) {
        tournament.setId(serial++);

        List<Tournament> tournaments = getTournaments();
        tournaments.add(tournament);
    }

    public List<Tournament> getTournaments() {
        List<Tournament> tournaments = null;
        
        if (ObjectTable.contains("TOURNAMENTS")) {
            tournaments = (List<Tournament>) ObjectTable.get("TOURNAMENTS");
        } else {
            tournaments = new ArrayList<Tournament>();
            ObjectTable.put("TOURNAMENTS", tournaments);
        }

        return tournaments;
    }

    public Tournament getTournament(int id) {
        List<Tournament> tournaments = getTournaments();

        for (Tournament tournament : tournaments) {
            if (tournament.getId() == id) return tournament;
        }

        return null;
    }

}
