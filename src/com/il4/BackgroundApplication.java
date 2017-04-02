package com.il4;

import com.il4.acteur.Bucheron;
import com.il4.acteur.Ouvrier;
import com.il4.acteur.Transporteur;
import com.il4.view.component.BucheronView;
import com.il4.view.MainViewController;
import com.il4.view.component.OuvrierView;

/**
 * Created by Argon on 02.04.17.
 */
public class BackgroundApplication {

    private MainViewController view;

    WaitingBenne bucheronWaitingBenne ;
    WaitingBenne ouvrierWaitingBenne ;

    WaitingBenne transporteurWaitingBenneFromBucheron;
    WaitingBenne transporteurWaitingBenneFromOuvrier;

    Transporteur transporteur;
    Bucheron bucheron;
    Ouvrier ouvrier;


    public void createBucheron(String name,MainViewController viewMain, BucheronView view){
        bucheron = new Bucheron(name,viewMain,transporteurWaitingBenneFromBucheron,bucheronWaitingBenne);
        bucheron.listeners.add(view);
    }

    public void createOuvrier(String name,MainViewController viewMain, OuvrierView view){
        ouvrier = new Ouvrier(name,viewMain,transporteurWaitingBenneFromBucheron,bucheronWaitingBenne);
        ouvrier.listeners.add(view);
    }

    public void Start(MainViewController view) {


        bucheron.start();
        transporteur.start();
        ouvrier.start();

    }


    private BackgroundApplication(){

//
//        WaitingBenne bucheronWaitingBenne = new WaitingBenne(view.BucheronWaitingBenne);
//        WaitingBenne ouvrierWaitingBenne = new WaitingBenne(view.OuvrierWaitingBenne);
//
//        //Création des trois bennes de départs;
//        bucheronWaitingBenne.GiveBenne(new Benne("Benne 1"));
//        bucheronWaitingBenne.GiveBenne(new Benne("Benne 2"));
//        bucheronWaitingBenne.GiveBenne(new Benne("Benne 3"));
//        bucheronWaitingBenne.GiveBenne(new Benne("Benne 4"));
//        bucheronWaitingBenne.GiveBenne(new Benne("Benne 5"));
//        bucheronWaitingBenne.GiveBenne(new Benne("Benne 6"));
//
//        WaitingBenne transporteurWaitingBenneFromBucheron = new WaitingBenne(view.TransporteurWaitingBenneBucheron);
//        WaitingBenne transporteurWaitingBenneFromOuvrier = new WaitingBenne(view.TransporteurWaitingBenneOuvrier);
//
//        Transporteur transporteur = new Transporteur("Robert",view,
//                transporteurWaitingBenneFromBucheron,bucheronWaitingBenne,
//                transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);
//
//        Bucheron bucheron = new Bucheron("Florian",view,transporteurWaitingBenneFromBucheron,bucheronWaitingBenne);
//        Ouvrier ouvrier = new Ouvrier("Manuel",view,transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);

    }

    private static final BackgroundApplication instance = new BackgroundApplication();

    public static BackgroundApplication getInstance(){
        return instance;
    }

}
