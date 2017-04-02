package com.il4.view;

import com.il4.BackgroundApplication;
import com.il4.view.component.BucheronView;
import com.il4.view.component.OuvrierView;
import com.il4.view.component.TransporteurView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Argon on 01.04.17.
 */
public class MainViewController implements Initializable {

    @FXML public Button buttonStart;

    @FXML public VBox bucheronsPane;
    @FXML public VBox transporteursPane;

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

    public WaitingBenneViewController transporteurWaitingBenneBucheron;
    public WaitingBenneViewController transporteurWaitingBenneOuvrier;
    public WaitingBenneViewController bucheronWaitingBenne;
    public WaitingBenneViewController ouvrierWaitingBenne;


    private void addBucheron(String name){
        BucheronView newView = new BucheronView();
        newView.setName(name);
        bucheronsPane.getChildren().add(newView);

        BackgroundApplication.getInstance().createBucheron(name,newView);
    }

    private void addTransporteur(String name){
        TransporteurView newView = new TransporteurView();
        newView.setName(name);
        transporteursPane.getChildren().add(newView);

        BackgroundApplication.getInstance().createTransporteur(name,newView);
    }

    private void addOuvrier(String name){
        OuvrierView newView = new OuvrierView();
        newView.setName(textFieldOuvrierName.getText());
        ouvriersPane.getChildren().add(newView);

        BackgroundApplication.getInstance().createOuvrier(name,newView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bucheronWaitingBenne = new WaitingBenneViewController(ListBucheronWaitingBenne);
        ouvrierWaitingBenne = new WaitingBenneViewController(ListOuvrierWaitingBenne);

        transporteurWaitingBenneBucheron = new WaitingBenneViewController(ListTransporteurWaitingBenneBucheron);
        transporteurWaitingBenneOuvrier = new WaitingBenneViewController(ListTransporteurWaitingBenneOuvrier);

        BackgroundApplication.getInstance().createWaitingBenneQueues(
                bucheronWaitingBenne,
                ouvrierWaitingBenne,
                transporteurWaitingBenneBucheron,
                transporteurWaitingBenneOuvrier);

        BackgroundApplication.getInstance().createBenne("Benne 1");
        BackgroundApplication.getInstance().createBenne("Benne 2");
        BackgroundApplication.getInstance().createBenne("Benne 3");
        BackgroundApplication.getInstance().createBenne("Benne 4");


        addBucheron("Florian");
        addTransporteur("Robert");
        addOuvrier("Manuel");

    }


    public void buttonStartClick(){
        BackgroundApplication.getInstance().Start();
    }

    public void buttonAddBucheron(){
        addBucheron(textFieldBucheronName.getText());
    }

    public void buttonAddOuvrier(){
        addOuvrier(textFieldBucheronName.getText());
    }



}
