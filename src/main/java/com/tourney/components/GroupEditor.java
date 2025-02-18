package com.tourney.components;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import com.tourney.models.Group;
import com.tourney.models.Participant;
import com.tourney.models.Tournament;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ListBox;
import com.webforj.component.list.ListItem;
import com.webforj.component.list.MultipleSelectableList.SelectionMode;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

public class GroupEditor extends Composite<Dialog> {

    private EventDispatcher dispatcher = new EventDispatcher();
    private H3 heading = new H3("");
    private TextField nameField = new TextField("Name");
    private ListBox participantsField = new ListBox("Participants");
    private Tournament tournament;
    private Group group;

    public GroupEditor() {
        Dialog dialog = getBoundComponent();
        dialog.setMaxWidth("400px");
        dialog.onOpen(ev -> nameField.focus());

        dialog.addToHeader(heading);

        participantsField.setSelectionMode(SelectionMode.MULTIPLE);
        participantsField.setMinHeight("200px");
        dialog.add(nameField, participantsField);

        FlexLayout footer = FlexLayout.create().align().end().build();
        dialog.addToFooter(footer);

        Button saveButton = new Button("Save", ButtonTheme.PRIMARY, ev -> save());
        Button cancelButton = new Button("Cancel", ev -> dialog.close());
        footer.add(saveButton, cancelButton);

        nameField.onKeypress(ev -> {
            if (ev.getKeyCode() == KeypressEvent.Key.ENTER) save();
        });
    }

    public void open(Tournament tournament) {
        this.tournament = tournament;
        this.group = null;

        heading.setText("Add new group");
        nameField.setText("");

        resetParticipants();
        populateParticipants();

        getBoundComponent().open();
    }

    public void open(Tournament tournament, Group group) {
        this.tournament = tournament;
        this.group = group;

        heading.setText("Edit group '" + group.getName() + "'");
        nameField.setText(group.getName());

        resetParticipants();
        populateParticipants();
        selectParticipants();

        getBoundComponent().open();
    }

    private void populateParticipants() {
        for (Participant pt : this.tournament.getParticipants()) {
            participantsField.add(new ListItem(pt.getId(), pt.getName()));
        }
    }

    private void selectParticipants() {
        for (Participant pt : this.group.getParticipants()) {
            participantsField.selectKey(pt.getId());
        }
    }

    private void resetParticipants() {
        participantsField.deselectAll();
        participantsField.removeAll();
    }

    public ListenerRegistration<GroupSavedEvent> onSave(EventListener<GroupSavedEvent> listener) {
        return dispatcher.addListener(GroupSavedEvent.class, listener);
    }

    private void save() {
        if (group == null) group = new Group();
        group.setName(nameField.getText());

        List<Participant> participants = new ArrayList<Participant>();
        
        for (ListItem item : participantsField.getSelectedItems()) {
            Participant pt = new Participant();
            pt.setId(Integer.valueOf(item.getKey().toString()));
            pt.setName(item.getText());
            participants.add(pt);
        }

        group.setParticipants(participants);
        
        getBoundComponent().close();
        dispatcher.dispatchEvent(new GroupSavedEvent(this));
    }

    public class GroupSavedEvent extends EventObject {

        public GroupSavedEvent(Object source) {
            super(source);
        }

        public Group getGroup() {
            return ((GroupEditor) getSource()).group;
        }
    }
}