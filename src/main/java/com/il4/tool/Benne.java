package com.il4.tool;

import com.il4.acteur.Acteur;
import com.il4.tool.listener.IBenneListener;
import com.il4.view.component.BenneView;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Benne, objet remplis par les bucherons, transporté par les transporteur et vidé par les ouvriers
 */
public class Benne {

    private static final int MAX_REMPLISSAGE = 10;

    private String name;
    private int remplissage;
    private final Lock lock = new ReentrantLock();

    public BenneView view;
    public ArrayList<IBenneListener> listeners;

    public Benne(String name, BenneView viewB){
        this.name = name;
        this.listeners = new ArrayList<>();
        this.view = viewB;
    }

    public boolean isFull(){
        return  remplissage >= MAX_REMPLISSAGE;
    }

    public boolean isEmpty(){
        return  remplissage <= 0;
    }

    /**
     * Essaie de prendre la main sur la benne (acquérir le lock).
     * @return True = l'opération a réussi.
     */
    public boolean startBenneWorkIfFree() throws InterruptedException{

        ((Acteur)Thread.currentThread()).setStatus(Acteur.ThreadStatus.Await);
        if (lock.tryLock(10, TimeUnit.MILLISECONDS)){

            ((Acteur)Thread.currentThread()).setStatus(Acteur.ThreadStatus.Running);
            return true;

        }else{

            ((Acteur)Thread.currentThread()).setStatus(Acteur.ThreadStatus.Running);
            return false;
        }
    }

    /**
     * Libère la benne (libère le lock).
     */
    public void stopBenneWork(){
        lock.unlock();
    }

    /**
     * Ajoute la valeur (value) dans la benne
     * @param value Valeur à ajouter
     * @param workerName Nom de la personne qui effectue l'opération
     */
    public void setNewValue(int value, String workerName){
        this.remplissage += value;
        fillBenne((double)value / 10, workerName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Notifie les abonnée que la benne a été remplie et par qui.
     */
    private void fillBenne(double value, String workerName){
        listeners.forEach( (listener) -> {
            Platform.runLater(() -> {
                listener.onFillBenne(value, workerName);
            });
        });
    }

}
