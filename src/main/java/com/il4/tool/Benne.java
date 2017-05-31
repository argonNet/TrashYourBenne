package com.il4.tool;

import com.il4.acteur.listener.IBenneListener;
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

    private int remplissage;
    private final Lock lock = new ReentrantLock();

    public BenneView view;

    public ArrayList<IBenneListener> listeners;

    public void fillBenne(double value, String workerName){
        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onFillBenne(value, workerName);
            });
        });
    }

    public synchronized boolean isFull(){
        return  remplissage == MAX_REMPLISSAGE;
    }

    public synchronized boolean isEmpty(){
        return  remplissage <= 0;
    }

    public boolean startBenneWorkIfFree(int value, String workerName) throws InterruptedException{

        if (lock.tryLock(100, TimeUnit.MILLISECONDS)){
            this.remplissage += value;
            fillBenne((double)value / 10, workerName);

            return true;
        }else{
            return false;
        }
    }

    public void stopBenneWork(){
        lock.unlock();
    }

    public Benne(String name, BenneView view){
        this.name = name;
        this.listeners = new ArrayList<>();
        this.view  = view;
    }

}
