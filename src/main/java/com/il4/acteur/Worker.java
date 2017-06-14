package com.il4.acteur;

import com.il4.acteur.listener.IWorkerListener;
import com.il4.tool.Benne;
import com.il4.tool.WaitingBenne;
import com.il4.tool.listener.IWorkingBenneListener;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

/**
 * Created by Argon on 31.05.17.
 */
public abstract class Worker extends Acteur {

    private static final int MAX_WORKING_BENNE_COUNT = 2; //TODO : Set it to BUCHERON COUNT -1

    protected abstract Lock getCurrentWorkingBennesLock();
    protected abstract LinkedList<Benne> getCurrentWorkingBennes();
    protected abstract int getValueOperation();
    protected abstract boolean isBenneReady(Benne benne);
    protected abstract void defineWorkerOperation();
    protected abstract boolean shouldIContinueMyWork();

    public WaitingBenne waitingBenne;
    public WaitingBenne transporteurWaitingBenne;

    public ArrayList<IWorkingBenneListener> workingBenneListeners;

    public Worker(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name);

        listeners = new ArrayList<>();
        workingBenneListeners = new ArrayList<>();

        this.transporteurWaitingBenne = transporteurWaitingBenne;
        this.waitingBenne = waitingBenne;
    }

    public void addWorkingBenneListener(IWorkingBenneListener listener){
        workingBenneListeners.add(listener);
    }

    private void addWorkingBenne(Benne benneName) {
        workingBenneListeners.forEach((listener) -> {
            Platform.runLater(() -> {
                if(listener instanceof  IWorkingBenneListener) listener.addWorkingBenne(benneName, this.getClass().getName());
            });
        });
    }

    private void removeWorkingBenne(Benne benneName) {
        workingBenneListeners.forEach((listener) -> {
            Platform.runLater(() -> {
                if(listener instanceof  IWorkingBenneListener) listener.removeWorkingBenne(benneName,this.getClass().getName());
            });
        });
    }

    private void startWorkingOnBenne(String benneName) {
        listeners.forEach((listener) -> {
            Platform.runLater(() -> {
                if(listener instanceof  IWorkerListener) ((IWorkerListener)listener).onStartWorkingOnBenne(benneName);
            });
        });
    }

    private void stopWorkingOnBenne() {
        listeners.forEach((listener) -> {
            Platform.runLater(() -> {
                if(listener instanceof  IWorkerListener) ((IWorkerListener)listener).onStopWorkingOnBenne();
            });
        });
    }

    @Override
    public void performWork(){

        try{

            while(shouldIContinueMyWork()){

                Benne currentBenne =  null;

                Acteur.threadAwait();
                getCurrentWorkingBennesLock().lock();
                Acteur.threadRun();
                try{

                    // 1 - Prends une benne en attente, pour la passer en cours de travail
                    if (getCurrentWorkingBennes().size() <= MAX_WORKING_BENNE_COUNT) {
                        Benne benne = this.waitingBenne.TakeBenne();
                        if(benne != null) {
                            getCurrentWorkingBennes().add(benne);
                            addWorkingBenne(benne);
                        }
                    }

                    // ... - s'assurer que le travail n'est pas terminé
                    if(!shouldIContinueMyWork()) break;

                    // 2 - Remplir une benne, si la 1er est occupée on prend la suivant jusqu'à en trouver une.
                    for (Benne parsedBenne : getCurrentWorkingBennes()) {
                        if (parsedBenne.startBenneWorkIfFree()){
                            currentBenne = parsedBenne;
                            break;
                        }

                        // ... - s'assurer que le travail n'est pas terminé
                        if(!shouldIContinueMyWork()) break;

                    }

                }finally {
                    getCurrentWorkingBennesLock().unlock();
                }

                // 3 - Si on a pu obtenir une benne
                if(currentBenne != null){

                    // ... - s'assurer que le travail n'est pas terminé
                    if(!shouldIContinueMyWork()) break;

                    startWorkingOnBenne(currentBenne.getName());

                    // 3a - Définir la nouvelle valeur de la benne
                    currentBenne.setNewValue(getValueOperation(),this.getNameActeur());
                    this.incOperationCount();

                    // 3b - Simulation du travail
                    for (int j = 0; j < 10; j++) {
                        sleep(speed);
                        defineWorkerOperation(); //Effectue l'opération
                    }

                    // 3c - Test si la benne est prête
                    if (isBenneReady(currentBenne)) {

                        Acteur.threadAwait();
                        getCurrentWorkingBennesLock().lock();
                        Acteur.threadRun();

                        try{
                            getCurrentWorkingBennes().remove(currentBenne);
                            removeWorkingBenne(currentBenne);
                            transporteurWaitingBenne.GiveBenne(currentBenne);
                        }finally {
                            getCurrentWorkingBennesLock().unlock();
                        }
                    }

                    currentBenne.stopBenneWork();
                    stopWorkingOnBenne();
                }

                sleep(20); //Petite pause du bucheron avant de reprendre une bille de bois
            }
        }catch(InterruptedException e){
            System.out.println("Error : " + e.getMessage());
        }
    }
}


