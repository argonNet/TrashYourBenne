package com.il4;

import com.il4.view.WaitingBenneViewController;
import javafx.application.Platform;

import java.util.LinkedList;

/**
 * Created by Argon on 01.04.17.
 */
public class WaitingBenne {

    private WaitingBenneViewController view;
    private LinkedList<Benne> waitingBenne;

    private synchronized boolean IsABenneWaiting(){
        return this.waitingBenne != null && !this.waitingBenne.isEmpty();
    }

    public synchronized void GiveBenne(Benne benne){
        this.waitingBenne.offer(benne);
        Platform.runLater(() -> this.view.AddBenne(benne));
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

        Platform.runLater(() -> this.view.RemoveBenne(firstBenne));

        return firstBenne;
    }

    public WaitingBenne(WaitingBenneViewController view){
        this.waitingBenne = new LinkedList<>();
        this.view = view;
    }

}
