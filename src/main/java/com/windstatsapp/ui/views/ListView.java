package com.windstatsapp.ui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.windstatsapp.ui.MainLayout;


@Route(value = "", layout =  MainLayout.class)
@PageTitle("Spots | WindStatsApp " )
public class ListView extends VerticalLayout {


    public ListView(){

    }

}
