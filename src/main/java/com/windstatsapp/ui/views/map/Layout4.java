package com.windstatsapp.ui.views.map;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;

public class Layout4 extends Div implements RouterLayout, PageConfigurator {

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addInlineFromFile("/jquery-jvectormap-2.0.5/index.html",
                InitialPageSettings.WrapMode.NONE);
    }

    @Override
    public Element getElement() {
        return null;
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {

    }

    @Override
    public void removeRouterLayoutContent(HasElement oldContent) {

    }
}
