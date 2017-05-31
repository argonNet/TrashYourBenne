package com.il4.acteur;

import com.il4.tool.Benne;
import com.il4.tool.WaitingBenne;
import com.il4.acteur.listener.IBucheronListener;
import javafx.application.Platform;

import java.util.LinkedList;

/**
 * Created by Argon on 31.03.17.
 */
public class Bucheron extends Worker {

    protected static LinkedList<Benne> currentFillingBennes = new LinkedList<>();

    public Bucheron(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name,transporteurWaitingBenne,waitingBenne);
    }

    private void addBoisToBenne() {
        listeners.forEach((listener) -> {
            Platform.runLater(() -> {
                ((IBucheronListener)listener).onAddBoisToBenne();
            });
        });
    }

    @Override
    protected LinkedList<Benne> getCurrentFillingBennes() {
        return currentFillingBennes;
    }

    @Override
    protected void defineWorkerOperation(){
        addBoisToBenne();
    }

    @Override
    protected boolean isBenneReady(Benne benne){
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

