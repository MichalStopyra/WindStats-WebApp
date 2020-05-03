package com.windstatsapp.ui.views.map;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.windstatsapp.ui.MainLayout;

@PageTitle("Map | WindStatsApp" )
@Route(value = "map2", layout = MainLayout.class)

public class MapView2 extends VerticalLayout {

   // CustomComponent html = new CustomComponent();

   // com.vaadin.flow.component.HtmlComponent html1 = new com.vaadin.flow.component.HtmlComponent("./jquery-jvectormap-2.0.5/index.html");

    public MapView2 () {

     //   add(html/*, html1*/);
  //      html.addDependencies();
      //  addDependencies();
    }

    private void addDependencies() {

      //  UI.getCurrent().getPage().addHtmlImport("./jquery-jvectormap-2.0.5/index.html");
    }
}
