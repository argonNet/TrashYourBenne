package com.il4.view;

import com.il4.acteur.Acteur;
import com.il4.acteur.listener.IPetrinetListener;
import com.il4.acteur.listener.ITransporteurListener;
import com.il4.petri.Petrinet;
import com.il4.petri.Place;
import com.il4.petri.Transition;
import com.il4.tool.Benne;
import com.il4.tool.PetrinetUtils;
import com.il4.tool.listener.IWorkingBenneListener;
import javafx.application.Platform;

/**
 * Created by Aoi on 24/05/2017.
 */


public class PetriViewController implements IWorkingBenneListener,ITransporteurListener {

    private Petrinet petrinet;


    private  static String BEGIN_PLACE = PetrinetUtils.PLACE_TRANSPORT_BENNE_LIVREE_A_FORET;
    private IPetrinetListener petrinetListener;

    public PetriViewController()
    {
        petrinet = initPetri();
    }

    @Override
    public void removeWorkingBenne(Benne b, String side)   {
            fireTransition(side, PetrinetUtils.TRANSITION_BENNE_REMPLIE_PAR_BUCHERON, PetrinetUtils.TRANSITION_BENNE_VIDEE_PAR_OUVRIER);
    }

    @Override
    public void addWorkingBenne(Benne benne, String side) {
        fireTransition(side,PetrinetUtils.TRANSITION_BENNE_DESAMARRE_FORET, PetrinetUtils.TRANSITION_BENNE_DESAMARRE_USINE);
    }

    @Override
    public void onTakeBenne(String benneName,String side){
        fireTransition(side, PetrinetUtils.TRANSITION_TRANSPORTEUR_PARTIR_FORET, PetrinetUtils.TRANSITION_TRANSPORTEUR_PARTIR_USINE);
    }

    @Override
    public void onGiveBenne(String benneName, String side)
    {
        fireTransition(side,PetrinetUtils.TRANSITION_TRANSPORTEUR_ARRIVER_FORET, PetrinetUtils.TRANSITION_TRANSPORTEUR_ARRIVER_USINE);
    }

    @Override
    public void onGoOuvrier(){
        //Nothing to do in this.
    }
    @Override
    public void onGoBucheron(){
        //Nothing to do in this.
    }

    @Override
    public void statusChange(Acteur.ThreadStatus status) {
        //Nothing to do in this.
    }

    @Override
    public void operationDone(Acteur sender) {
        //Nothing to do in this.
    }

    public void addToken()
    {
        petrinet.getPlace(BEGIN_PLACE).addTokens(1);
    }

    public void SetPetrinetListener(IPetrinetListener petrinetListener) { this.petrinetListener = petrinetListener;}

    private Petrinet initPetri()
    {
        Petrinet pn = new Petrinet("PetriTrashYourBenne");
        Place p1 = pn.createPlace(PetrinetUtils.PLACE_BENNE_VIDE_POUR_BUCHERON);
        Place p2 = pn.createPlace(PetrinetUtils.PLACE_BENNE_REMPLIE_POUR_TRANSPORTEUR);
        Place p3 = pn.createPlace(PetrinetUtils.PLACE_TRANSPORT_BENNE_FORET_A_USINE);
        Place p4 = pn.createPlace(PetrinetUtils.PLACE_TRANSPORT_BENNE_LIVREE_A_USINE);
        Place p5 = pn.createPlace(PetrinetUtils.PLACE_BENNE_REMPLIE_POUR_OUVRIER);
        Place p6 = pn.createPlace(PetrinetUtils.PLACE_BENNE_VIDE_POUR_TRANSPORTEUR);
        Place p7 = pn.createPlace(PetrinetUtils.PLACE_TRANSPORT_BENNE_USINE_A_FORET);
        Place p8 = pn.createPlace(PetrinetUtils.PLACE_TRANSPORT_BENNE_LIVREE_A_FORET);

        Transition t1 = pn.createTransition(PetrinetUtils.TRANSITION_BENNE_REMPLIE_PAR_BUCHERON);
        Transition t2 = pn.createTransition(PetrinetUtils.TRANSITION_TRANSPORTEUR_PARTIR_FORET);
        Transition t3 = pn.createTransition(PetrinetUtils.TRANSITION_TRANSPORTEUR_ARRIVER_USINE);
        Transition t4 = pn.createTransition(PetrinetUtils.TRANSITION_BENNE_DESAMARRE_USINE);
        Transition t5 = pn.createTransition(PetrinetUtils.TRANSITION_BENNE_VIDEE_PAR_OUVRIER);
        Transition t6 = pn.createTransition(PetrinetUtils.TRANSITION_TRANSPORTEUR_PARTIR_USINE);
        Transition t7 = pn.createTransition(PetrinetUtils.TRANSITION_TRANSPORTEUR_ARRIVER_FORET);
        Transition t8 = pn.createTransition(PetrinetUtils.TRANSITION_BENNE_DESAMARRE_FORET);


        pn.createArc("a1", p1, t1);
        pn.createArc("a2", t1, p2);
        pn.createArc("a3", p2, t2);
        pn.createArc("a4", t2, p3);
        pn.createArc("a5", p3, t3);
        pn.createArc("a6", t3, p4);
        pn.createArc("a7", p4, t4);
        pn.createArc("a8", t4, p5);
        pn.createArc("a9", p5, t5);
        pn.createArc("a10", t5, p6);
        pn.createArc("a11", p6, t6);
        pn.createArc("a12", t6, p7);
        pn.createArc("a13",p7,t7);
        pn.createArc("a14",t7,p8);
        pn.createArc("a15",p8,t8);
        pn.createArc("a16",t8,p1);

        return pn;
    }

    private void fireTransition(String side, String bucheronTransition, String ouvrierTransition)
    {
        Transition t = null;
        if (side.contains("Bucheron")) {
            t =petrinet.getTransition(bucheronTransition);
        }
        else if (side.contains("Ouvrier"))
        {
            t =petrinet.getTransition(ouvrierTransition);
        }
        if (t != null) {
            try {
                if (t.canFire()) {
                    t.fire();

                    Platform.runLater(() -> {
                        if(petrinetListener instanceof  IPetrinetListener) petrinetListener.OnChange(petrinet);
                    });
                }
            }
            catch(Exception e)
            {
                Platform.runLater(() -> {
                    if(petrinetListener instanceof  IPetrinetListener) petrinetListener.OnExceptionOccured(e);
                });

            }
        }
    }
}
