package com.il4.view.component;

import com.il4.BackgroundApplication;
import com.il4.acteur.IOuvrierListener;
import com.il4.acteur.Ouvrier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Argon on 02.04.17.
 */
public class OuvrierView extends AnchorPane implements IOuvrierListener {

    @FXML public ProgressBar progressBar;
    @FXML public Label labelName;
    @FXML public Label labelBenne;
    private int idOuvrier;

    public OuvrierView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("ouvrierView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setIdOuvrier (int index)
    {
        this.idOuvrier = index;
    }

    public void setName(String name){
        labelName.setText(name);
    }

    public void setCurrentBenne(String name){
        labelBenne.setText(name);
    }

    public void resetCurrentBenne(){
        labelBenne.setText("---");
    }

    public void removeBois(){
        progressBar.progressProperty().setValue(progressBar.progressProperty().getValue() - 0.1);
    }

    public void resetBois(){
        progressBar.progressProperty().setValue(1);
    }


    @Override
    public void onTakeBenne(String benneName) {
        resetBois();
        setCurrentBenne(benneName);
    }

    @Override
    public void onRemoveBoisToBenne() {
        removeBois();
    }

    @Override
    public void onGiveBenne(String benneName) {
        resetBois();
        resetCurrentBenne();
    }
    @FXML  public void buttonWorkFasterClick (){
        Ouvrier ouvrier = BackgroundApplication.getInstance().getOuvrier(idOuvrier);
        if (ouvrier.getSpeed() < 0) ouvrier.setSpeed(0);
            ouvrier.setSpeed(ouvrier.getSpeed() - 100);

    }

    @FXML  public void buttonWorkLessClick (){
        Ouvrier ouvrier = BackgroundApplication.getInstance().getOuvrier(idOuvrier);
        if (ouvrier.getSpeed() < 0) ouvrier.setSpeed(0);
        ouvrier.setSpeed(ouvrier.getSpeed() + 100);
    }
}
