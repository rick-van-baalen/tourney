package com.tourney.components;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.field.NumberField;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.text.Label;

public class NewTournament extends Composite<Dialog> {

    private Dialog self = getBoundComponent();

    public NewTournament() {
        self.addClassName("new-tournament");
        self.setMoveable(false);
        self.setCancelOnOutsideClick(false);

        Label heading = new Label("New tournament");
        self.addToHeader(heading);

        FlexLayout body = new FlexLayout().setDirection(FlexDirection.COLUMN);
        self.addToContent(body);

        TextField nameField = new TextField("Tournament name").setExpanse(Expanse.LARGE);
        NumberField numberOfParticipantsField = new NumberField("Number of participants")
            .setExpanse(Expanse.LARGE)
            .setMin(0.0)
            .setStep(1.0);
        body.add(nameField, numberOfParticipantsField);

        FlexLayout footer = new FlexLayout()
            .setAlignment(FlexAlignment.CENTER)
            .setJustifyContent(FlexJustifyContent.END);
        self.addToFooter(footer);
        
        Button button = new Button("Create new tournament")
            .setExpanse(Expanse.LARGE)
            .addClassName("new-tournament-button");
        button.onClick(e -> {
            
        });
        footer.add(button);
    }

    public void open() {
        self.open();
    }

    public void close() {
        self.close();
    }
    
}
