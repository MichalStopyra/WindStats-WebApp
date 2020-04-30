package com.windstatsapp.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.windstatsapp.backend.entity.Country;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.backend.service.CountryService;
import com.windstatsapp.backend.service.SpotService;
import com.windstatsapp.backend.weatherapi.UserPreferences;
import com.windstatsapp.ui.MainLayout;
import com.windstatsapp.ui.views.spotInfo.SpotInfoView;


@PageTitle("Spots | WindStatsApp" )
@Route(value = "spotlist", layout = MainLayout.class)
public class ListView extends VerticalLayout {

    SpotInfoView spotInfoView;
    SpotService spotService;
    Grid<Spot> grid = new Grid<>(Spot.class);
    TextField filterText = new TextField();
    Boolean TypeBoxValue = false;


    public ListView( SpotService spotService,
                     CountryService countryService) {
        this.spotService = spotService;
        addClassName("list-view");
        setSizeFull();

        //UserPreferences.spotTypeChoice = UserPreferences.spotTypeChoice.replaceAll(" ", "_");


        if(UserPreferences.monthChoice != null /* UserPreferences.monthChoice.isEmpty()*/) {
            updateListWithMonth();
            UserPreferences.spotTypeChoice = UserPreferences.spotTypeChoice.replaceAll(" ", "_");
        }

        configureGrid();

        spotInfoView = new SpotInfoView(null);

        //Div content = new Div(grid, spotInfoView);
        HorizontalLayout content = new HorizontalLayout(grid, spotInfoView);
        spotInfoView.setVisible(false);
        content.addClassName("content");
        content.setSizeFull();
        add(getToolBar(), content);

        if(!TypeBoxValue)
            updateListFalseTypeBox();
        else
            updateListTrueTypeBox();

    }


    private void configureGrid() {
        grid.addClassName("spot-grid");
        grid.setSizeFull();
        //grid.removeColumnByKey("country");
        //grid.removeColumnByKey("name");

        grid.setColumns("name", "windPercentage", "avgWindSpeed", "avgGustSpeed", "avgTemperature");
        grid.addColumn(spot -> {
            Spot.Type type = spot.getType();
            return type == null ? "-" : spot.getType().toString().replaceAll("_", " ");
        }).setHeader("Type");

        grid.addColumn(spot -> {
            Country country = spot.getCountry();
            return country == null ? "-" : country.getName();
        }).setHeader("Country");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.getColumnByKey("avgWindSpeed").setHeader("Wind Speed [knots] ");
        grid.getColumnByKey("avgGustSpeed").setHeader("Gust Speed [knots] ");
        grid.getColumnByKey("avgTemperature").setHeader("Temperature [°C] ");


        grid.setSortableColumns("windPercentage");
        grid.asSingleSelect().addValueChangeListener(evt -> displaySpotInfo(evt.getValue()));

    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by spot name..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateListFalseTypeBox());

        Checkbox checkbox = new Checkbox();
        if( UserPreferences.spotTypeChoice != null)
          //  checkbox.setLabel("Show only Flat Water Spots");
            checkbox.setLabel("Show Only " + UserPreferences.spotTypeChoice.replaceAll("_", " ") + " Spots");
        else
            checkbox.setLabel("Empty list");

        checkbox.setValue(false);
        checkbox.addValueChangeListener(e->{
                                            TypeBoxValue = checkbox.getValue();
                                            if(TypeBoxValue)
                                                updateListTrueTypeBox();
                                            else
                                                updateListFalseTypeBox();
        });

        Button leftButton = new Button("Change Preferences", new Icon(VaadinIcon.ARROW_BACKWARD));
        leftButton.addClickListener(e -> UI.getCurrent().navigate(""));



        //      chosenMonth = "Month: Chosen Month";
        HorizontalLayout toolbar = new HorizontalLayout(filterText, checkbox, leftButton);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
//        toolbar.add(chosenMonth);

        toolbar.addClassName("toolbar");
        return toolbar;
    }



    private void updateListFalseTypeBox() {
        grid.setItems(spotService.findAll(filterText.getValue()));
    }

    private void updateListTrueTypeBox() { grid.setItems(spotService.filterSpotByType(filterText.getValue())); }


    private void updateListWithMonth() {
        spotService.setSpotParameters();
        grid.setItems(spotService.findAll(filterText.getValue()));
    }

    private void displaySpotInfo(Spot spot) {
        if (spot == null) {
            closeSpotInfo();
        } else {
            spotInfoView.setSpot(spot);
            spotInfoView.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeSpotInfo() {
        spotInfoView.removeComponents();
        spotInfoView.setVisible(false);
        removeClassName("spotInfo");
    }


}
