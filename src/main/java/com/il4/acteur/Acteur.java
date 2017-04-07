package com.il4.acteur;

import com.il4.Benne;

import java.util.ArrayList;

/**
 * Created by Argon on 31.03.17.
 */
public class Acteur extends Thread{
    

    private Benne benne;

    protected String name;

    protected static int benToFill = 100;
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


    public Acteur(String name){
        this.name = name;
    }

    public static void setBenToFill(int value){
        benToFill = value;
    }

}
