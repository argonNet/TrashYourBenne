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

        Place p1 = pn.createPlace("pBenneVideBucheron");
        Place p2 = pn.createPlace("pBenneRemplieTransporteur");
        Place p3 = pn.createPlace("pBenneTransportForetUsine");
        Place p4 = pn.createPlace("pBenneTransportLivreUsine");
        Place p5 = pn.createPlace("pBenneRemplieOuvrier");
        Place p6 = pn.createPlace("pBenneVideTransporteur");
        Place p7 = pn.createPlace("pBenneTransportUsineForet");
        Place p8 = pn.createPlace("pBenneTransportLivreForet");

        Transition t1 = pn.createTransition("tBucheronBenneRemplie");
        Transition t2 = pn.createTransition("tTransporteurPartirForet");
        Transition t3 = pn.createTransition("tTransporteurArriverUsine");
        Transition t4 = pn.createTransition("tTransporteurBenneDesamarrerUsine");
        Transition t5 = pn.createTransition("tOuvrierBenneVide");
        Transition t6 = pn.createTransition("tTransporteurPartirUsine");
        Transition t7 = pn.createTransition("tTransporteurArriverForet");
        Transition t8 = pn.createTransition("tTransporteurBenneDesamarrerForet");


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

    public void addToken()
    {
        petrinet.getPlace(BEGIN_PLACE).addTokens(1);
        System.out.println(petrinet);
    }


    @Override
    public void removeWorkingBenne(Benne b, String side)   {
            fireTransition(side, "tBucheronBenneRemplie", "tOuvrierBenneVide");
    }

    @Override
    public void addWorkingBenne(Benne benne, String side) {
        fireTransition(side,"tTransporteurBenneDesamarrerForet", "tTransporteurBenneDesamarrerUsine");
    }

    @Override
    public void onTakeBenne(String benneName,String side){
        fireTransition(side,"tTransporteurPartirForet", "tTransporteurPartirUsine");
    }

    @Override
    public void onGiveBenne(String benneName, String side)
    {
        fireTransition(side,"tTransporteurArriverForet", "tTransporteurArriverUsine");
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
                    System.out.println(petrinet);
                }
            }
            catch(Exception e)
            {
                System.out.println("Exception sur le réseau de Petri: "+ e);
            }
        }
    }
}
