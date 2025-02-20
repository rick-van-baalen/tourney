package com.tourney.views;

import com.tourney.models.Tournament;
import com.tourney.services.TournamentService;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.event.ButtonClickEvent;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.loading.Loading;

public class TournamentMatches extends Composite<Div> {

    private Tournament tournament;
    private TournamentService tournamentService = new TournamentService();
    private Loading loading;

    public TournamentMatches(Tournament tournament) {
        this.tournament = tournament;

        FlexLayout layout = new FlexLayout();
        layout.addClassName("no-matches-found");
        layout.setJustifyContent(FlexJustifyContent.CENTER);
        layout.setDirection(FlexDirection.COLUMN);
        layout.setAlignment(FlexAlignment.CENTER);
        layout.setMaxWidth(600);
        layout.setSpacing("20px");
        layout.setHeight("100%");
        getBoundComponent().add(layout);

        H3 heading = new H3("The schedule has not yet been generated.").addClassName("no-matches-found-heading");
        Button button = new Button("Generate schedule").setExpanse(Expanse.LARGE);
        button.onClick(this::onGenerateSchedule);
        layout.add(heading, button);

        loading = new Loading();
        getBoundComponent().add(loading);
    }

    private void onGenerateSchedule(ButtonClickEvent event) {
        loading.setText("Generating schedule...");
        loading.open();

        tournamentService.generateSchedule(tournament.getId());
        
        loading.close();
    }
    
}
