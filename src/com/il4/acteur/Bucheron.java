package com.il4.acteur;

import com.il4.WaitingBenne;
import com.il4.view.MainViewController;
import javafx.application.Platform;

import java.util.ArrayList;

/**
 * Created by Argon on 31.03.17.
 */
public class Bucheron extends Acteur{

    public ArrayList<IBucheronListener> listeners;

    public WaitingBenne waitingBenne;
    public WaitingBenne transporteurWaitingBenne;


    private void addBoisToBenne(){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onAddBoisToBenne();
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


    public Bucheron(String name, MainViewController mainViewController, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name,mainViewController);

        listeners = new ArrayList<>();

        this.transporteurWaitingBenne = transporteurWaitingBenne;
        this.waitingBenne = waitingBenne;
    }

    @Override
    public void run(){

        while(filledBenCount < BEN_TO_FILL) {

            this.setBenne(this.waitingBenne.TakeBenne());

            takeBenne(this.getBenne().name);
            System.out.println(this.name + "-> Récupération de la benne :  " + this.getBenne().name);


            while (this.getBenne().remplissage < this.getBenne().MAX_REMPLISSAGE) {
                this.getBenne().remplissage++;
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.name + "-> Remplissage de la benne : " + this.getBenne().name + " - etat : " +
                        this.getBenne().remplissage + " / " + this.getBenne().MAX_REMPLISSAGE);

                addBoisToBenne();
            }

            this.transporteurWaitingBenne.GiveBenne(this.getBenne());
            giveBenne(this.getBenne().name);

            incFilledBenCount();
            this.setBenne(null);
        }
    }

}
