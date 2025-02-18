package com.tourney.views;

import com.tourney.models.Tournament;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;

public class TournamentGroups extends Composite<Div> {

    private Tournament tournament;

    public TournamentGroups(Tournament tournament) {
        this.tournament = tournament;
    }
    
}
