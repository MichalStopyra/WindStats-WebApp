package com.windstatsapp.ui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.windstatsapp.ui.MainLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Home | WindStats App")
public class MainView extends VerticalLayout {


    public MainView() {
        //createHeader();
        createInstruction();
        createSelects();
        createApplyButton();
    }

    private void createSelects() {
        Select<String> monthSelect = new Select<>();
        monthSelect.setItems("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        monthSelect.setLabel("Month");

        Select<String> knotsSelect = new Select<>();
        knotsSelect.setItems("0-10", "11-20", "21-30", "31-40", "41-50");
        knotsSelect.setLabel("Wind Strength");

        Select<String> spotTypeSelect = new Select<>();
        spotTypeSelect.setItems("Flat water", "Small Waves", "Choppy", "Huge Waves");
        spotTypeSelect.setLabel("Spot Type");

       // add(monthSelect, knotsSelect, spotTypeSelect);

        HorizontalLayout horizontalLayout = new HorizontalLayout(monthSelect, knotsSelect, spotTypeSelect);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.START);
        VerticalLayout temp = new VerticalLayout(horizontalLayout);
        temp.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(temp);
    }


    private void createInstruction() {
        String instruction = new String("Choose your preferences");
        VerticalLayout text = new VerticalLayout();
        text.add(instruction);
        text.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(text);
    }

    private void createApplyButton() {
        Button button = new Button("APPLY");
        button.addClickListener(this::showButtonClickedMessage);

        //navigating
        button.addClickListener( e -> UI.getCurrent().navigate("spotlist"));

        VerticalLayout temp = new VerticalLayout(button);
        temp.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(temp);
    }

    private void showButtonClickedMessage(ClickEvent<Button> buttonClickEvent) {
    }


   /* private void createHeader() {
        H1 logo = new H1("WindStats App");
        logo.addClassName("logo");

        VerticalLayout header = new VerticalLayout(logo);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(header);
    }*/
}
