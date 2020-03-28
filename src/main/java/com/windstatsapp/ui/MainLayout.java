package com.windstatsapp.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


//@CssImport("")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createLists();
    }

    private void createLists() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        ListBox<String> listBox = new ListBox<>();
        listBox.setItems("January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December");

        addToNavbar(horizontalLayout);
        //listBox.setValue("Option one");
    }

    private void createHeader() {
        H1 logo = new H1("WindStats App");
        logo.addClassName("logo");

        VerticalLayout header = new VerticalLayout(logo);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);
    }
}
