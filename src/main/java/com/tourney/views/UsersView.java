package com.tourney.views;

import com.tourney.components.Explore;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route(value = "/users", outlet = MainLayout.class)
@FrameTitle("Users")
public class UsersView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public UsersView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Users"));
  }
}
