package com.tourney.views;

import com.tourney.models.Participant;
import com.tourney.models.Tournament;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Table;
import com.webforj.data.repository.CollectionRepository;

public class TournamentParticipants extends Composite<Div> {

    private Tournament tournament;

    public TournamentParticipants(Tournament tournament) {
        this.tournament = tournament;
        
        FlexLayout layout = new FlexLayout()
            .setDirection(FlexDirection.COLUMN)
            .setHeight("100%")
            .setSpacing("7px");
        getBoundComponent().add(layout);

        FlexLayout buttonLayout = new FlexLayout();
        layout.add(buttonLayout);

        Button newButton = new Button("Add new participant");
        buttonLayout.add(newButton);
        
        Table<Participant> table = new Table<Participant>();
        table.setWidth("100%");
        table.setRowHeight(50);

        table.addColumn("Name", Participant::getName);

        CollectionRepository<Participant> repo = new CollectionRepository<>(tournament.getParticipants());
        table.setRepository(repo);

        layout.add(table);
    }
    
}
