package com.tourney.views;

import com.tourney.models.Tournament;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;

public class TournamentDetails extends Composite<Div> {

    private Tournament tournament;

    public TournamentDetails(Tournament tournament) {
        this.tournament = tournament;
    }
    
}
