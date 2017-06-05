package com.il4.acteur;

import com.il4.tool.Benne;
import com.il4.tool.listener.IWorkingBenneListener;
import com.il4.tool.WaitingBenne;
import com.il4.acteur.listener.IWorkerListener;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by Argon on 31.05.17.
 */
public abstract class Worker extends Acteur {

    private static final int MAX_WORKING_BENNE_COUNT = 2; //TODO : Set it to BUCHERON COUNT -1

    protected abstract ReadWriteLock getCurrentWorkingBennesLock();
    protected abstract LinkedList<Benne> getCurrentWorkingBennes();
    protected abstract int getValueOperation();
    protected abstract boolean isBenneReady(Benne benne);
    protected abstract void defineWorkerOperation();

    public WaitingBenne waitingBenne;
    public WaitingBenne transporteurWaitingBenne;

    public IWorkingBenneListener workingBenneListener;

    public Worker(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name);

        listeners = new ArrayList<>();

        this.transporteurWaitingBenne = transporteurWaitingBenne;
        this.waitingBenne = waitingBenne;
    }

    private void startWorkingOnBenne(String benneName) {
        listeners.forEach((listener) -> {
            Platform.runLater(() -> {
                ((IWorkerListener)listener).onStartWorkingOnBenne(benneName);
            });
        });
    }

    private void stopWorkingOnBenne() {
        listeners.forEach((listener) -> {
            Platform.runLater(() -> {
                ((IWorkerListener)listener).onStopWorkingOnBenne();
            });
        });
    }

    @Override
    public void run(){

        try{
            //while(filledBenCount < benToFill) {
            while(!getWorkIsDone()){
                //TEST si a des bennes disponible sinon en prendre une
                if (getCurrentWorkingBennes().size() <= MAX_WORKING_BENNE_COUNT) {
                    Benne benne = this.waitingBenne.TakeBenne();
                    if(benne != null) {
                        getCurrentWorkingBennes().add(benne);
                        Platform.runLater(() -> workingBenneListener.addWorkingBenne(benne));
                    }
                }

                //for (Benne currentBenne : getCurrentWorkingBennes()) {
                int i = 0;
                int notOkTry = 0;
                Benne currentBenne =  null;

                getCurrentWorkingBennesLock().readLock().lock();

                if(i <= getCurrentWorkingBennes().size() - 1) currentBenne = getCurrentWorkingBennes().get(i);

                getCurrentWorkingBennesLock().readLock().unlock();

                while(currentBenne != null && notOkTry <= MAX_WORKING_BENNE_COUNT){



                    if (currentBenne.startBenneWorkIfFree(getValueOperation(), this.getNameActeur())) {
                        startWorkingOnBenne(currentBenne.name);

                        //Transporte une bille de jusqu'à la benne
                        for(int j = 0; j < 10; j++ ){
                            sleep(speed);
                            defineWorkerOperation();
                        }

                        if(isBenneReady(currentBenne)){

                            getCurrentWorkingBennesLock().writeLock().lock();

                            getCurrentWorkingBennes().remove(currentBenne);
                            final Benne realCurrentBenne = currentBenne; //To cear a warning
                            Platform.runLater(() ->  workingBenneListener.removeWorkingBenne(realCurrentBenne));
                            transporteurWaitingBenne.GiveBenne(currentBenne);

                            getCurrentWorkingBennesLock().writeLock().unlock();

                        }

                        currentBenne.stopBenneWork();
                        stopWorkingOnBenne();
                        break;
                    }else{
                        notOkTry++;
                    }

                    getCurrentWorkingBennesLock().readLock().lock();

                    if(getCurrentWorkingBennes().size() - 1 > i){
                        i++;
                        currentBenne = getCurrentWorkingBennes().get(i);
                    }

                    getCurrentWorkingBennesLock().readLock().unlock();
                }

                sleep(20); //Petite pause du bucheron avant de reprendre une bille de bois
            }
        }catch(InterruptedException e){
            System.out.println("Error : " + e.getMessage());
        }

        System.out.println("Fin du travail pour " + this.name );
    }
}


