package com.il4.view;

import com.il4.acteur.Acteur;
import com.il4.acteur.listener.ITransporteurListener;
import com.il4.petri.Petrinet;
import com.il4.petri.Place;
import com.il4.petri.Transition;
import com.il4.tool.Benne;
import com.il4.tool.listener.IWorkingBenneListener;

/**
 * Created by Aoi on 24/05/2017.
 */
public class PetriViewController implements IWorkingBenneListener,ITransporteurListener {

    private Petrinet petrinet;
    private  static String BEGIN_PLACE = "pBenneTransportLivreForet";

    public PetriViewController()
    {
        petrinet = initPetri();
    }

    private Petrinet initPetri()
    {
        Petrinet pn = new Petrinet("PetriTrashYourBenne");

        Place p1 = pn.place("pBenneVideBucheron");
        Place p2 = pn.place("pBenneRemplieTransporteur");
        Place p3 = pn.place("pBenneTransportForetUsine");
        Place p4 = pn.place("pBenneTransportLivreUsine");
        Place p5 = pn.place("pBenneRemplieOuvrier");
        Place p6 = pn.place("pBenneVideTransporteur");
        Place p7 = pn.place("pBenneTransportUsineForet");
        Place p8 = pn.place("pBenneTransportLivreForet");

        Transition t1 = pn.transition("tBucheronBenneRemplie");
        Transition t2 = pn.transition("tTransporteurPartirForet");
        Transition t3 = pn.transition("tTransporteurArriverUsine");
        Transition t4 = pn.transition("tTransporteurBenneDesamarrerUsine");
        Transition t5 = pn.transition("tOuvrierBenneVide");
        Transition t6 = pn.transition("tTransporteurPartirUsine");
        Transition t7 = pn.transition("tTransporteurArriverForet");
        Transition t8 = pn.transition("tTransporteurBenneDesamarrerForet");


        pn.arc("a1", p1, t1);
        pn.arc("a2", t1, p2);
        pn.arc("a3", p2, t2);
        pn.arc("a4", t2, p3);
        pn.arc("a5", p3, t3);
        pn.arc("a6", t3, p4);
        pn.arc("a7", p4, t4);
        pn.arc("a8", t4, p5);
        pn.arc("a9", p5, t5);
        pn.arc("a10", t5, p6);
        pn.arc("a11", p6, t6);
        pn.arc("a12", t6, p7);
        pn.arc("a13",p7,t7);
        pn.arc("a14",t7,p8);
        pn.arc("a15",p8,t8);
        pn.arc("a16",t8,p1);

        return pn;
    }

    public void addToken()
    {
        petrinet.getPlace(BEGIN_PLACE).addTokens(1);
        System.out.println(petrinet);
    }


    @Override
    public void removeWorkingBenne(Benne b, String side)  {

        Transition t = null;
        if (side.contains("Bucheron")) {
           t =petrinet.getTransition("tBucheronBenneRemplie");
        }
        else if (side.contains("Ouvrier"))
        {
            t =petrinet.getTransition("tOuvrierBenneVide");
        }
        if (t != null) {
            if (t.canFire()) {
                t.fire();
                System.out.println(petrinet);
            }
        }
    }

    @Override
    public void addWorkingBenne(Benne benne, String side)
    {
        Transition t = null;

        if (side.contains("Bucheron")) {
            t =petrinet.getTransition("tTransporteurBenneDesamarrerForet");
        }
        else if (side.contains("Ouvrier"))
        {
            t =petrinet.getTransition("tTransporteurBenneDesamarrerUsine");
        }
        if (t != null) {
            if (t.canFire()) {
                t.fire();
                System.out.println(petrinet);
            }
        }
    }

    @Override
    public void onTakeBenne(String benneName,String side){
        Transition t = null;
        if (side.contains("Bucheron")) {
            t =petrinet.getTransition("tTransporteurPartirForet");
        }
        else if (side.contains("Ouvrier"))
        {
            t =petrinet.getTransition("tTransporteurPartirUsine");
        }
        if (t != null) {
            if (t.canFire()) {
                t.fire();
                System.out.println(petrinet);
            }
        }
    }
    @Override
    public void onGoOuvrier(){
        //Inutile pour le réseau de petri
    }
    @Override
    public void onGoBucheron(){
        //Inutile pour le réseau de petri
    }
    @Override
    public void onGiveBenne(String benneName, String side)
    {
        Transition t = null;
        if (side.contains("Bucheron")) {
            t =petrinet.getTransition("tTransporteurArriverForet");
        }
        else if (side.contains("Ouvrier"))
        {
            t =petrinet.getTransition("tTransporteurArriverUsine");
        }
        if (t != null) {
            if (t.canFire()) {
                t.fire();
                System.out.println(petrinet);
            }
        }
    }

    @Override
    public void statusChange(Acteur.ThreadStatus status) {
        //Nothing to do in this.
    }
}
