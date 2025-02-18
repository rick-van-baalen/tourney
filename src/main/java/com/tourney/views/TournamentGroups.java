package com.tourney.views;

import com.tourney.components.GroupEditor;
import com.tourney.components.GroupEditor.GroupSavedEvent;
import com.tourney.models.Group;
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

public class TournamentGroups extends Composite<Div> {

    private Tournament tournament;
    private Table<Group> table;
    private TournamentService tournamentService = new TournamentService();
    private GroupEditor editor = new GroupEditor();

    public TournamentGroups(Tournament tournament) {
        this.tournament = tournament;

        FlexLayout layout = new FlexLayout()
            .setDirection(FlexDirection.COLUMN)
            .setHeight("calc(100% - 20px)")
            .setSpacing("7px")
            .setStyle("padding", "10px");
        getBoundComponent().add(layout, editor);

        FlexLayout buttonLayout = new FlexLayout();
        layout.add(buttonLayout);

        Button newButton = new Button("Add new group");
        newButton.onClick(e -> {
            editor.open(tournament);
        });
        buttonLayout.add(newButton);
        
        table = new Table<Group>();
        table.setWidth("100%");
        table.setRowHeight(50);

        Column name = table.addColumn("Name", Group::getName);
        name.setPinDirection(Column.PinDirection.LEFT);

        table.addColumn("Participants", Group::getParticipantsAsString);

        VoidElementRenderer<Group> editRenderer = new VoidElementRenderer<>("dwc-icon-button", ev -> editor.open(tournament, ev.getItem()));
        editRenderer.setAttribute("name", "pencil-pin");
        Column edit = table.addColumn(editRenderer);
        edit.setAlignment(Column.Alignment.CENTER);
        edit.setPinDirection(Column.PinDirection.RIGHT);

        VoidElementRenderer<Group> deleteRenderer = new VoidElementRenderer<>("dwc-icon-button", this::onRemoveGroup);
        deleteRenderer.setAttribute("name", "trash");
        Column delete = table.addColumn(deleteRenderer);
        delete.setAlignment(Column.Alignment.CENTER);
        delete.setPinDirection(Column.PinDirection.RIGHT);

        CollectionRepository<Group> repo = new CollectionRepository<>(tournament.getGroups());
        table.setRepository(repo);

        editor.onSave(this::onGroupSaved);

        layout.add(table);
    }

    private void onGroupSaved(GroupSavedEvent ev) {
        Group group = ev.getGroup();

        if (group.getId() != null) {
            tournamentService.editGroup(tournament.getId(), group.getId(), group);
        } else {
            group = tournamentService.addGroup(tournament.getId(), group);
            tournament.getGroups().add(group);
        }
        
        table.getRepository().commit();
    }

    private void onRemoveGroup(RendererClickEvent<Group> ev) {
        Group group = ev.getItem();
        tournamentService.removeGroup(tournament.getId(), group.getId());

        tournament.getGroups().remove(group);
        table.getRepository().commit();
    }
    
}
