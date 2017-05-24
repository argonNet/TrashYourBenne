package com.il4;

import com.il4.acteur.IBenneListener;
import com.il4.acteur.IBucheronListener;
import com.il4.view.component.BenneView;
import javafx.application.Platform;

import java.util.ArrayList;
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

    public BenneView view;

    public ArrayList<IBenneListener> listeners;

    public void fillBenne(double value){
        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onFillBenne(value);
            });
        });
    }

    public synchronized boolean isFull(){
        return  remplissage == MAX_REMPLISSAGE;
    }

    public boolean startFillBenneIfFree(int value) throws InterruptedException{

        if (lock.tryLock(500, TimeUnit.MILLISECONDS)){
            this.remplissage += value;
            fillBenne((double)value / 10);
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

    public Benne(String name, BenneView view){
        this.name = name;
        this.listeners = new ArrayList<>();
        this.view  = view;
    }

}
