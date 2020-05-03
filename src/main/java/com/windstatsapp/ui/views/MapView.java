package com.windstatsapp.ui.views;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
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

    /**
     * Creates a new MapView.
     */
    public MapView() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between MapView and map-view
     */
    public interface MapViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
