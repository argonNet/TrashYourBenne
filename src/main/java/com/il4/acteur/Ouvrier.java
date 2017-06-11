package com.il4.acteur;

import com.il4.acteur.listener.IOuvrierListener;
import com.il4.tool.Benne;
import com.il4.tool.WaitingBenne;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Argon on 31.03.17.
 */
public class Ouvrier extends Worker {

    protected static Lock currentEmptyingBennesLock = new ReentrantLock();
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
    protected Lock getCurrentWorkingBennesLock() {
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
    protected boolean shouldIContinueMyWork() {
        //Un ouvrier s'arrête lorsque toutes les bennes qui devaient être remplies on été vidées
        return !isTotalBenneCountFilled() ||
                !(
                    isTotalBenneCountFilled() &&
                    !this.waitingBenne.IsABenneWaiting() &&
                    !isATransporteurWorking()
                );
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

