package com.windstatsapp.ui.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.windstatsapp.backend.entity.Country;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.ui.views.spotInfoView.SpotInfoView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    List<Country> countries;
    private Spot warsaw;
    private Country country1;

    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenSpotSelected() {
        Grid<Spot> grid = listView.grid;
        grid.setItems(warsaw);
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

   /* @Before
    public void setupData() {
        countries = new ArrayList<>();
        country1 = new Country("Poland");
        countries.add(country1);

        warsaw = new Spot();
        warsaw.setName("Warsaw");
        warsaw.setSpotInfoText("Warsaw is nice");
        warsaw.setSpotInfoTextPath("none");
        warsaw.setImgPath("none");
        warsaw.setAvgTemperature(35);
        warsaw.setAvgGustSpeed(30);
        warsaw.setWindPercentage(100);
        warsaw.setAvgWindSpeed(25.0);
        warsaw.setLatitude(52.229676);
        warsaw.setLongtitude(21.012229);
        warsaw.setType(Spot.Type.values()[1]);
        warsaw.setCountry(country1);
    }*/

   /* @Test
    public void formFieldsPopulated() {
        ContactForm form = new ContactForm(companies);
        form.setContact(marcUsher);
        Assert.assertEquals("Marc", form.firstName.getValue());
        Assert.assertEquals("Usher", form.lastName.getValue());
        Assert.assertEquals("marc@usher.com", form.email.getValue());
        Assert.assertEquals(company2, form.company.getValue());
        Assert.assertEquals(Contact.Status.NotContacted, form.status.getValue());
    }


*/
}
