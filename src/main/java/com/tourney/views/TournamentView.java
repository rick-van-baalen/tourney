package com.tourney.views;

import com.tourney.models.Tournament;
import com.tourney.services.TournamentService;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H1;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.environment.ObjectTable;
import com.webforj.router.NavigationContext;
import com.webforj.router.annotation.Route;
import com.webforj.router.concern.HasFrameTitle;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.DidEnterObserver;

@Route(value = "/tournament/:id", outlet = MainLayout.class)
public class TournamentView extends Composite<FlexLayout> implements DidEnterObserver, HasFrameTitle {
    private FlexLayout self = getBoundComponent();
    private TabbedPane pane;
    private Tournament tournament;
    private TournamentService tournamentService = new TournamentService();

    public TournamentView() {
        self.setHeight("100%");

        pane = new TabbedPane().addClassName("tournament-tab");
        pane.setWidth("100%");
        pane.setHeight("100%");
        self.add(pane);

        pane.addTab(new Tab("Details", TablerIcon.create("file-description")));
        pane.setComponentFor(0, new TournamentDetails());

        pane.addTab(new Tab("Participants", TablerIcon.create("users")));
        pane.setComponentFor(1, new TournamentParticipants());

        pane.addTab(new Tab("Groups", TablerIcon.create("folders")));
        pane.setComponentFor(2, new TournamentGroups());

        pane.addTab(new Tab("Matches", TablerIcon.create("ladder")));
        pane.setComponentFor(3, new TournamentMatches());
    }

    @Override
    public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
        Integer id = parameters.getInt("id").get();
        tournament = tournamentService.getTournament(id);
        if (tournament == null) {
            OptionDialog.showMessageDialog("Something went wrong while showing your tournament. Please try again.");
            return;
        } else {
            setAppTitle();
        }
    }

    private void setAppTitle() {
        H1 title = (H1) ObjectTable.get("HEADER_TITLE");
        title.setText(tournament.getName());
    }

    @Override
    public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
        Integer id = parameters.getInt("id").get();
        tournament = tournamentService.getTournament(id);
        if (tournament == null) return "Page not found";
        return tournament.getName();
    }
}
