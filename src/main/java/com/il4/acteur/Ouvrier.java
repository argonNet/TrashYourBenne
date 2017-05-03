package com.il4.acteur;

import com.il4.WaitingBenne;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Argon on 31.03.17.
 */
public class Ouvrier extends Acteur {

    public ArrayList<IOuvrierListener> listeners;

    public WaitingBenne waitingBenne;
    public WaitingBenne transporteurWaitingBenne;

    private static int filledBenCountOuvrier = 0;

    private static Lock mylockOuvrier = new ReentrantLock();

    private boolean checkIfBenneToTakeAndIncFilledBenCountOuvrier() {
        mylockOuvrier.lock();
        try {
            if(filledBenCountOuvrier < filledBenCount){
                filledBenCountOuvrier++;
                return true;
            }else {
                return false;
            }
        } finally {
            mylockOuvrier.unlock();
        }
    }

    private void removeBoisToBenne(){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onRemoveBoisToBenne();
            });
        });
    }


    private void giveBenne(String benneName){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onGiveBenne(benneName);
            });
        });
    }

    private void takeBenne(String benneName){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onTakeBenne(benneName);
            });
        });
    }


    public Ouvrier(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne){
        super(name);

        this.listeners = new ArrayList<>();
        this.speed = 700;
        this.waitingBenne = waitingBenne;
        this.transporteurWaitingBenne = transporteurWaitingBenne;
    }

    @Override
    public void run(){

        while(checkIfBenneToTakeAndIncFilledBenCountOuvrier()) {

            this.setBenne(this.waitingBenne.TakeBenne());
            takeBenne(this.getBenne().name);
            System.out.println(this.name + "-> Récupération de la benne :  " + this.getBenne().name);


            while (this.getBenne().remplissage > 0) {
                this.getBenne().remplissage--;
                try {
                    sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.name + "-> Vidage de la benne : " + this.getBenne().name + " - etat : " +
                        this.getBenne().remplissage + " / " + 0);
                removeBoisToBenne();
            }

            this.transporteurWaitingBenne.GiveBenne(this.getBenne());
            giveBenne(this.getBenne().name);
            this.setBenne(null);

        }
        System.out.println("Fin du travail pour " + this.name);
    }
}

