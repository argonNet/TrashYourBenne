package com.il4.acteur;

import com.il4.Benne;
import com.il4.view.MainViewController;

/**
 * Created by Argon on 31.03.17.
 */
public class Acteur extends Thread{


    protected String name;
    protected  MainViewController view;

    private Benne benne;

    protected static final int BEN_TO_FILL = 100;
    protected static int filledBenCount = 0;

    protected synchronized void incFilledBenCount(){
        filledBenCount++;
    }

    public Benne getBenne(){
        return this.benne;
    }

    public synchronized void setBenne(Benne value){
        this.benne = value;
    }

    public Acteur(String name, MainViewController view){
        this.name = name;
        this.view = view;
    }

}
