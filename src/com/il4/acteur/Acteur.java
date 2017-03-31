package com.il4.acteur;

import com.il4.Benne;

import java.util.LinkedList;

/**
 * Created by Argon on 31.03.17.
 */
public class Acteur extends Thread{

    protected String name;

    private Benne benne;

    public Benne getBenne(){
        return this.benne;
    }

    public synchronized void setBenne(Benne value){
        this.benne = value;
    }

    private LinkedList<Benne> waitingBenne;

    private synchronized boolean IsABenneWaiting(){
        return this.waitingBenne != null && !this.waitingBenne.isEmpty();
    }

    public synchronized void GiveBenne(Benne benne){
        this.waitingBenne.offer(benne);
    }

    public synchronized Benne TakeBenne(){
        Benne firstBenne = this.waitingBenne.getFirst();
        this.waitingBenne.removeFirst();
        return firstBenne;
    }


    public Acteur(String name){
        this.name = name;

        this.waitingBenne = new LinkedList<Benne>();
    }

}
