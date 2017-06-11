package com.il4.acteur;

import com.il4.acteur.listener.IActeurListener;
import com.il4.tool.Benne;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Classe de base commune à tous les acteurs de l'application (Thread)
 */
public abstract class Acteur extends Thread{

    //region *** Type Declaraion ***
    public enum ThreadStatus{
        Await,
        Running,
        End
    }
    //endregion

    //region *** Static Declaration ***

    private static boolean workIsDone = false;

    protected static int benToFill = 100;
    protected static Lock mylock = new ReentrantLock();
    protected static int filledBenCount = 0;
    private static int workingBucheronCount = 0;
    private static int workingTransporteurCount = 0;
    private static int workingOuvrierCount = 0;

    protected static  boolean isTotalBenneCountFilled(){
        return workIsDone;
    }

    protected static void incFilledBenneCount(){
        mylock.lock();
        try{

            filledBenCount++;

            if(filledBenCount == benToFill){
                workIsDone = true;
            }

        }finally {
            mylock.unlock();
        }
    }

    public static void setBenToFill(int value){
        benToFill = value;
    }

    public static boolean isABucheronWorking(){return workingBucheronCount > 0;}
    public static boolean isATransporteurWorking(){return workingTransporteurCount > 0;}
    public static boolean isAOuvrierWorking(){return workingOuvrierCount > 0;}

    public static void threadAwait(){
        if(Thread.currentThread() instanceof Acteur) {
            ((Acteur) Thread.currentThread()).setStatus(Acteur.ThreadStatus.Await);
        }
    }

    public static void threadRun(){
        if(Thread.currentThread() instanceof Acteur) {
            ((Acteur) Thread.currentThread()).setStatus(ThreadStatus.Running);
        }
    }

    //endregion

    //region *** Standard Declaration ***

    private Benne benne;

    protected String name;
    protected int speed;
    protected ArrayList<IActeurListener> listeners;

    public Acteur(String name){
        this.name = name;
        this.setName(name);

        this.setSpeed(10 + (int)(Math.random() * ((500 - 10) + 1)));
        this.listeners = new ArrayList<>();
    }

    public String getNameActeur() {
        return this.name;
    }

    public int getSpeed(){
        return this.speed;
    }
    public  void setSpeed(int value){
        if(value >= 0) {
            this.speed = value;
        }
    }

    public Benne getBenne(){
        return this.benne;
    }

    public void setBenne(Benne value){
        this.benne = value;
    }


    public void addListener(IActeurListener listener){
        this.listeners.add(listener);
    }

    public void setStatus(ThreadStatus status){
        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                (listener).statusChange(status);
            });
        });
    }

    @Override
    public void run(){
        if(this instanceof Bucheron) workingBucheronCount++;
        if(this instanceof Transporteur) workingTransporteurCount++;
        if(this instanceof Ouvrier) workingOuvrierCount++;
        try{
            performWork();

            System.out.println("Fin du travail pour " + this.getNameActeur());

            setStatus(ThreadStatus.End);
        }finally {
            if(this instanceof Bucheron) workingBucheronCount--;
            if(this instanceof Transporteur) workingTransporteurCount--;
            if(this instanceof Ouvrier) workingOuvrierCount--;
        }
    }

    protected abstract void performWork();

    //endregion

}
