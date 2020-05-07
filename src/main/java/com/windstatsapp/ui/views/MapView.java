package com.windstatsapp.ui.views;

import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.windstatsapp.backend.weatherapi.UserPreferences;
import com.windstatsapp.ui.MainLayout;

/**
 * A Designer generated component for the map-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("map-view")
@PageTitle("Map | WindStatsApp" )
@JsModule("./map-view.js")
@Route(value = "map", layout = MainLayout.class)
public class MapView extends PolymerTemplate<MapView.MapViewModel> {



    public MapView() {

    }

@EventHandler
private void handleClick( @EventData("event.srcElement.id") String name) {
    System.out.println("Country name: " + name);
    setCountry(name);
    UI.getCurrent().navigate("country");
}

private void setCountry(String countryName) {
        if(countryName!=null) {
            UserPreferences.country = countryName;
        }
}


    /**
     * This model binds properties between MapView and map-view
     */
    public interface MapViewModel extends TemplateModel {

        void setR(String r);
        String getR();


    }



}
