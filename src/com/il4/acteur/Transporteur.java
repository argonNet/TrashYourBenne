package com.il4.acteur;

import com.il4.WaitingBenne;

/**
 * Created by Argon on 31.03.17.
 */
public class Transporteur extends Acteur{

    public WaitingBenne waitingBenneFromBucheron;
    public WaitingBenne bucheronWaitingBenne;
    public WaitingBenne waitingBenneFromOuvrier;
    public WaitingBenne ouvrierWaitingBenne;


    private final static int TRAJET_DISTANCE = 5;

    public Transporteur(String name,
                        WaitingBenne waitingBenneFromBucheron,
                        WaitingBenne bucheronWaitingBenne,
                        WaitingBenne waitingBenneFromOuvrier,
                        WaitingBenne ouvrierWaitingBenne){
        super(name);

        this.waitingBenneFromBucheron = waitingBenneFromBucheron;
        this.bucheronWaitingBenne = bucheronWaitingBenne;
        this.waitingBenneFromOuvrier = waitingBenneFromOuvrier;
        this.ouvrierWaitingBenne = ouvrierWaitingBenne;
    }

    @Override
    public void run(){

        while(filledBenCount < BEN_TO_FILL) {

            this.setBenne(this.waitingBenneFromBucheron.TakeBenne());

            System.out.println(this.name + "-> Prise en charge de la benne :  " + this.getBenne().name + " à destination de l'ouvrier");


            for(int i = 0; i <= TRAJET_DISTANCE; i++){
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.name + "-> Acheminement de la benne : " + this.getBenne().name + " - etat : " +
                        i + " / " + TRAJET_DISTANCE);
            }


            this.ouvrierWaitingBenne.GiveBenne(this.getBenne());
            this.setBenne(this.waitingBenneFromOuvrier.TakeBenne());

            System.out.println(this.name + "-> Prise en charge de la benne :  " + this.getBenne().name + " à destination du bucheron");


            for(int i = 0; i <= TRAJET_DISTANCE; i++){
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.name + "-> Acheminement de la benne : " + this.getBenne().name + " - etat : " +
                        i + " / " + TRAJET_DISTANCE);
            }


            this.bucheronWaitingBenne.GiveBenne(this.getBenne());
            this.setBenne(null);

        }
    }

}
