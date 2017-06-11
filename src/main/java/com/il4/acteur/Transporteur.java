package com.il4.acteur;

import com.il4.tool.WaitingBenne;
import com.il4.acteur.listener.ITransporteurListener;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Argon on 31.03.17.
 */
public class Transporteur extends Acteur{

    private static int filledBenCountTransporteur = 0;

    private static Lock mylockTransporteur = new ReentrantLock();


    private void takeBenne(String benneName, String side){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                ((ITransporteurListener)listener).onTakeBenne(benneName,side);
            });
        });
    }

    private void goToOuvrier(){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                ((ITransporteurListener)listener).onGoOuvrier();
            });
        });
    }

    private void goToBucheron(){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                ((ITransporteurListener)listener).onGoBucheron();
            });
        });
    }

    private void giveBenne(String benneName, String side){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                ((ITransporteurListener)listener).onGiveBenne(benneName,side);
            });
        });
    }

    public WaitingBenne waitingBenneFromBucheron;
    public WaitingBenne bucheronWaitingBenne;
    public WaitingBenne waitingBenneFromOuvrier;
    public WaitingBenne ouvrierWaitingBenne;


    private final static int TRAJET_DISTANCE = 50;

    public Transporteur(String name,
                        WaitingBenne waitingBenneFromBucheron,
                        WaitingBenne bucheronWaitingBenne,
                        WaitingBenne waitingBenneFromOuvrier,
                        WaitingBenne ouvrierWaitingBenne){
        super(name);

        this.listeners = new ArrayList<>();
        this.waitingBenneFromBucheron = waitingBenneFromBucheron;
        this.bucheronWaitingBenne = bucheronWaitingBenne;
        this.waitingBenneFromOuvrier = waitingBenneFromOuvrier;
        this.ouvrierWaitingBenne = ouvrierWaitingBenne;
    }

    @Override
    public void performWork(){

        while(!isTotalBenneCountFilled() ||
                !(
                    isTotalBenneCountFilled() &&
                    !this.waitingBenneFromBucheron.IsABenneWaiting() &&
                    !this.ouvrierWaitingBenne.IsABenneWaiting() &&
                    !this.waitingBenneFromOuvrier.IsABenneWaiting() &&
                    !isABucheronWorking()
                )
        ) {

            this.setBenne(this.waitingBenneFromBucheron.TakeBenne());

            takeBenne(this.getBenne().getName(),  "Bucheron");

            for(int i = 0; i <= TRAJET_DISTANCE; i++){
                try {
                    sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                goToOuvrier();
            }


            this.ouvrierWaitingBenne.GiveBenne(this.getBenne());
            giveBenne(this.getBenne().getName(),  "Ouvrier");

            this.setBenne(this.waitingBenneFromOuvrier.TakeBenne());

            takeBenne(this.getBenne().getName(),  "Ouvrier");



            for(int i = 0; i <= TRAJET_DISTANCE; i++){
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                goToBucheron();

            }


            this.bucheronWaitingBenne.GiveBenne(this.getBenne());
            giveBenne(this.getBenne().getName(), "Bucheron");

            this.setBenne(null);

        }

    }

}
