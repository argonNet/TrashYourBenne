package com.il4;

import java.util.LinkedList;

/**
 * Created by Argon on 01.04.17.
 */
public class WaitingBenne {

    private LinkedList<Benne> waitingBenne;

    private synchronized boolean IsABenneWaiting(){
        return this.waitingBenne != null && !this.waitingBenne.isEmpty();
    }

    public synchronized void GiveBenne(Benne benne){
        this.waitingBenne.offer(benne);
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
        return firstBenne;
    }

    public WaitingBenne(){
        this.waitingBenne = new LinkedList<>();
    }


}
