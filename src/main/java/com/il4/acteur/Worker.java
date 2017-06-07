package com.il4.acteur;

import com.il4.tool.Benne;
import com.il4.tool.listener.IWorkingBenneListener;
import com.il4.tool.WaitingBenne;
import com.il4.acteur.listener.IWorkerListener;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Argon on 31.05.17.
 */
public abstract class Worker extends Acteur {

    protected abstract LinkedList<Benne> getCurrentFillingBennes();
    protected abstract int getValueOperation();
    protected abstract boolean isBenneReady(Benne benne);
    protected abstract void defineWorkerOperation();

    public WaitingBenne waitingBenne;
    public WaitingBenne transporteurWaitingBenne;

    public ArrayList<IWorkerListener> listeners;
    public ArrayList<IWorkingBenneListener> workingBenneListeners;



    public Worker(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name);

        listeners = new ArrayList<>();
        workingBenneListeners = new ArrayList<>();

        this.transporteurWaitingBenne = transporteurWaitingBenne;
        this.waitingBenne = waitingBenne;
    }

    private void startWorkingOnBenne(String benneName) {
        listeners.forEach((listener) -> {
            Platform.runLater(() -> {
                listener.onStartWorkingOnBenne(benneName);
            });
        });
    }

    private void stopWorkingOnBenne() {
        listeners.forEach((listener) -> {
            Platform.runLater(() -> {
                listener.onStopWorkingOnBenne();
            });
        });
    }

    public void addWorkingBenneListener(IWorkingBenneListener listener){
        workingBenneListeners.add(listener);
    }

    private void addWorkingBenne(Benne benneName) {
        workingBenneListeners.forEach((listener) -> {
            Platform.runLater(() -> {
                listener.addWorkingBenne(benneName, this.getClass().getName());
            });
        });
    }

    private void stopWorkingOnBenne(Benne benneName) {
        workingBenneListeners.forEach((listener) -> {
            Platform.runLater(() -> {
                listener.removeWorkingBenne(benneName,this.getClass().getName());
            });
        });
    }

    @Override
    public void run(){

        try{
            while(filledBenCount < benToFill) {

                //TEST si a des bennes disponible sinon en prendre une
                if (getCurrentFillingBennes().isEmpty()) {
                    Benne benne = this.waitingBenne.TakeBenne();
                    if(benne != null) {
                        getCurrentFillingBennes().add(benne);
                       // Platform.runLater(() -> workingBenneListener.addWorkingBenne(benne));
                        try {
                            sleep(100); //WORKAROUND pour résoudre le problème que l'ouvrier prend la benne avant que le transporteur le lui donne...
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        addWorkingBenne(benne);
                    }
                }

                for (Benne currentBenne : getCurrentFillingBennes()) {
                    if (currentBenne.startBenneWorkIfFree(getValueOperation(), this.getNameActeur())) {
                        startWorkingOnBenne(currentBenne.name);

                        //Transporte une bille de jusqu'à la benne
                        for(int i = 0; i < 10; i++ ){
                            sleep(speed);
                            defineWorkerOperation();
                        }

                        if(isBenneReady(currentBenne)){
                            getCurrentFillingBennes().remove(currentBenne);
                            //Platform.runLater(() ->  workingBenneListener.removeWorkingBenne(currentBenne));
                            stopWorkingOnBenne(currentBenne);
                            incFilledBenCount();
                            transporteurWaitingBenne.GiveBenne(currentBenne);
                        }

                        currentBenne.stopBenneWork();
                        stopWorkingOnBenne();
                        break;
                    }
                }

                sleep(500); //Petite pause du bucheron avant de reprendre une bille de bois
            }
        }catch(InterruptedException e){
            System.out.println("Error : " + e.getMessage());
        }

        System.out.println("Fin du travail pour " + this.name );
    }
}


