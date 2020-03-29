package com.windstatsapp.ui.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.backend.entity.SpotManager;

import java.util.List;


//@Route(value = "", layout =  MainLayout.class)
//@PageTitle("Spots | WindStatsApp" )
@Route("spotlist")
public class ListView extends VerticalLayout {


    public ListView(){

        //temporarily
        SpotManager spotManager = new SpotManager();

        displaySpotsList(/*temp*/ spotManager);

    }

    private void displaySpotsList(SpotManager spotManager) {

        List<Spot> spotList = spotManager.getSpotList();


        Grid<Spot> grid = new Grid<>(Spot.class);
        grid.setItems(spotList);

       // grid.removeColumnByKey("id");

        grid.setColumns("name", "countryName", "windPercentage");

        VerticalLayout vl = new VerticalLayout(grid);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(vl);
    }

}
