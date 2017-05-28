package com.il4.acteur;

import com.il4.Benne;
import com.il4.IWorkingBenneListener;
import com.il4.WaitingBenne;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Argon on 31.03.17.
 */
public class Ouvrier extends Acteur {

    protected static LinkedList<Benne> currentEmptyingBennes = new LinkedList<>();

    public ArrayList<IOuvrierListener> listeners;
    public IWorkingBenneListener workingBenneListener;


    public WaitingBenne waitingBenne;
    public WaitingBenne transporteurWaitingBenne;

    private static int filledBenCountOuvrier = 0;

    private static Lock mylockOuvrier = new ReentrantLock();

//    private boolean checkIfBenneToTakeAndIncFilledBenCountOuvrier() {
//        mylockOuvrier.lock();
//        try {
//            if(filledBenCountOuvrier < filledBenCount){
//                filledBenCountOuvrier++;
//                return true;
//            }else {
//                return false;
//            }
//        } finally {
//            mylockOuvrier.unlock();
//        }
//    }

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

        try{
            while(filledBenCount < benToFill) {

                //TEST si a des bennes disponible sinon en prendre une
                if (currentEmptyingBennes.isEmpty()) {
                    Benne benne = this.waitingBenne.TakeBenne();
                    currentEmptyingBennes.add(benne);
                    Platform.runLater(() ->  workingBenneListener.addWorkingBenne(benne));
                }

                for (Benne currentBenne : currentEmptyingBennes) {
                    if (currentBenne.startBenneWorkIfFree(-1, this.getNameActeur())) {
                        sleep(speed);

                        if(currentBenne.isEmpty()){

                            currentEmptyingBennes.remove(currentBenne);
                            Platform.runLater(() ->  workingBenneListener.removeWorkingBenne(currentBenne));
                            incFilledBenCount();
                            transporteurWaitingBenne.GiveBenne(currentBenne);
                        }
                        currentBenne.stopBenneWork();
                        break;
                    }
                }
            }
        }catch(InterruptedException e){
            System.out.println("Error : " + e.getMessage());
        }

        System.out.println("Fin du travail pour " + this.name );
    }
}

