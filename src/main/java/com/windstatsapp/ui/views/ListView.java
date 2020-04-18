package com.windstatsapp.ui.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
import com.windstatsapp.backend.weatherapi.tools.Tools;
import com.windstatsapp.ui.MainLayout;

import java.util.ArrayList;


@PageTitle("Spots | WindStatsApp" )
@Route(value = "spotlist", layout = MainLayout.class)
public class ListView extends VerticalLayout {

    SpotService spotService;
    Grid<Spot> grid = new Grid<>(Spot.class);
    TextField filterText = new TextField();
    String darksky;
    //String chosenMonth;


    public ListView( SpotService spotService,
                     CountryService countryService) {
        this.spotService = spotService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        Div content = new Div(grid);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();

        String c = new String("assdad");
        add(c);
        ArrayList<String> list= Tools.DateArr("january");
        if (list.size()==0)
            add("wehszlo");
        for ( int i = 0; i<list.size(); ++i) {
            add(list.get(i)); }
        Tools.convertListToStream(Tools.DateArr("january")).forEach(s-> {
            String a = s.toString();

        });


    }


    private void configureGrid() {
        grid.addClassName("spot-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("country");
        grid.setColumns("name", "windPercentage", "type");
        grid.addColumn(spot -> {
            Country country = spot.getCountry();
            return country == null ? "-" : country.getName();
        }).setHeader("Country");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        //grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));

    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by spot name..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

  //      chosenMonth = "Month: Chosen Month";
        HorizontalLayout toolbar = new HorizontalLayout(filterText);
//        toolbar.add(chosenMonth);
        toolbar.addClassName("toolbar");
        return toolbar;
    }



    private void updateList() {
        grid.setItems(spotService.findAll(filterText.getValue()));
    }


  /*  private void displaySpotsList(SpotManager spotManager) {

        List<Spot> spotList = spotManager.getSpotList();


        Grid<Spot> grid = new Grid<>(Spot.class);
        grid.setItems(spotList);

       // grid.removeColumnByKey("id");

        grid.setColumns("name", "countryName", "windPercentage");

        VerticalLayout vl = new VerticalLayout(grid);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(vl);
    }*/


}
