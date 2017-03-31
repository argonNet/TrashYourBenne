package com.il4.acteur;

import com.il4.Benne;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TRANSACTION_MODE;

/**
 * Created by Argon on 31.03.17.
 */
public class Bucheron extends Acteur{

    private static final int MAX_BEN = 3;
    private int benCount = 0;

    public Transporteur transporteur;

    public Bucheron(String name, Transporteur transporteur) {
        super(name);
        this.transporteur = transporteur;
    }


    @Override
    public void run(){

        while(this.getBenne() == null) {

            if (benCount < MAX_BEN) {
                benCount++;
                this.setBenne(new Benne(String.valueOf(benCount)));
                System.out.println(this.name + "-> Creation de la benne :  " + this.getBenne().name);
            }else{
                try {

                    while(!this.IsABenneWaiting()){
                        wait();
                    }

                    this.setBenne(this.TakeBenne());
                    System.out.println(this.name + "-> Récupération de la benne :  " + this.getBenne().name);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

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

            this.transporteur.GiveBenne(this.getBenne());
            this.setBenne(null);
            this.notifyAll();
        }
    }

}
