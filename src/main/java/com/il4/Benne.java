package com.il4;

/**
 * Created by Argon on 31.03.17.
 */
public class Benne {

    public static final int MAX_REMPLISSAGE = 10;

    public String name;

    private boolean someoneFillingTheBenne;
    private int remplissage;


    public synchronized int getRemplissage(){
        return  remplissage;
    }

    public synchronized void fillBenne(int value){
        this.remplissage += value;
        someoneFillingTheBenne = false;
    }

    public synchronized boolean isSomeoneFillingTheBenne(){
        boolean tmpFlag = someoneFillingTheBenne;
        if(!someoneFillingTheBenne){
            someoneFillingTheBenne = false;
        }
        return tmpFlag;
    }

    public Benne(String name){
        this.name = name;
    }

}
