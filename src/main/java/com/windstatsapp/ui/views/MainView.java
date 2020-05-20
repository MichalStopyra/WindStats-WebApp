package com.windstatsapp.ui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.windstatsapp.backend.service.SpotService;
import com.windstatsapp.backend.weatherapi.UserPreferences;
import com.windstatsapp.ui.MainLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Home | WindStats App")
public class MainView extends VerticalLayout {

    UserPreferences userPreferences = new UserPreferences();
    Binder<UserPreferences> binder = new Binder<>();

    SpotService spotService;
    Select<String> monthSelect = new Select<>();
    Select<String> knotsSelect = new Select<>();
    Select<String> spotTypeSelect = new Select<>();
    Button button = new Button("APPLY");



    public MainView(SpotService spotService) {
        this.spotService = spotService;
        createInstruction();
        createSelects();
        createApplyButton();
    }

    private void createSelects() {
        monthSelect.setItems("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        monthSelect.setLabel("Month");
        monthSelect.setId("month-select");
        monthSelect.setEmptySelectionAllowed(false);
        binder.forField(monthSelect)
                .asRequired(
                        "Please choose the appropriate month")
                .bind(UserPreferences::getMonthChoice, UserPreferences::setMonthChoice);

        knotsSelect.setItems("00-10", "11-20", "21-30", "31-40", "41-50");
        knotsSelect.setLabel("Wind Strength [knots]");
        knotsSelect.setId("knots-select");

        knotsSelect.setEmptySelectionAllowed(false);
        binder.forField(knotsSelect)
                .asRequired(
                        "Please choose the appropriate wind strength")
                .bind(UserPreferences::getWindChoice, UserPreferences::setWindChoice);

        spotTypeSelect.setItems("Flat Water", "Wave", "Chop");
        spotTypeSelect.setLabel("Spot Type");
        spotTypeSelect.setId("type-select");
        spotTypeSelect.setEmptySelectionAllowed(false);
        binder.forField(spotTypeSelect)
                .asRequired(
                        "Please choose the appropriate spot type")
                .bind(UserPreferences::getSpotTypeChoice, UserPreferences::setSpotTypeChoice);

        HorizontalLayout horizontalLayout = new HorizontalLayout(monthSelect, knotsSelect, spotTypeSelect);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.START);
        VerticalLayout temp = new VerticalLayout(horizontalLayout);
        temp.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(temp);
    }


    private void createInstruction() {
        String instruction = new String("Choose your preferences\nand WindStats App will list you the best wind/kitesurfing spots in Europe");
        VerticalLayout text = new VerticalLayout();
        text.add(instruction);
        text.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(text);
    }

    private void createApplyButton() {
        button.addClickListener(this::showButtonClickedMessage);

        button.addClickListener( event -> {
            if (binder.writeBeanIfValid(userPreferences)) {
                navigateToList();
            }
        });

        VerticalLayout temp = new VerticalLayout(button);
        temp.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(temp);
    }

    private void navigateToList() {
        UI.getCurrent().navigate("spotlist");
    }

    private void showButtonClickedMessage(ClickEvent<Button> buttonClickEvent) {
    }

}
