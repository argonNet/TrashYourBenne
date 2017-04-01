package com.il4.acteur;

import com.il4.WaitingBenne;
import com.il4.view.MainViewController;
import javafx.application.Platform;

/**
 * Created by Argon on 31.03.17.
 */
public class Ouvrier extends Acteur {

    public WaitingBenne waitingBenne;

    public WaitingBenne transporteurWaitingBenne;


    public Ouvrier(String name, MainViewController mainViewController, WaitingBenne transporteurWaitingBenne, WaitingBenne waitingBenne){
        super(name,mainViewController);

        this.waitingBenne = waitingBenne;
        this.transporteurWaitingBenne = transporteurWaitingBenne;
    }

    @Override
    public void run(){

        while(filledBenCount < BEN_TO_FILL) {


            this.setBenne(this.waitingBenne.TakeBenne());
            Platform.runLater(() -> {
                        view.OuvrierReset();
                        view.OuvrierSetCurrentBenne(this.getBenne().name);
                    });
            System.out.println(this.name + "-> Récupération de la benne :  " + this.getBenne().name);


            while (this.getBenne().remplissage > 0) {
                this.getBenne().remplissage--;
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.name + "-> Vidage de la benne : " + this.getBenne().name + " - etat : " +
                        this.getBenne().remplissage + " / " + 0);
                Platform.runLater(() -> view.OuvrierRemoveBois());
            }

            this.transporteurWaitingBenne.GiveBenne(this.getBenne());
            this.setBenne(null);
            Platform.runLater(() -> {view.OuvrierResetCurrentBenne();});

        }
    }
}

