package com.il4.acteur;

import com.il4.tool.Benne;
import com.il4.tool.WaitingBenne;
import com.il4.acteur.listener.IBucheronListener;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Argon on 31.03.17.
 */
public class Bucheron extends Worker {

    protected static Lock currentFillingBennesLock = new ReentrantLock();
    protected static LinkedList<Benne> currentFillingBennes = new LinkedList<>();

    public Bucheron(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name,transporteurWaitingBenne,waitingBenne);
    }

    private void addBoisToBenne() {
        listeners.forEach((listener) -> {
            Platform.runLater(() -> {
                if(listener instanceof  IBucheronListener) ((IBucheronListener)listener).onAddBoisToBenne();
            });
        });
    }

    @Override
    protected Lock getCurrentWorkingBennesLock() {
        return currentFillingBennesLock;
    }

    @Override
    protected LinkedList<Benne> getCurrentWorkingBennes() {
        return currentFillingBennes;
    }

    @Override
    protected void defineWorkerOperation(){
        addBoisToBenne();
    }

    @Override
    protected boolean shouldIContinueMyWork() {
        //Les bucherons s'arrêtent lorsque le total de benne a été remplis
        //(même si des bennes sont en cours de remplissage)
        return !isTotalBenneCountFilled();
    }

    @Override
    protected boolean isBenneReady(Benne benne){
        if(benne.isFull()){
            incFilledBenneCount();
        }

        return benne.isFull();
    }

    @Override
    protected int getValueOperation(){
        return 1;
    }

    public void addListener(IBucheronListener listener){
        listeners.add(listener);
    }

}

