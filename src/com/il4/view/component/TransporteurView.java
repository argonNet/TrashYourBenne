package com.il4.view.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Argon on 02.04.17.
 */
public class TransporteurView extends AnchorPane {

    @FXML private Slider slider;
    @FXML public Label labelName;
    @FXML public Label labelBenne;

    public TransporteurView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/il4/view/component/transporteurView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setCurrentBenne(String name){
        labelBenne.setText(name);
    }

    public void resetCurrentBenne(){
        labelBenne.setText("---");
    }

    public void ProgressDirectionOuvrier(){
        slider.increment();
    }

    public void ProgressDirectionBucheron(){
        slider.decrement();
    }



}
