package com.windstatsapp.ui.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.windstatsapp.backend.entity.Spot;


public class SpotInfoView extends VerticalLayout {
   // Binder<Spot> binder = new BeanValidationBinder<>(Spot.class);
    private Spot spot;
    VerticalLayout layout = new VerticalLayout();

    public SpotInfoView (Spot spot) {

        this.spot = spot;
    }

    public void removeComponents(){
        this.layout.removeAll();
    }

    public void configureSpotInfo(){
        if( this.spot == null )
            return;
        addClassName("spotInfoView");
//        binder.bindInstanceFields(this);
         //H1 logo;

        layout.setSpacing(true);
        H1 spotName = new H1(spot.getName());
//        H1 logo = new H1(binder.getBean().getName());
        spotName.addClassName("spotName");
        layout.add(spotName);
        Image picture = new Image(spot.getImgPath(), "spotImage");
        layout.add(picture);
        String text = new String();
        text = spot.getSpotInfoText();
        layout.add(text);
        /*try
        {
          //  picture = ImageIO.read(new File(spot.getImgPath()));
            layout.add(picture);

        }
        catch (IOException e)
        {
            String workingDir = System.getProperty("user.dir");
            System.out.println("Current working directory : " + workingDir);
            e.printStackTrace();
        }*/
        layout.addClassName("layout");
        layout.setWidth("100%");
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(layout);
     }

    public void setSpot(Spot spot) {
        this.spot=spot;
        configureSpotInfo();
        //binder.setBean(spot);
    }
}
