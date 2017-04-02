package com.il4;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Argon on 01.04.17.
 */
public class WaitingBenne {

    public ArrayList<IWaitingBenneListener> listeners;

    private LinkedList<Benne> waitingBenne;


    private void onBenneGivenNotify(String benneName){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onBenneGiven(benneName);
            });
        });
    }

    private void onBenneTakeNotify(String benneName){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onBenneTaken(benneName);
            });
        });
    }


    private synchronized boolean IsABenneWaiting(){
        return this.waitingBenne != null && !this.waitingBenne.isEmpty();
    }

    public synchronized void GiveBenne(Benne benne){
        this.waitingBenne.offer(benne);
        onBenneGivenNotify(benne.name);

        notifyAll();
    }

    public synchronized Benne TakeBenne(){

        while(!this.IsABenneWaiting()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Benne firstBenne = this.waitingBenne.getFirst();
        this.waitingBenne.removeFirst();

        onBenneTakeNotify(firstBenne.name);

        return firstBenne;
    }

    public WaitingBenne(){
        this.waitingBenne = new LinkedList<>();
        this.listeners = new ArrayList<>();
    }

}
