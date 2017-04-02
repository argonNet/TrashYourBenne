package com.il4;

import com.il4.acteur.Bucheron;
import com.il4.acteur.Ouvrier;
import com.il4.acteur.Transporteur;
import com.il4.view.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    private MainViewController view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //Graphical part
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/il4/view/mainView.fxml"));
            Parent root = loader.load();
            view = loader.getController();

            primaryStage.setTitle("WOOD SIMULATOR");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();


            WaitingBenne bucheronWaitingBenne = new WaitingBenne(view.BucheronWaitingBenne);
            WaitingBenne ouvrierWaitingBenne = new WaitingBenne(view.OuvrierWaitingBenne);

            //Création des trois bennes de départs;
            bucheronWaitingBenne.GiveBenne(new Benne("Benne 1"));
            bucheronWaitingBenne.GiveBenne(new Benne("Benne 2"));
            bucheronWaitingBenne.GiveBenne(new Benne("Benne 3"));
            bucheronWaitingBenne.GiveBenne(new Benne("Benne 4"));
            bucheronWaitingBenne.GiveBenne(new Benne("Benne 5"));
            bucheronWaitingBenne.GiveBenne(new Benne("Benne 6"));


            WaitingBenne transporteurWaitingBenneFromBucheron = new WaitingBenne(view.TransporteurWaitingBenneBucheron);
            WaitingBenne transporteurWaitingBenneFromOuvrier = new WaitingBenne(view.TransporteurWaitingBenneOuvrier);

            Transporteur transporteur = new Transporteur("Robert",view,
                    transporteurWaitingBenneFromBucheron,bucheronWaitingBenne,
                    transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);

            Bucheron bucheron = new Bucheron("Florian",view,transporteurWaitingBenneFromBucheron,bucheronWaitingBenne);
            Ouvrier ouvrier = new Ouvrier("Manuel",view,transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);

            bucheron.start();
            transporteur.start();
            ouvrier.start();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

