package com.tourney.components;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.text.Label;

public class NewTournament extends Composite<Dialog> {

    private Dialog self = getBoundComponent();

    public NewTournament() {
        self.setMoveable(false);
        self.setCancelOnOutsideClick(false);

        Label heading = new Label("New tournament");
        self.addToHeader(heading);

        Button button = new Button("Create new tournament").setExpanse(Expanse.LARGE);
        button.onClick(e -> {
            
        });
        self.addToFooter(button);
    }

    public void open() {
        self.open();
    }

    public void close() {
        self.close();
    }
    
}
