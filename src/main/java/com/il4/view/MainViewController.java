package com.il4.view;

import com.il4.BackgroundApplication;
import com.il4.IWorkingBenneListener;
import com.il4.acteur.Bucheron;
import com.il4.view.component.BenneView;
import com.il4.view.component.BucheronView;
import com.il4.view.component.OuvrierView;
import com.il4.view.component.TransporteurView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import com.il4.Benne;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Argon on 01.04.17.
 */
    public class MainViewController implements Initializable,IWorkingBenneListener  {

    @FXML public VBox bucheronsPane;
    @FXML public VBox fillingBennePane;
    @FXML public VBox transporteursPane;
    @FXML public VBox emptyingBennePane;
    @FXML public VBox ouvriersPane;

    @FXML public TextField textFieldBucheronName;
    @FXML public TextField textFieldTransporteurName;
    @FXML public TextField textFieldOuvrierName;

    @FXML public Spinner<Integer> spinnerTotalBenneToFill;

    @FXML public Button buttonStart;

    @FXML public ListView<String> ListTransporteurWaitingBenneBucheron;
    @FXML public ListView<String> ListTransporteurWaitingBenneOuvrier;
    @FXML public ListView<String> ListBucheronWaitingBenne;

    @FXML public ListView<String> ListOuvrierWaitingBenne;

    public WaitingBenneViewController transporteurWaitingBenneBucheron;
    public WaitingBenneViewController transporteurWaitingBenneOuvrier;
    public WaitingBenneViewController bucheronWaitingBenne;
    public WaitingBenneViewController ouvrierWaitingBenne;


    private void addBucheron(String name){
        BucheronView newView = new BucheronView();
        newView.setName(name);
        bucheronsPane.getChildren().add(newView);

        BackgroundApplication.getInstance().createBucheron(name,newView, this);
    }

    private void addTransporteur(String name){
        TransporteurView newView = new TransporteurView();
        newView.setName(name);
        transporteursPane.getChildren().add(newView);

        BackgroundApplication.getInstance().createTransporteur(name,newView);
    }

    private void addOuvrier(String name){
        OuvrierView newView = new OuvrierView();
        newView.setName(name);
        ouvriersPane.getChildren().add(newView);

        BackgroundApplication.getInstance().createOuvrier(name,newView);
    }

    private void addBenne(String name){
        BenneView newView = new BenneView();
        newView.setName(name);
        BackgroundApplication.getInstance().createBenne(name,newView);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        spinnerTotalBenneToFill.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100));

        bucheronWaitingBenne = new WaitingBenneViewController(ListBucheronWaitingBenne);
        ouvrierWaitingBenne = new WaitingBenneViewController(ListOuvrierWaitingBenne);

        transporteurWaitingBenneBucheron = new WaitingBenneViewController(ListTransporteurWaitingBenneBucheron);
        transporteurWaitingBenneOuvrier = new WaitingBenneViewController(ListTransporteurWaitingBenneOuvrier);

        BackgroundApplication.getInstance().createWaitingBenneQueues(
                bucheronWaitingBenne,
                ouvrierWaitingBenne,
                transporteurWaitingBenneBucheron,
                transporteurWaitingBenneOuvrier);

        addBenne("Benne 1");
        addBenne("Benne 2");
        addBenne("Benne 3");

        addBucheron("Florian");
        addTransporteur("Robert");
        addOuvrier("Manuel");

        spinnerTotalBenneToFill.getValueFactory().valueProperty().setValue(20);

    }


    public void buttonStartClick(){
        BackgroundApplication.getInstance().setBenneToFillCount(spinnerTotalBenneToFill.getValue());
        BackgroundApplication.getInstance().Start();
        buttonStart.setDisable(true);
    }

    @FXML  public void buttonAddBucheron(){
        addBucheron(textFieldBucheronName.getText());
    }

    @FXML  public void buttonAddTransporteur() { addTransporteur(textFieldTransporteurName.getText());}

    @FXML  public void buttonAddOuvrier(){
        addOuvrier(textFieldOuvrierName.getText());
    }

    @FXML  public void buttonAddBenneClick() {
        addBenne("Benne " + (BackgroundApplication.getInstance().getBennesCount() + 1));
    }

    @Override
    public void addWorkingBenne(Benne benne) {
        fillingBennePane.getChildren().add(benne.view);
    }
    @Override
    public void removeWorkingBenne(Benne benne) {
        fillingBennePane.getChildren().remove(benne.view);
    }
}
