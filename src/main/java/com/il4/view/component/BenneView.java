package com.il4.view.component;

import com.il4.acteur.IBenneListener;
import com.il4.acteur.IBucheronListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Argon on 07.04.17.
 */
public class BenneView extends AnchorPane implements IBenneListener {

    @FXML public ProgressBar progressBar;
    @FXML public Label labelName;

    public BenneView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("benneView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setName(String name){
        System.out.printf(name);
        labelName.setText(name);
    }

    public void addBois(){
        progressBar.progressProperty().setValue(progressBar.progressProperty().getValue() + 0.1);
    }

    public void resetBois(){
        progressBar.progressProperty().setValue(0);
    }

    @Override
    public void onFillBenne(double value) {
        progressBar.progressProperty().setValue(progressBar.progressProperty().getValue() + value);
    }
}