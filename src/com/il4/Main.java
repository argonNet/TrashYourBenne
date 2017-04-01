package com.il4;

import com.il4.acteur.Bucheron;
import com.il4.acteur.Ouvrier;
import com.il4.acteur.Transporteur;

public class Main {

    public static void main(String[] args) {

        WaitingBenne bucheronWaitingBenne = new WaitingBenne();
        WaitingBenne ouvrierWaitingBenne = new WaitingBenne();

        //Création des trois bennes de départs;
        bucheronWaitingBenne.GiveBenne(new Benne("Benne 1"));
        bucheronWaitingBenne.GiveBenne(new Benne("Benne 2"));
        bucheronWaitingBenne.GiveBenne(new Benne("Benne 3"));

        WaitingBenne transporteurWaitingBenneFromBucheron = new WaitingBenne();
        WaitingBenne transporteurWaitingBenneFromOuvrier = new WaitingBenne();

        Transporteur transporteur = new Transporteur("Robert",
                transporteurWaitingBenneFromBucheron,bucheronWaitingBenne,
                transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);

        Bucheron bucheron = new Bucheron("Florian",transporteurWaitingBenneFromBucheron,bucheronWaitingBenne);
        Ouvrier ouvrier = new Ouvrier("Manuel",transporteurWaitingBenneFromOuvrier,ouvrierWaitingBenne);


        bucheron.start();
        transporteur.start();
        ouvrier.start();




    }
}
