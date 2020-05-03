package com.windstatsapp.ui.views.map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;

@Tag("div")
//@JsModule("./src/my-module.js")
//@CssImport("./jquery-jvectormap-2.0.5/jquery-jvectormap-2.0.5.css")
//@JavaScript("./jquery-jvectormap-2.0.5/jquery-jvectormap-2.0.5.min.js")
//@JavaScript("./jquery-jvectormap-2.0.5/jquery-jvectormap-europe-merc.js")
//@JavaScript("https://code.jquery.com/jquery-3.5.0.min.js")

//@JsModule("./jquery-jvectormap-2.0.5/index.html")
//@HtmlImport("./jquery-jvectormap-2.0.5/index.html")
//@NpmPackage
//@JsModule("./jquery-jvectormap-2.0.5/index.html")
//static
class CustomComponent extends Component
        implements HasText {

    public void addDependencies() {
        UI.getCurrent().getPage()
                .addJavaScript("./jquery-jvectormap-2.0.5/jquery-jvectormap-2.0.5.min.js");
        UI.getCurrent().getPage()
                .addJavaScript("./jquery-jvectormap-2.0.5/jquery-jvectormap-europe-merc.js");
        UI.getCurrent().getPage()
                .addHtmlImport("./jquery-jvectormap-2.0.5/index.html");
        UI.getCurrent().getPage()
                .addJsModule("./jquery-jvectormap-2.0.5/index.html");
        // external JavaScript module
        //UI.getCurrent().getPage()
                //.addJsModule("https://unpkg.com/lodash@4.17.15");
    }
    CustomComponent(){
      //  JavaScript.Container europeMap;
        //europeMap.vectorMap

    }

    // implementation omitted
}