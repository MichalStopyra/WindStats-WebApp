package com.windstatsapp.ui;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.windstatsapp.ui.views.ListView;

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

    public MainLayout (){
        createHeader();
        createDrawer();
    }


    private void createHeader() {
        H1 logo = new H1("WindStats App");
        logo.addClassName("logo");
        logo.addClickListener( e-> UI.getCurrent().navigate("") );


        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("List", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listLink
                //new RouterLink("Dashboard", DashboardView.class)
        ));
    }
}
