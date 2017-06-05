package com.il4.acteur;

import com.il4.tool.Benne;
import com.il4.tool.WaitingBenne;
import com.il4.acteur.listener.IOuvrierListener;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Argon on 31.03.17.
 */
public class Ouvrier extends Worker {

    protected static ReadWriteLock currentEmptyingBennesLock = new ReentrantReadWriteLock();
    protected static LinkedList<Benne> currentEmptyingBennes = new LinkedList<>();

    private void removeBoisToBenne(){

        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                ((IOuvrierListener)listener).onRemoveBoisToBenne();
            });
        });
    }

    public Ouvrier(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne){
        super(name,transporteurWaitingBenne,waitingBenne);
    }

    @Override
    protected ReadWriteLock getCurrentWorkingBennesLock() {
        return currentEmptyingBennesLock;
    }

    @Override
    protected LinkedList<Benne> getCurrentWorkingBennes() {
        return currentEmptyingBennes;
    }

    @Override
    protected void defineWorkerOperation(){
        removeBoisToBenne();
    }

    @Override
    protected boolean isBenneReady(Benne benne){
        return benne.isEmpty();
    }

    @Override
    protected int getValueOperation(){
        return -1;
    }

    public void addListener(IOuvrierListener listener){
        listeners.add(listener);
    }
}

