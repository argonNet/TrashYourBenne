package com.il4;

import com.il4.acteur.Acteur;
import com.il4.acteur.Bucheron;
import com.il4.acteur.Ouvrier;
import com.il4.acteur.Transporteur;
import com.il4.tool.Benne;
import com.il4.tool.listener.IWorkingBenneListener;
import com.il4.tool.WaitingBenne;
import com.il4.view.MainViewController;
import com.il4.view.PetriViewController;
import com.il4.view.WaitingBenneViewController;
import com.il4.view.component.BenneView;
import com.il4.view.component.BucheronView;
import com.il4.view.component.OuvrierView;
import com.il4.view.component.TransporteurView;

import java.util.ArrayList;

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

    public void setBucheronWorkingBenneMaxCount(int benneCount){
        Bucheron.setMaxWorkingBenneCount(benneCount);
    }

    public void setOuvrierWorkingBenneMaxCount(int benneCount){
        Ouvrier.setMaxWorkingBenneCount(benneCount);
    }

    public void createBucheron(String name,
                               BucheronView view,
                               IWorkingBenneListener workingBenneListener,
                               PetriViewController pvctrl,
                               MainViewController mainView){
        Bucheron bucheron = new Bucheron(name,transporteurWaitingBenneFromBucheron,bucheronWaitingBenne);
        bucheron.addListener(view);
        bucheron.addWorkingBenneListener(workingBenneListener);
        bucheron.addWorkingBenneListener(pvctrl);
        bucheron.addListener(mainView);
        bucherons.add(bucheron);
        view.setIdBucheron(bucherons.indexOf(bucheron));
        if(isRunning) bucheron.start();
    }

    public void createOuvrier(String name,
                              OuvrierView view,
                              IWorkingBenneListener workingBenneListener,
                              PetriViewController pvctrl,
                              MainViewController mainView){
        Ouvrier ouvrier = new Ouvrier(name,transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);
        ouvrier.addListener(view);
        ouvrier.addWorkingBenneListener(workingBenneListener);
        ouvrier.addWorkingBenneListener(pvctrl);
        ouvrier.addListener(mainView);
        ouvriers.add(ouvrier);
        view.setIdOuvrier(ouvriers.indexOf(ouvrier));
        if(isRunning) ouvrier.start();
    }


    public void createTransporteur(String name,
                                   TransporteurView view,
                                   PetriViewController pvctrl,
                                   MainViewController mainView){
        Transporteur transporteur = new Transporteur(name,
                                                     transporteurWaitingBenneFromBucheron,bucheronWaitingBenne,
                                                     transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);
        transporteur.addListener(view);
        transporteur.addListener(pvctrl);
        transporteur.addListener(mainView);
        transporteurs.add(transporteur);
        view.setIdTransporteur(transporteurs.indexOf(transporteur));
        if(isRunning) transporteur.start();
    }


    public void createBenne(String name, BenneView view){
        Benne benne = new Benne(name,view);
        benne.listeners.add(view);
        bennes.add(benne);
        bucheronWaitingBenne.GiveBenne(benne);
    }

    public void createWaitingBenneQueues(
            WaitingBenneViewController bucheronWaitingBenneView,
            WaitingBenneViewController ouvrierWaitingBenneView,
            WaitingBenneViewController transporteurWaitingBenneFromBucheronView,
            WaitingBenneViewController transporteurWaitingBenneFromOuvrierView){

        bucheronWaitingBenne = new WaitingBenne(WaitingBenne.WaitingMode.noWaiting);
        bucheronWaitingBenne.listeners.add(bucheronWaitingBenneView);

        ouvrierWaitingBenne = new WaitingBenne(WaitingBenne.WaitingMode.noWaiting);
        ouvrierWaitingBenne.listeners.add(ouvrierWaitingBenneView);

        transporteurWaitingBenneFromBucheron = new WaitingBenne(WaitingBenne.WaitingMode.waitingInQueue);
        transporteurWaitingBenneFromBucheron.listeners.add(transporteurWaitingBenneFromBucheronView);

        transporteurWaitingBenneFromOuvrier = new WaitingBenne(WaitingBenne.WaitingMode.waitingInQueue);
        transporteurWaitingBenneFromOuvrier.listeners.add(transporteurWaitingBenneFromOuvrierView);
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
