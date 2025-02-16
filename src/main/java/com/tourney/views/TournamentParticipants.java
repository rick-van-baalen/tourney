package com.tourney.views;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tourney.models.Participant;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.utilities.Assets;

public class TournamentParticipants extends Composite<Div> {

    public TournamentParticipants() {
        Table<Participant> table = new Table<Participant>();
        table.setWidth("100%");
        table.setHeight("100%");
        table.setRowHeight(50);

        table.addColumn("Name", Participant::getName);

        getBoundComponent().add(table);
    }
    
}
