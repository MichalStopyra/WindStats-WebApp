package com.windstatsapp.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.ui.MainLayout;

import java.util.List;

@PageTitle("Country | WindStatsApp" )
@Route(value = "country", layout = MainLayout.class)
public class CountryView extends VerticalLayout{

    private List<Spot> spotList;
    SpotInfoView spotInfoView;

    public CountryView () {
        spotInfoView = new SpotInfoView(null);
        HorizontalLayout content = new HorizontalLayout(spotInfoView);
        spotInfoView.setVisible(false);
        content.addClassName("content");
        content.setSizeFull();
        add(content);
        Button leftButton = new Button("Change Country", new Icon(VaadinIcon.ARROW_BACKWARD));
        leftButton.addClickListener(e -> UI.getCurrent().navigate("map"));
        add(leftButton);
        //this.spot = spot;
    }


}
