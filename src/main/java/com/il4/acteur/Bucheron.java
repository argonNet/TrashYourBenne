package com.il4.acteur;

import com.il4.Benne;
import com.il4.IWorkingBenneListener;
import com.il4.WaitingBenne;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Argon on 31.03.17.
 */
public class Bucheron extends Acteur{

    protected static LinkedList<Benne> currentFillingBennes = new LinkedList<>();

    public ArrayList<IBucheronListener> listeners;
    public IWorkingBenneListener workingBenneListener;

    public WaitingBenne waitingBenne;
    public WaitingBenne transporteurWaitingBenne;


    private void addBoisToBenne(){
        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onAddBoisToBenne();
            });
        });
    }

    private void startWorkingOnBenne(String benneName){
        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onStartWorkingOnBenne(benneName);
            });
        });
    }

    private void stopWorkingOnBenne(){
        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onStopWorkingOnBenne();
            });
        });
    }



    public Bucheron(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name);

        listeners = new ArrayList<>();

        this.transporteurWaitingBenne = transporteurWaitingBenne;
        this.waitingBenne = waitingBenne;
        this.speed = 50;
    }

    @Override
    public void run(){

        try{
            while(filledBenCount < benToFill) {

                //TEST si a des bennes disponible sinon en prendre une
                if (currentFillingBennes.isEmpty()) {
                    Benne benne = this.waitingBenne.TakeBenne();
                    if(benne != null){
                        currentFillingBennes.add(benne);
                        Platform.runLater(() ->  workingBenneListener.addWorkingBenne(benne));
                    }

                }

                for (Benne currentBenne : currentFillingBennes) {
                    if (currentBenne.startBenneWorkIfFree(1, this.getNameActeur())) {
                        startWorkingOnBenne(currentBenne.name);

                        //Transporte une bille de jusqu'à la benne
                        for(int i = 0; i < 10; i++ ){
                            sleep(speed);
                            addBoisToBenne();
                        }

                        if(currentBenne.isFull()){
                            currentFillingBennes.remove(currentBenne);
                            Platform.runLater(() ->  workingBenneListener.removeWorkingBenne(currentBenne));
                            incFilledBenCount();
                            transporteurWaitingBenne.GiveBenne(currentBenne);
                        }
                        currentBenne.stopBenneWork();
                        stopWorkingOnBenne();
                        break;
                    }
                }

                sleep(500); //Petite pause du bucheron avant de reprendre une bille de bois
            }
        }catch(InterruptedException e){
            System.out.println("Error : " + e.getMessage());
        }

        System.out.println("Fin du travail pour " + this.name );
    }

}
