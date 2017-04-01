package com.il4.acteur;

import com.il4.WaitingBenne;
import com.il4.view.MainViewController;
import javafx.application.Platform;


/**
 * Created by Argon on 31.03.17.
 */
public class Bucheron extends Acteur{



    public  WaitingBenne waitingBenne;

    public WaitingBenne transporteurWaitingBenne;

    public Bucheron(String name, MainViewController mainViewController, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne) {
        super(name,mainViewController);
        this.transporteurWaitingBenne = transporteurWaitingBenne;
        this.waitingBenne = waitingBenne;
    }

    @Override
    public void run(){

        while(filledBenCount < BEN_TO_FILL) {

            this.setBenne(this.waitingBenne.TakeBenne());

            Platform.runLater(()-> {
                view.BucheronReset();
                view.BucheronSetCurrentBenne(this.getBenne().name);
            });
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

                Platform.runLater(() -> view.BucheronAddBois());
            }

            this.transporteurWaitingBenne.GiveBenne(this.getBenne());
            incFilledBenCount();
            this.setBenne(null);
            Platform.runLater(() -> {
                view.BucheronReset();
                view.BucheronResetCurrentBenne();
            });
        }
    }

}
