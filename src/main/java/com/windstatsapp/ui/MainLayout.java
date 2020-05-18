package com.windstatsapp.ui;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.windstatsapp.ui.views.ListView;
import com.windstatsapp.ui.views.MapView;

@PWA(
        name = "WindStatsApp",
        shortName = "WSAp",
        iconPath = "./icons/icon.png",
        offlineResources = {
                "./styles/offline.css",
                "./images/offline.png" }

)

//@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    MapView m = new MapView();
    public MainLayout (){
        m.setVisible(false);
        createHeader();
        createDrawer();
    }


    private void createHeader() {
        H1 logo = new H1("WindStats App");
        logo.addClassName("logo");
        logo.addClickListener( e-> UI.getCurrent().navigate("") );

        Image icon = new Image("./icons/icon_smaller.png", "spotImage");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), icon, logo);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("List", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink mapLink = new RouterLink("Map", MapView.class);
        mapLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listLink,
                mapLink
                //new RouterLink("Dashboard", DashboardView.class)
        ));
    }
}
