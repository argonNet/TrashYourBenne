package com.il4;

import com.il4.acteur.Acteur;
import com.il4.acteur.Bucheron;
import com.il4.acteur.Ouvrier;
import com.il4.acteur.Transporteur;
import com.il4.view.MainViewController;
import com.il4.view.WaitingBenneViewController;
import com.il4.view.component.BucheronView;
import com.il4.view.component.OuvrierView;
import com.il4.view.component.TransporteurView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Argon on 02.04.17.
 */
public class BackgroundApplication {

    private MainViewController view;

    private WaitingBenne bucheronWaitingBenne ;
    private WaitingBenne ouvrierWaitingBenne ;
    private WaitingBenne transporteurWaitingBenneFromBucheron;
    private WaitingBenne transporteurWaitingBenneFromOuvrier;

    private ArrayList<Benne> bennes = new ArrayList<>();

    private ArrayList<Transporteur> transporteurs = new ArrayList<>();
    private ArrayList<Bucheron> bucherons = new ArrayList<>();
    private ArrayList<Ouvrier> ouvriers = new ArrayList<>();

    private boolean isRunning = false;

    public void setBenneToFillCount(int benneCount){
        Acteur.setBenToFill(benneCount);
    }

    public void createBucheron(String name, BucheronView view){
        Bucheron bucheron = new Bucheron(name,transporteurWaitingBenneFromBucheron,bucheronWaitingBenne);
        bucheron.listeners.add(view);
        bucherons.add(bucheron);
        view.setIdBucheron(bucherons.indexOf(bucheron));
        if(isRunning) bucheron.start();
    }

    public void createOuvrier(String name, OuvrierView view){
        Ouvrier ouvrier = new Ouvrier(name,transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);
        ouvrier.listeners.add(view);
        ouvriers.add(ouvrier);
        view.setIdOuvrier(ouvriers.indexOf(ouvrier));
        if(isRunning) ouvrier.start();
    }


    public void createTransporteur(String name, TransporteurView view){
        Transporteur transporteur = new Transporteur(name,
                                                     transporteurWaitingBenneFromBucheron,bucheronWaitingBenne,
                                                     transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);
        transporteur.listeners.add(view);
        transporteurs.add(transporteur);
        view.setIdTransporteur(transporteurs.indexOf(transporteur));
        if(isRunning) transporteur.start();
    }

    public void createWaitingBenneQueues(
            WaitingBenneViewController bucheronWaitingBenneView,
            WaitingBenneViewController ouvrierWaitingBenneView,
            WaitingBenneViewController transporteurWaitingBenneFromBucheronView,
            WaitingBenneViewController transporteurWaitingBenneFromOuvrierView){

        bucheronWaitingBenne = new WaitingBenne();
        bucheronWaitingBenne.listeners.add(bucheronWaitingBenneView);

        ouvrierWaitingBenne = new WaitingBenne();
        ouvrierWaitingBenne.listeners.add(ouvrierWaitingBenneView);

        transporteurWaitingBenneFromBucheron = new WaitingBenne();
        transporteurWaitingBenneFromBucheron.listeners.add(transporteurWaitingBenneFromBucheronView);

        transporteurWaitingBenneFromOuvrier = new WaitingBenne();
        transporteurWaitingBenneFromOuvrier.listeners.add(transporteurWaitingBenneFromOuvrierView);
    }


    public void createBenne(String name){
        Benne benne = new Benne(name);
        bennes.add(benne);
        bucheronWaitingBenne.GiveBenne(benne);
    }
    
    public Bucheron getBucheron(int index)
    {
        return bucherons.get(index);
    }

    public Ouvrier getOuvrier(int index)
    {
        return ouvriers.get(index);
    }

    public Transporteur getTransporteur(int index)
    {
        return transporteurs.get(index);
    }

    public int getBennesCount(){
        return  bennes.size();
    }


    public void Start( ) {

        isRunning = true;

        bucherons.forEach((bucheron -> bucheron.start()));
        transporteurs.forEach((transporteur -> transporteur.start()));
        ouvriers.forEach((ouvrier -> ouvrier.start()));
    }


    private BackgroundApplication(){
    }

    private static final BackgroundApplication instance = new BackgroundApplication();

    public static BackgroundApplication getInstance(){
        return instance;
    }

}
