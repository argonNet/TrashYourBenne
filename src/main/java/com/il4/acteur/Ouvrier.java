package com.il4.acteur;

import com.il4.tool.Benne;
import com.il4.tool.WaitingBenne;
import com.il4.acteur.listener.IOuvrierListener;
import javafx.application.Platform;

import java.util.LinkedList;

/**
 * Created by Argon on 31.03.17.
 */
public class Ouvrier extends Worker {

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
    protected LinkedList<Benne> getCurrentFillingBennes() {
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

