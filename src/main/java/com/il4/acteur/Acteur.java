package com.il4.acteur;

import com.il4.acteur.listener.IActeurListener;
import com.il4.tool.Benne;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Argon on 31.03.17.
 */
public abstract class Acteur extends Thread{


    protected String name;

    public String getNameActeur(){
        return this.name;
    }

    protected int speed;
    public int getSpeed(){
        return this.speed;
    }
    public  void setSpeed(int value){
        if(value >= 0) {
            this.speed = value;
        }
    }

    private Benne benne;

    protected static int benToFill = 100;


    protected static Lock mylock = new ReentrantLock();

    protected static int filledBenCount = 0;

    private static boolean workIsDone = false;
    protected static  boolean getWorkIsDone(){
        return workIsDone;
    }

    protected void incFilledBenCount(){
        mylock.lock();
        try{

            if(filledBenCount < benToFill){
                filledBenCount++;
            }else{
                workIsDone = true;
            }

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

        this.setSpeed(10 + (int)(Math.random() * ((500 - 10) + 1)));
        this.listeners = new ArrayList<>();
    }

    public static void setBenToFill(int value){
        benToFill = value;
    }


    public enum ThreadStatus{
        Await,
        AwaitAlone,
        AwaitInQueue,
        Running,
        End
    }

    private ThreadStatus status;

    protected ArrayList<IActeurListener> listeners;

    public void addListener(IActeurListener listener){
        this.listeners.add(listener);
    }

    public void setStatus(ThreadStatus status){
        this.status = status;

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                (listener).statusChange(status);
            });
        });
    }

    protected abstract void performWork();

    @Override
    public void run(){
        performWork();

        System.out.println("Fin du travail pour " + this.getNameActeur());
        setStatus(ThreadStatus.End);
    }
}
