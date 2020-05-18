package com.windstatsapp.ui.views.spotInfoView;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.backend.service.SpotService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SpotInfoView extends VerticalLayout {
    private Spot spot;
    VerticalLayout layout = new VerticalLayout();
    private boolean forecastFlag;
    Grid<EachMonthSpotHelper> grid = new Grid<>(EachMonthSpotHelper.class);
    private SpotService spotService;


    public SpotInfoView (Spot spot, SpotService spotService) {
        this.spotService = spotService;
        this.spot = spot;

    }

    public void removeComponents(){
        this.layout.removeAll();
    }

    public void configureSpotInfo(){
        if( this.spot == null )
            return;
        addClassName("spotInfoView");


        layout.setSpacing(true);
        H1 spotName = new H1(spot.getName());
        spotName.addClassName("spotName");
        layout.add(spotName);
        Image picture = new Image(spot.getImgPath(), "spotImage");
        layout.add(picture);
        String text = new String();
        text = spot.getSpotInfoText();
        String space = "\n";
        layout.add(space);
        layout.add(text);
        layout.add(grid);
        grid.setVisible(forecastFlag);

        layout.addClassName("layout");
        layout.setWidth("100%");
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(layout);
     }

    public void setSpot(Spot spot) {
        this.spot=spot;
        configureSpotInfo();
        addEachMonthStats();
    }

    public Spot getSpot() {
        return spot;
    }

    public void setForecastFlag(boolean flag){
        this.forecastFlag = flag;
    }

    public void addEachMonthStats(){
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        // for (int tempYear = 2019; tempYear <= 2020; ++tempYear)
        List<EachMonthSpotHelper> eachMonthSpotHelperList= new ArrayList<>();


        for(int j = 0; j < months.size(); ++j) {
            EachMonthSpotHelper temp = new EachMonthSpotHelper(spotService);
            temp.setMonth(months.get(j));
            temp.setSpot(this.spot);
            temp.setParameters();
            eachMonthSpotHelperList.add(temp);

        }

            grid.addClassName("eachMonth-grid");

            grid.setItems(eachMonthSpotHelperList);
            grid.setColumns("month", "avgWindSpeed", "avgGustSpeed", "avgTemperature");

            grid.getColumns().forEach(col -> col.setAutoWidth(true));

            grid.getColumnByKey("avgWindSpeed").setHeader("Avg Wind [knots]").setSortable(false);
            grid.getColumnByKey("avgGustSpeed").setHeader("Avg Gust [knots]").setSortable(false);
            grid.getColumnByKey("avgTemperature").setHeader("Temperature [°C]").setSortable(false);
            grid.getColumnByKey("month").setSortable(false);
            grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
    }

}
