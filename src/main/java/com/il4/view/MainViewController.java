package com.il4.view;

import com.il4.BackgroundApplication;
import com.il4.view.component.BucheronView;
import com.il4.view.component.OuvrierView;
import com.il4.view.component.TransporteurView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Argon on 01.04.17.
 */
public class MainViewController implements Initializable {

    @FXML public VBox bucheronsPane;
    @FXML public VBox transporteursPane;

    @FXML public VBox ouvriersPane;

    @FXML public TextField textFieldBucheronName;
    @FXML public TextField textFieldTransporteurName;
    @FXML public TextField textFieldOuvrierName;

    @FXML public Spinner<Integer> spinnerTotalBenneToFill;

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
        newView.setName(name);
        ouvriersPane.getChildren().add(newView);

        BackgroundApplication.getInstance().createOuvrier(name,newView);
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

        BackgroundApplication.getInstance().createBenne("Benne 1");
        BackgroundApplication.getInstance().createBenne("Benne 2");
        BackgroundApplication.getInstance().createBenne("Benne 3");


        addBucheron("Florian");
        addTransporteur("Robert");
        addOuvrier("Manuel");

    }


    public void buttonStartClick(){
        BackgroundApplication.getInstance().setBenneToFillCount(spinnerTotalBenneToFill.getValue());
        BackgroundApplication.getInstance().Start();
    }

    @FXML  public void buttonAddBucheron(){
        addBucheron(textFieldBucheronName.getText());
    }

    @FXML  public void buttonAddTransporteur() { addTransporteur(textFieldTransporteurName.getText());}

    @FXML  public void buttonAddOuvrier(){
        addOuvrier(textFieldBucheronName.getText());
    }

    @FXML  public void buttonAddBenneClick() {
        BackgroundApplication.getInstance().createBenne(
                "Benne " + (BackgroundApplication.getInstance().getBennesCount() + 1));
    }

}
