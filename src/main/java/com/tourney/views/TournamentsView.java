package com.tourney.views;

import com.tourney.components.NewTournament;
import com.tourney.models.Tournament;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Img;
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
@FrameTitle("Tournaments")
public class TournamentsView extends Composite<FlexLayout> {

    private FlexLayout self = getBoundComponent();
    private CreateTournament createTournament;
    private FlexLayout tournamentsContainer;
    private NewTournament newTournament;

    public TournamentsView() {
        self.setHeight("100%");

        createTournament = new CreateTournament();
        createTournament.onAddTournament(this::onAddTournament);
        tournamentsContainer = new FlexLayout().setVisible(false);
        self.add(createTournament, tournamentsContainer);
    }

    private void onAddTournament() {
        createTournament.setVisible(false);
        tournamentsContainer.setVisible(true);

        Tournament tournament = newTournament.getTournament();

        Div tournamentContainer = new Div().addClassName("tournament-container");
        tournamentsContainer.add(tournamentContainer);

        H3 tournamentTitle = new H3(tournament.getName()).addClassName("tournament-heading");
        String numberOfParticipants = tournament.getNumberOfParticipants().toString().replace(".0", "");
        Label participants = new Label("Participants: " + numberOfParticipants);
        Button tournamentButton = new Button("View")
            .setExpanse(Expanse.LARGE)
            .addClassName("tournament-button");
        
        tournamentButton.onClick(e -> {
            Router.getCurrent().navigate(TournamentView.class, ParametersBag.of("id=" + tournament.getId()));
        });
        tournamentContainer.add(tournamentTitle, participants, tournamentButton);
    }

    class CreateTournament extends Composite<FlexLayout> {
        private FlexLayout self = getBoundComponent();
        private Runnable callbackFunction;

        public CreateTournament() {
            self.addClassName("create-tournament");
            self.setJustifyContent(FlexJustifyContent.CENTER);
            self.setDirection(FlexDirection.COLUMN);
            self.setAlignment(FlexAlignment.CENTER);
            self.setMaxWidth(600);
            self.setSpacing("20px");

            Img img = new Img("ws://create-tournament.svg", "Create a new tournament").addClassName("create-tournament-image");
            H3 heading = new H3("You don't have any tournaments.").addClassName("create-tournament-heading");

            Button button = new Button("Create a new tournament").addClassName("create-tournament-button");
            button.setExpanse(Expanse.LARGE);
            button.onClick(e -> {
                if (newTournament == null) {
                    newTournament = new NewTournament();
                    newTournament.onAddTournament(callbackFunction);
                    self.add(newTournament);
                }
                newTournament.open();
            });

            self.add(img, heading, button);
        }

        public void onAddTournament(Runnable function) {
            this.callbackFunction = function;
        }

        public void setVisible(Boolean visible) {
            self.setVisible(visible);
        }
    }

}
