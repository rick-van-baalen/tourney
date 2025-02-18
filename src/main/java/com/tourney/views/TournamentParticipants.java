package com.tourney.views;

import com.tourney.components.ParticipantEditor;
import com.tourney.components.ParticipantEditor.ParticipantSavedEvent;
import com.tourney.models.Participant;
import com.tourney.models.Tournament;
import com.tourney.services.TournamentService;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.component.table.event.renderer.RendererClickEvent;
import com.webforj.component.table.renderer.VoidElementRenderer;
import com.webforj.data.repository.CollectionRepository;

public class TournamentParticipants extends Composite<Div> {

    private Tournament tournament;
    private Table<Participant> table;
    private TournamentService tournamentService = new TournamentService();
    private ParticipantEditor editor = new ParticipantEditor();

    public TournamentParticipants(Tournament tournament) {
        this.tournament = tournament;

        FlexLayout layout = new FlexLayout()
            .setDirection(FlexDirection.COLUMN)
            .setHeight("calc(100% - 20px)")
            .setSpacing("7px")
            .setStyle("padding", "10px");
        getBoundComponent().add(layout, editor);

        FlexLayout buttonLayout = new FlexLayout();
        layout.add(buttonLayout);

        Button newButton = new Button("Add new participant");
        newButton.onClick(e -> {
            editor.open();
        });
        buttonLayout.add(newButton);
        
        table = new Table<Participant>();
        table.setWidth("100%");
        table.setRowHeight(50);

        Column name = table.addColumn("Name", Participant::getName);
        name.setPinDirection(Column.PinDirection.LEFT);

        VoidElementRenderer<Participant> editRenderer = new VoidElementRenderer<>("dwc-icon-button", ev -> editor.open(ev.getItem()));
        editRenderer.setAttribute("name", "pencil-pin");
        Column edit = table.addColumn(editRenderer);
        edit.setAlignment(Column.Alignment.CENTER);
        edit.setPinDirection(Column.PinDirection.RIGHT);

        VoidElementRenderer<Participant> deleteRenderer = new VoidElementRenderer<>("dwc-icon-button", this::onRemoveParticipant);
        deleteRenderer.setAttribute("name", "trash");
        Column delete = table.addColumn(deleteRenderer);
        delete.setAlignment(Column.Alignment.CENTER);
        delete.setPinDirection(Column.PinDirection.RIGHT);

        CollectionRepository<Participant> repo = new CollectionRepository<>(tournament.getParticipants());
        table.setRepository(repo);

        editor.onSave(this::onParticipantSaved);

        layout.add(table);
    }

    private void onParticipantSaved(ParticipantSavedEvent ev) {
        Participant participant = ev.getParticipant();

        if (participant.getId() != null) {
            tournamentService.editParticipant(tournament.getId(), participant.getId(), participant);
        } else {
            participant = tournamentService.addParticipant(tournament.getId(), participant);
            tournament.getParticipants().add(participant);
        }
        
        table.getRepository().commit();
    }

    private void onRemoveParticipant(RendererClickEvent<Participant> ev) {
        Participant participant = ev.getItem();
        tournamentService.removeParticipant(tournament.getId(), participant.getId());

        tournament.getParticipants().remove(participant);
        table.getRepository().commit();
    }
    
}
