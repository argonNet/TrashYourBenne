package com.il4.acteur;

import com.il4.Benne;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Argon on 31.03.17.
 */
public class Acteur extends Thread{
    
    protected String name;

    public String getNameActeur(){
        return this.name;
    }

    protected int speed;
    public int getSpeed(){
        return this.speed;
    }
    public  void setSpeed(int value){this.speed = value;}

    private Benne benne;

    protected static int benToFill = 100;
    protected static int filledBenCount = 0;

    protected static Lock mylock = new ReentrantLock();

    protected void incFilledBenCount(){
        mylock.lock();
        try{
            filledBenCount++;
        }finally {
            mylock.unlock();
        }
    }

    public Benne getBenne(){
        return this.benne;
    }

    public void setBenne(Benne value){
        this.benne = value;
    }

    public Acteur(String name){
        this.name = name;
        this.setName(name);
    }

    public static void setBenToFill(int value){
        benToFill = value;
    }
}
