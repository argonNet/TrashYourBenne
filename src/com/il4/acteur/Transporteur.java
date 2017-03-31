package com.il4.acteur;

/**
 * Created by Argon on 31.03.17.
 */
public class Transporteur extends Acteur{

    public Ouvrier ouvrier;
    public Bucheron bucheron;

    public Transporteur(String name,Ouvrier ouvrier, Bucheron bucheron){
        super(name);
        this.ouvrier = ouvrier;
        this.bucheron = bucheron;
    }
}
