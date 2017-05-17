package com.il4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Argon on 31.03.17.
 */
public class Benne {

    public static final int MAX_REMPLISSAGE = 10;

    public String name;

    private boolean someoneFillingTheBenne;
    private int remplissage;


    private final Lock lock = new ReentrantLock();

    public synchronized int getRemplissage(){
        return  remplissage;
    }

    public boolean startFillBenneIfFree(int value) throws InterruptedException{

        if (lock.tryLock(500, TimeUnit.MILLISECONDS)){
            this.remplissage += value;
            someoneFillingTheBenne = false;

            return true;
        }else{
            return false;
        }
    }

    public void stopFilleBenne(){
        lock.unlock();
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
