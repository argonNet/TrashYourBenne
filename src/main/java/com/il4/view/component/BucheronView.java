package com.il4.view.component;

import com.il4.acteur.IBucheronListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Argon on 02.04.17.
 */
public class BucheronView extends AnchorPane implements IBucheronListener{

    @FXML public ProgressBar progressBar;
    @FXML public Label labelName;
    @FXML public Label labelBenne;

    public BucheronView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("bucheronView.fxml"));
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

    public void setCurrentBenne(String name){
        labelBenne.setText(name);
    }

    public void resetCurrentBenne(){
        labelBenne.setText("---");
    }

    public void addBois(){
        progressBar.progressProperty().setValue(progressBar.progressProperty().getValue() + 0.1);
    }

    public void resetBois(){
        progressBar.progressProperty().setValue(0);
    }


    @Override
    public void onTakeBenne(String benneName) {
        resetBois();
        setCurrentBenne(benneName);
    }

    @Override
    public void onAddBoisToBenne() {
        addBois();
    }

    @Override
    public void onGiveBenne(String benneName) {
        resetBois();
        resetCurrentBenne();
    }
}
