package com.tourney.components;

import java.util.EventObject;

import com.tourney.models.Participant;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

public class ParticipantEditor extends Composite<Dialog> {

    private EventDispatcher dispatcher = new EventDispatcher();
    private H3 heading = new H3("");
    private TextField nameField = new TextField("Name");
    private Participant participant;

    public ParticipantEditor() {
        Dialog dialog = getBoundComponent();
        dialog.setMaxWidth("400px");
        dialog.onOpen(ev -> nameField.focus());

        dialog.addToHeader(heading);
        dialog.add(nameField);

        FlexLayout footer = FlexLayout.create().align().end().build();
        dialog.addToFooter(footer);

        Button saveButton = new Button("Save", ButtonTheme.PRIMARY, ev -> save());
        Button cancelButton = new Button("Cancel", ev -> dialog.close());
        footer.add(saveButton, cancelButton);

        nameField.onKeypress(ev -> {
            if (ev.getKeyCode() == KeypressEvent.Key.ENTER) save();
        });
    }

    public void open() {
        open(null);
    }

    public void open(Participant participant) {
        this.participant = participant;

        if (participant == null) {
            heading.setText("Add new participant");
            nameField.setText("");
        } else {
            heading.setText("Edit participant '" + participant.getName() + "'");
            nameField.setText(participant.getName());
        }

        getBoundComponent().open();
    }

    public ListenerRegistration<ParticipantSavedEvent> onSave(EventListener<ParticipantSavedEvent> listener) {
        return dispatcher.addListener(ParticipantSavedEvent.class, listener);
    }

    private void save() {
        if (participant == null) participant = new Participant();
        participant.setName(nameField.getText());
        getBoundComponent().close();
        dispatcher.dispatchEvent(new ParticipantSavedEvent(this));
    }

    public class ParticipantSavedEvent extends EventObject {

        public ParticipantSavedEvent(Object source) {
            super(source);
        }

        public Participant getParticipant() {
            return ((ParticipantEditor) getSource()).participant;
        }
    }
}