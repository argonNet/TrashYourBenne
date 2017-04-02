package com.il4.view;

import com.il4.BackgroundApplication;
import com.il4.view.component.BucheronView;
import com.il4.view.component.OuvrierView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Argon on 01.04.17.
 */
public class MainViewController implements Initializable{

    @FXML public Button buttonStart;

    @FXML public VBox bucheronsPane;
    @FXML public VBox ouvriersPane;

    @FXML public TextField textFieldBucheronName;
    @FXML public TextField textFieldOuvrierName;

    @FXML public ProgressBar ProgressBarOuvrier;
    @FXML public ProgressBar ProgressBarBucheron;
    @FXML public Slider SliderTransporteur;

    @FXML public ListView<String> ListTransporteurWaitingBenneBucheron;
    @FXML public ListView<String> ListTransporteurWaitingBenneOuvrier;
    @FXML public ListView<String> ListBucheronWaitingBenne;
    @FXML public ListView<String> ListOuvrierWaitingBenne;

    @FXML public Label labelBucheron;
    @FXML public Label labelOuvrier;
    @FXML public Label labelTransporter;

    public WaitingBenneViewController TransporteurWaitingBenneBucheron;
    public WaitingBenneViewController TransporteurWaitingBenneOuvrier;
    public WaitingBenneViewController BucheronWaitingBenne;
    public WaitingBenneViewController OuvrierWaitingBenne;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TransporteurWaitingBenneBucheron = new WaitingBenneViewController(ListTransporteurWaitingBenneBucheron);
        TransporteurWaitingBenneOuvrier = new WaitingBenneViewController(ListTransporteurWaitingBenneOuvrier);
        BucheronWaitingBenne = new WaitingBenneViewController(ListBucheronWaitingBenne);
        OuvrierWaitingBenne = new WaitingBenneViewController(ListOuvrierWaitingBenne);
    }





    public void buttonStartClick(){
        BackgroundApplication.getInstance().Start(this);
    }

    public void buttonAddBucheron(){
        BucheronView newView = new BucheronView();
        newView.setName(textFieldBucheronName.getText());
        bucheronsPane.getChildren().add(newView);

        BackgroundApplication.getInstance().createBucheron(textFieldBucheronName.getText(),this,newView);
    }

    public void buttonAddOuvrier(){
        OuvrierView newView = new OuvrierView();
        newView.setName(textFieldOuvrierName.getText());
        ouvriersPane.getChildren().add(newView);

        BackgroundApplication.getInstance().createOuvrier(textFieldBucheronName.getText(),this,newView);
    }

}
