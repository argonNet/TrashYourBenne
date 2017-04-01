package com.il4.acteur;

import com.il4.WaitingBenne;
import com.il4.view.MainViewController;
import javafx.application.Platform;

/**
 * Created by Argon on 31.03.17.
 */
public class Transporteur extends Acteur{

    public WaitingBenne waitingBenneFromBucheron;
    public WaitingBenne bucheronWaitingBenne;
    public WaitingBenne waitingBenneFromOuvrier;
    public WaitingBenne ouvrierWaitingBenne;


    private final static int TRAJET_DISTANCE = 50;

    public Transporteur(String name, MainViewController mainViewController,
                        WaitingBenne waitingBenneFromBucheron,
                        WaitingBenne bucheronWaitingBenne,
                        WaitingBenne waitingBenneFromOuvrier,
                        WaitingBenne ouvrierWaitingBenne){
        super(name,mainViewController);

        this.waitingBenneFromBucheron = waitingBenneFromBucheron;
        this.bucheronWaitingBenne = bucheronWaitingBenne;
        this.waitingBenneFromOuvrier = waitingBenneFromOuvrier;
        this.ouvrierWaitingBenne = ouvrierWaitingBenne;
    }

    @Override
    public void run(){

        while(filledBenCount < BEN_TO_FILL) {

            Platform.runLater(() -> this.view.TransporteurResetCurrentBenne());
            this.setBenne(this.waitingBenneFromBucheron.TakeBenne());

            Platform.runLater(() -> this.view.TransporteurSetCurrentBenne(this.getBenne().name));
            System.out.println(this.name + "-> Prise en charge de la benne :  " + this.getBenne().name + " à destination de l'ouvrier");


            for(int i = 0; i <= TRAJET_DISTANCE; i++){
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> this.view.TransporteurProgressDirectionOuvrier());
                System.out.println(this.name + "-> Acheminement de la benne : " + this.getBenne().name + " - etat : " +
                        i + " / " + TRAJET_DISTANCE);
            }



            this.ouvrierWaitingBenne.GiveBenne(this.getBenne());
            Platform.runLater(() -> this.view.TransporteurResetCurrentBenne());
            this.setBenne(this.waitingBenneFromOuvrier.TakeBenne());

            Platform.runLater(() -> this.view.TransporteurSetCurrentBenne(this.getBenne().name));
            System.out.println(this.name + "-> Prise en charge de la benne :  " + this.getBenne().name + " à destination du bucheron");


            for(int i = 0; i <= TRAJET_DISTANCE; i++){
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> this.view.TransporteurProgressDirectionBucheron());
                System.out.println(this.name + "-> Acheminement de la benne : " + this.getBenne().name + " - etat : " +
                        i + " / " + TRAJET_DISTANCE);
            }


            this.bucheronWaitingBenne.GiveBenne(this.getBenne());
            Platform.runLater(() -> this.view.TransporteurResetCurrentBenne());

            this.setBenne(null);

        }
    }

}
