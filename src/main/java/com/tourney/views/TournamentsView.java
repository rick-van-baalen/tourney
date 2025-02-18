package com.tourney.views;

import java.util.List;

import com.tourney.components.NewTournament;
import com.tourney.models.Tournament;
import com.tourney.services.TournamentService;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Img;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.text.Label;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.annotation.RouteAlias;
import com.webforj.router.history.ParametersBag;

@Route(value = "/tournaments", outlet = MainLayout.class)
@RouteAlias(value = "/tournaments")
@FrameTitle("My tournaments")
public class TournamentsView extends Composite<FlexLayout> {

    private FlexLayout self = getBoundComponent();
    private NoTournamentsFound noTournamentsFound;
    private Div tournamentsContainer;
    private NewTournament newTournament;
    private TournamentService tournamentService = new TournamentService();

    public TournamentsView() {
        self.setHeight("100%");
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        noTournamentsFound = new NoTournamentsFound();
        tournamentsContainer = new Div().addClassName("tournaments-container");
        self.add(noTournamentsFound, tournamentsContainer);

        newTournament = new NewTournament();
        newTournament.onAddTournament(this::onAddTournament);
        newTournament.close();
        self.add(newTournament);

        List<Tournament> tournaments = tournamentService.getTournaments();
        noTournamentsFound.setVisible(tournaments.size() == 0);
        tournamentsContainer.setVisible(tournaments.size() > 0);

        for (Tournament tournament : tournaments) {
            renderTournament(tournament);
        }

        Button newButton = new Button(TablerIcon.create("plus")).addClassName("new-tournament-button");
        newButton.setExpanse(Expanse.XLARGE);
        newButton.onClick(e -> {
            newTournament.open();
        });
        self.add(newButton);
    }

    private void renderTournament(Tournament tournament) {
        Div tournamentContainer = new Div().addClassName("tournament-container");
        tournamentsContainer.add(tournamentContainer);

        Div left = new Div().addClassName("tournament-container-left");
        Div right = new Div().addClassName("tournament-container-right");
        tournamentContainer.add(left, right);

        H3 tournamentTitle = new H3(tournament.getName()).addClassName("tournament-heading");

        String labelText = tournament.getParticipants().size() + " participants, " + tournament.getGroups().size() + " groups";
        Label participants = new Label(labelText);
        left.add(tournamentTitle, participants);

        Button deleteButton = new Button(TablerIcon.create("trash"))
            .setExpanse(Expanse.LARGE)
            .addClassName("tournament-button");
        deleteButton.onClick(e -> {
            
        });

        Button openButton = new Button(TablerIcon.create("arrow-right"))
            .setExpanse(Expanse.LARGE)
            .addClassName("tournament-button");
        openButton.onClick(e -> {
            Router.getCurrent().navigate(TournamentView.class, ParametersBag.of("id=" + tournament.getId()));
        });
        
        right.add(deleteButton, openButton);
    }

    private void onAddTournament() {
        noTournamentsFound.setVisible(false);
        tournamentsContainer.setVisible(true);

        Tournament tournament = newTournament.getTournament();
        renderTournament(tournament);
    }

    class NoTournamentsFound extends Composite<FlexLayout> {
        private FlexLayout self = getBoundComponent();

        public NoTournamentsFound() {
            self.addClassName("no-tournaments-found");
            self.setJustifyContent(FlexJustifyContent.CENTER);
            self.setDirection(FlexDirection.COLUMN);
            self.setAlignment(FlexAlignment.CENTER);
            self.setMaxWidth(600);
            self.setSpacing("20px");

            Img img = new Img("ws://no-tournaments-found.svg", "No tournaments found.").addClassName("no-tournaments-found-image");
            H3 heading = new H3("You don't have any tournaments.").addClassName("no-tournaments-found-heading");

            self.add(img, heading);
        }

        public void setVisible(Boolean visible) {
            self.setVisible(visible);
        }
    }

}
