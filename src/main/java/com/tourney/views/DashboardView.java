package com.tourney.views;

import com.tourney.components.NewTournament;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Anchor;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Img;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.annotation.RouteAlias;

@Route(value = "/", outlet = MainLayout.class)
@RouteAlias(value = "/dashboard")
@FrameTitle("Dashboard")
public class DashboardView extends Composite<FlexLayout> {

    private FlexLayout self = getBoundComponent();
    private NewTournament newTournament;

    public DashboardView() {
        self.setHeight("100%");
        self.setAlignment(FlexAlignment.CENTER);
        self.add(new CreateTournament());
    }

    class CreateTournament extends Composite<FlexLayout> {
        private FlexLayout self = getBoundComponent();

        public CreateTournament() {
            self.addClassName("create-tournament");
            self.setDirection(FlexDirection.COLUMN);
            self.setAlignment(FlexAlignment.CENTER);
            self.setMaxWidth(600);
            self.setSpacing("20px");

            Img img = new Img("ws://create-tournament.svg", "Create a new tournament").addClassName("create-tournament-image");
            H3 heading = new H3("You don't have any tournaments.").addClassName("create-tournament-heading");

            Button button = new Button("Create a new tournament").addClassName("create-tournament-button");
            button.setExpanse(Expanse.LARGE);
            button.onClick(e -> {
                if (newTournament == null) {
                    newTournament = new NewTournament();
                    self.add(newTournament);
                }
                newTournament.open();
            });

            self.add(img, heading, button);
        }
    }

}
