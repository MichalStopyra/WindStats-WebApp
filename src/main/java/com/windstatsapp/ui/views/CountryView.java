package com.windstatsapp.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.backend.service.CountryService;
import com.windstatsapp.backend.service.SpotService;
import com.windstatsapp.backend.weatherapi.UserPreferences;
import com.windstatsapp.ui.MainLayout;

import java.util.List;

@PageTitle("Country | WindStatsApp" )
@Route(value = "country", layout = MainLayout.class)
public class CountryView extends VerticalLayout{

    private List<Spot> spotList;
    SpotInfoView spotInfoView;
    Grid<Spot> grid = new Grid<>(Spot.class);
    SpotService spotService;
    String countryName;


    public CountryView ( SpotService spotService,
                        CountryService countryService ) {
        if (UserPreferences.country.isEmpty() || UserPreferences.country == null)
            System.out.println("asdsad");
         //   UI.getCurrent().navigate("");
        countryName = new String(UserPreferences.country);

        this.spotService = spotService;

        addClassName("country-view");

        configureTreeGrid();
        spotInfoView = new SpotInfoView(null, spotService);
        spotInfoView.setVisible(false);

        Button leftButton = new Button("Change Country", new Icon(VaadinIcon.ARROW_BACKWARD));
        leftButton.addClickListener(e -> UI.getCurrent().navigate("map"));

        HorizontalLayout horizontalLayout1 = new HorizontalLayout(leftButton);
        horizontalLayout1.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        horizontalLayout1.add(countryName);
        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout1, grid);

        HorizontalLayout content = new HorizontalLayout(verticalLayout, spotInfoView);
        content.addClassName("content");
        content.setSizeFull();
        add(content);

        grid.asSingleSelect().addValueChangeListener(evt -> displaySpotInfo(evt.getValue()));
    }

    private void configureTreeGrid() {
        grid.addClassName("country-grid");

        spotList = spotService.filterSpotByCountry();
        if(spotList.isEmpty()) {
            Dialog dialog = new Dialog();

            dialog.setCloseOnOutsideClick(false);
            dialog.setCloseOnEsc(false);

            Label messageLabel = new Label("No spots in selected country");

            NativeButton returnButton = new NativeButton("Return", event -> {
                dialog.close();
                UI.getCurrent().navigate("map");
            });
            VerticalLayout vl = new VerticalLayout(messageLabel, returnButton);
            vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            dialog.add(vl);
            dialog.open();
        }

        grid.setItems(spotList);


        grid.setColumns("name", "type");
        grid.setSortableColumns("type");
        grid.removeColumnByKey("type");
        grid.addColumn(spot -> {
            Spot.Type type = spot.getType();
            return type == null ? "-" : spot.getType().toString().replaceAll("_", " ");
        }).setHeader("Type");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private void displaySpotInfo(Spot spot) {
        if (spot == null) {
            closeSpotInfo();
        } else {
            if(spotInfoView.isVisible() )
                spotInfoView.removeComponents();
            spotInfoView.setForecastFlag(true);
            spotInfoView.setSpot(spot);
            spotInfoView.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeSpotInfo() {
        spotInfoView.setForecastFlag(false);
        spotInfoView.removeComponents();
        spotInfoView.setVisible(false);
        removeClassName("spotInfo");
    }

}
