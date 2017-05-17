package com.il4.acteur;

import com.il4.Benne;
import com.il4.WaitingBenne;
import javafx.application.Platform;

import java.util.ArrayList;

/**
 * Created by Argon on 31.03.17.
 */
public class Bucheron extends Acteur{

    protected static ArrayList<Benne> currentFillingBennes = new ArrayList<>();

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

    private Benne getABenneFilling(){
        if(currentFillingBennes.size() > 0){


        } else {

        }
        return ;
    }

    private Benne getABenne(){

    }

    public Bucheron(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name);

        listeners = new ArrayList<>();

        this.transporteurWaitingBenne = transporteurWaitingBenne;
        this.waitingBenne = waitingBenne;
        this.speed = 500;
    }

    @Override
    public void run() throws InterruptedException{


        while(filledBenCount < benToFill) {
            incFilledBenCount();

            //TEST si a des bennes disponibles sinon en prendre une


            for (Benne currentBenne : currentFillingBennes) {
                if(currentBenne.startFillBenneIfFree(1)){
                    sleep(speed);
                    currentBenne.stopFilleBenne();
                    break;
                }
            }



            this.setBenne(this.waitingBenne.TakeBenne());

            takeBenne(this.getBenne().name);
            System.out.println(this.name + "-> Récupération de la benne :  " + this.getBenne().name);


            while (this.getBenne().getRemplissage() < this.getBenne().MAX_REMPLISSAGE) {
                this.getBenne().remplissage++;
                try {
                    sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.name + "-> Remplissage de la benne : " + this.getBenne().name + " - etat : " +
                        this.getBenne().remplissage + " / " + this.getBenne().MAX_REMPLISSAGE);

                addBoisToBenne();
            }

            this.transporteurWaitingBenne.GiveBenne(this.getBenne());
            giveBenne(this.getBenne().name);

            this.setBenne(null);
        }

        System.out.println("Fin du travail pour " + this.name );
    }

}
