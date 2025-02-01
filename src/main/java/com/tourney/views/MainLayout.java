package com.tourney.views;

import java.util.Set;

import com.webforj.component.Component;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H1;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.applayout.AppLayout.DrawerPlacement;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.component.tabbedpane.event.TabSelectEvent;
import com.webforj.dispatcher.ListenerRegistration;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.event.NavigateEvent;
import com.webforj.router.history.Location;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.DidEnterObserver;

@Route
public class MainLayout extends Composite<AppLayout> implements DidEnterObserver {

    private static final String DEFAULT_VIEW = "dashboard";
    private AppLayout self = getBoundComponent();
    private TabbedPane nav = new TabbedPane();
    private H1 title = new H1();
    private ListenerRegistration<TabSelectEvent> registration;

    public MainLayout() {
        setHeader();
        setNav();
        Router.getCurrent().onNavigate(this::onNavigate);
    }

    @Override
    public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
        setSelectListener();
    }

    private void setHeader() {
        self.setDrawerPlacement(DrawerPlacement.HIDDEN);

        Toolbar toolbar = new Toolbar();
        toolbar.addToTitle(title);

        self.addToHeader(toolbar);
    }

    private void setNav() {
        nav.addClassName("app-nav");
        nav.setBodyHidden(true);
        nav.setBorderless(true);
        nav.setPlacement(TabbedPane.Placement.BOTTOM);
        nav.setAlignment(TabbedPane.Alignment.CENTER);

        nav.addTab(new Tab("Dashboard", TablerIcon.create("dashboard")));
        nav.addTab(new Tab("Settings", TablerIcon.create("settings")));

        self.addToFooter(nav);
    }

    private void onNavigate(NavigateEvent ev) {
        setAppTitle(ev);
        setSelectedTab(ev);
    }

    private void setAppTitle(NavigateEvent ev) {
        Set<Component> components = ev.getContext().getAllComponents();
        Component view = components.stream().filter(c -> c.getClass().getSimpleName().endsWith("View")).findFirst()
                .orElse(null);

        if (view != null) {
            FrameTitle frameTitle = view.getClass().getAnnotation(FrameTitle.class);
            title.setText(frameTitle != null ? frameTitle.value() : "");
        }
    }

    private void setSelectedTab(NavigateEvent ev) {
        String path = ev.getLocation().getFullURI().substring(1);
        if (path.isEmpty()) {
            path = DEFAULT_VIEW;
        }

        for (Tab tab : nav.getTabs()) {
            if (tab.getText().toLowerCase().equals(path)) {
                // temporarily remove the listener to avoid history push
                removeSelectListener();
                nav.select(tab);
                setSelectListener();
                break;
            }
        }
    }

    private void setSelectListener() {
        registration = nav.onSelect(ev -> {
            String tab = ev.getTab().getText().toLowerCase();
            Router.getCurrent().navigate(new Location(tab));
        });
    }

    private void removeSelectListener() {
        if (registration != null) {
            registration.remove();
        }
    }

}
