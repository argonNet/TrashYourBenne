package com.il4;

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

    public ArrayList<IWaitingBenneListener> listeners;

    private LinkedList<Benne> waitingBenne;

    private Lock mylock = new ReentrantLock();
    private Condition myCond = mylock.newCondition();

    private  void onBenneGivenNotify(String benneName){

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


    public boolean IsABenneWaiting(){
        return this.waitingBenne != null && !this.waitingBenne.isEmpty();
    }

    public void GiveBenne(Benne benne){
        mylock.lock();
        try{
            this.waitingBenne.offer(benne);
            onBenneGivenNotify(benne.name);

            myCond.signalAll();
        }finally {
            mylock.unlock();
        }

    }

    public Benne TakeBenne(){
        mylock.lock();

        try{

            while(!this.IsABenneWaiting()){
                try {

                    myCond.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Benne firstBenne = this.waitingBenne.getFirst();
            this.waitingBenne.removeFirst();

            onBenneTakeNotify(firstBenne.name);


            return firstBenne;
        }finally {
            mylock.unlock();
        }
    }

    public WaitingBenne(){
        this.waitingBenne = new LinkedList<>();
        this.listeners = new ArrayList<>();
    }

}
