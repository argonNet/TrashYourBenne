package com.il4.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Argon on 01.04.17.
 */
public class MainViewController implements Initializable{


    @FXML public ProgressBar ProgressBarOuvrier;
    @FXML public ProgressBar ProgressBarBucheron;
    @FXML public Slider SliderTransporteur;


    @FXML public Label labelBucheron;
    @FXML public Label labelOuvrier;
    @FXML public Label labelTransporter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }



    public void BucheronSetCurrentBenne(String name){
        labelBucheron.setText(name);
    }

    public void BucheronResetCurrentBenne(){
        labelBucheron.setText("---");
    }

    public void BucheronAddBois(){
        ProgressBarBucheron.progressProperty().setValue(ProgressBarBucheron.progressProperty().getValue() + 0.1);
    }

    public void BucheronReset(){
        ProgressBarBucheron.progressProperty().setValue(0);
    }



    public void TransporteurSetCurrentBenne(String name){
        labelTransporter.setText(name);
    }

    public void TransporteurResetCurrentBenne(){
        labelTransporter.setText("---");
    }

    public void TransporteurProgressDirectionOuvrier(){
        SliderTransporteur.increment();
    }

    public void TransporteurProgressDirectionBucheron(){
        SliderTransporteur.decrement();
    }



    public void OuvrierSetCurrentBenne(String name){
        labelOuvrier.setText(name);
    }

    public void OuvrierResetCurrentBenne(){
        labelOuvrier.setText("---");
    }

    public void OuvrierRemoveBois(){
        ProgressBarOuvrier.progressProperty().setValue(ProgressBarOuvrier.progressProperty().getValue() - 0.1);
    }

    public void OuvrierReset(){
        ProgressBarOuvrier.progressProperty().setValue(1);
    }

}
