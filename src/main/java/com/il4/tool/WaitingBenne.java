package com.il4.tool;

import com.il4.acteur.Acteur;
import com.il4.tool.listener.IWaitingBenneListener;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Argon on 01.04.17.
 */
public class WaitingBenne {

    public enum WaitingMode{
        oneWaiting,
        severalWaiting
    }

    public ArrayList<IWaitingBenneListener> listeners;

    private LinkedList<Benne> waitingBenne;

    private Lock takeOrGiveBenneLock = new ReentrantLock();
    private Condition waitIfNoBenneAvailable = takeOrGiveBenneLock.newCondition();

    private WaitingMode waitingMode = WaitingMode.oneWaiting;

    private void benneListChange(){
        ArrayList<String> benneNames = new ArrayList<>();
        waitingBenne.forEach((x) -> benneNames.add(x.getName()));

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onWaitingBennesChange(benneNames);
            });
        });
    }

    public boolean IsABenneWaiting() {
        return this.waitingBenne != null && !this.waitingBenne.isEmpty();
    }

    public void GiveBenne(Benne benne){

        Acteur.threadAwait();
        takeOrGiveBenneLock.lock();
        Acteur.threadRun();

        try{
            this.waitingBenne.offer(benne);

            benneListChange();

            waitIfNoBenneAvailable.signalAll();
        }finally {
           takeOrGiveBenneLock.unlock();
        }
    }

    public Benne TakeBenne(){

        Acteur.threadAwait();
        takeOrGiveBenneLock.lock();
        Acteur.threadRun();

        try{

            if(waitingMode == WaitingMode.severalWaiting){

                //Plusieurs thread attende simulatnément (file d'attente)
                while(!this.IsABenneWaiting()){
                    try {

                        Acteur.threadAwait();
                        waitIfNoBenneAvailable.await();
                        Acteur.threadRun();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(this.IsABenneWaiting()){
                Benne firstBenne = this.waitingBenne.getFirst();
                this.waitingBenne.removeFirst();

                benneListChange();

                return firstBenne;
            }else{
                return null;
            }

        }finally {
            takeOrGiveBenneLock.unlock();
        }
    }


    public WaitingBenne(WaitingMode waitingMode){
        this.waitingMode = waitingMode;
        this.waitingBenne = new LinkedList<>();
        this.listeners = new ArrayList<>();
    }

}
