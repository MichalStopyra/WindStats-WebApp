package com.windstatsapp.ui.views;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.backend.weatherapi.UserPreferences;
import com.windstatsapp.ui.views.spotInfoView.SpotInfoView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    @Test
    public void SpotInfoShownWhenSpotSelected() {
        Grid<Spot> grid = listView.grid;

        Spot firstSpot = getFirstItem(grid);

        SpotInfoView spotInfoView = listView.spotInfoView;

        Assert.assertFalse(spotInfoView.isVisible());
        grid.asSingleSelect().setValue(firstSpot);
        Assert.assertTrue(spotInfoView.isVisible());
        Assert.assertEquals(firstSpot, spotInfoView.getSpot());
    }

    private Spot getFirstItem(Grid<Spot> grid) {
        return( (ListDataProvider<Spot>) grid.getDataProvider()).getItems().iterator().next();
    }

    @Test
    public void updateListSpotTypeTest() {
        Grid<Spot> grid = listView.grid;
        Checkbox typeCheckBox = listView.checkbox;
        TextField filterText = listView.filterText;
        Spot temp;
        Object[] list = ( (ListDataProvider<Spot>) grid.getDataProvider()).getItems().toArray();
        int i = 0;
        UserPreferences.spotTypeChoice = "Wave";
        typeCheckBox.setValue(true);
        filterText.setValue("");
        listView.updateListTrueTypeBox();
            temp = (Spot) list[i];
            Assert.assertTrue(temp.getType().toString().compareTo("Wave") == 0 );
    }







}
