package com.tourney.components;

import com.tourney.models.Tournament;
import com.tourney.services.TournamentService;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.field.TextField;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexContentAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.text.Label;
import com.webforj.data.binding.BindingContext;
import com.webforj.data.validation.server.ValidationResult;

public class NewTournament extends Composite<Dialog> {

    private Dialog self = getBoundComponent();
    private Runnable onAddTournament;
    private BindingContext<Tournament> context;
    private TextField nameField;
    private Tournament tournament;
    private TournamentService tournamentService = new TournamentService();

    public NewTournament() {
        self.addClassName("new-tournament");
        self.setMoveable(false);
        self.setCancelOnEscKey(true);
        self.setCancelOnOutsideClick(false);
        self.setMaxWidth("500px");

        FlexLayout header = new FlexLayout()
            .setAlignContent(FlexContentAlignment.CENTER)
            .setAlignment(FlexAlignment.CENTER)
            .setJustifyContent(FlexJustifyContent.BETWEEN);
        self.addToHeader(header);

        Label heading = new Label("New tournament");
        Button close = new Button(TablerIcon.create("x"));
        close.onClick(e -> self.close());
        header.add(heading, close);

        FlexLayout body = new FlexLayout().setDirection(FlexDirection.COLUMN);
        self.addToContent(body);

        context = BindingContext.of(this, Tournament.class);
        tournament = new Tournament();

        nameField = new TextField("Tournament name").setExpanse(Expanse.LARGE);

        context.bind(nameField, "name")
            .useValidator(value -> value != null && !value.isEmpty(), "Tournament name cannot be empty.")
            .useValidator(value -> value != null && value.length() >= 4, "Tournament name must be at least 4 characters long.")
            .add();

        body.add(nameField);

        FlexLayout footer = new FlexLayout()
            .setAlignment(FlexAlignment.CENTER)
            .setJustifyContent(FlexJustifyContent.END);
        self.addToFooter(footer);
        
        Button button = new Button("Create new tournament")
            .setExpanse(Expanse.LARGE)
            .addClassName("create-new-tournament-button");
        button.onClick(e -> onCreateNewTournament());
        footer.add(button);
    }

    public void onAddTournament(Runnable onAddTournament) {
        this.onAddTournament = onAddTournament;
    }

    private void onCreateNewTournament() {
        ValidationResult results = context.write(tournament);
        if (results.isValid()) {
            // Close the dialog.
            self.close();

            // Add the tournament in memory.
            tournamentService.addTournament(tournament);

            // Call the callback to add the new tournament to the Tournaments UI
            onAddTournament.run();

            // Reset the form.
            tournament = new Tournament();
            nameField.setText("");
        }
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void open() {
        self.open();
    }

    public void close() {
        self.close();
    }
    
}
