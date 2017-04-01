package com.il4.acteur;

import com.il4.WaitingBenne;


/**
 * Created by Argon on 31.03.17.
 */
public class Bucheron extends Acteur{



    public  WaitingBenne waitingBenne;

    public WaitingBenne transporteurWaitingBenne;

    public Bucheron(String name, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name);
        this.transporteurWaitingBenne = transporteurWaitingBenne;
        this.waitingBenne = waitingBenne;
    }

    @Override
    public void run(){

        while(filledBenCount < BEN_TO_FILL) {

            this.setBenne(this.waitingBenne.TakeBenne());

            System.out.println(this.name + "-> Récupération de la benne :  " + this.getBenne().name);


            while (this.getBenne().remplissage < this.getBenne().MAX_REMPLISSAGE) {
                this.getBenne().remplissage++;
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.name + "-> Remplissage de la benne : " + this.getBenne().name + " - etat : " +
                        this.getBenne().remplissage + " / " + this.getBenne().MAX_REMPLISSAGE);
            }

            this.transporteurWaitingBenne.GiveBenne(this.getBenne());
            incFilledBenCount();
            this.setBenne(null);
        }
    }

}
