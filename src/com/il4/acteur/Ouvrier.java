package com.il4.acteur;

import com.il4.WaitingBenne;

/**
 * Created by Argon on 31.03.17.
 */
public class Ouvrier extends Acteur {

    public WaitingBenne waitingBenne;

    public WaitingBenne transporteurWaitingBenne;


    public Ouvrier(String name,WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne){
        super(name);

        this.waitingBenne = waitingBenne;
        this.transporteurWaitingBenne = transporteurWaitingBenne;
    }

    @Override
    public void run(){

        while(filledBenCount < BEN_TO_FILL) {

            this.setBenne(this.waitingBenne.TakeBenne());

            System.out.println(this.name + "-> Récupération de la benne :  " + this.getBenne().name);


            while (this.getBenne().remplissage < this.getBenne().MAX_REMPLISSAGE) {
                this.getBenne().remplissage--;
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.name + "-> Vidage de la benne : " + this.getBenne().name + " - etat : " +
                        this.getBenne().remplissage + " / " + 0);
            }

            this.transporteurWaitingBenne.GiveBenne(this.getBenne());
            this.setBenne(null);
        }
    }
}

