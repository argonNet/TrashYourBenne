package com.il4.petri;

import java.util.ArrayList;
import java.util.List;


public class Petrinet
extends PetrinetObject {

    private static final String nl = "\n";
    List<Place> places              = new ArrayList<Place>();
    List<Transition> transitions    = new ArrayList<Transition>();
    List<Arc> arcs                  = new ArrayList<Arc>();
    
    public Petrinet(String name) {
        super(name);
    }

    public void add(PetrinetObject o) {
        if (o instanceof Arc) {
            arcs.add((Arc) o);
        } else if (o instanceof Place) {
            places.add((Place) o);
        } else if (o instanceof Transition) {
            transitions.add((Transition) o);
        }
    }
    
    public Transition createTransition(String name) {
        Transition t = new Transition(name);
        transitions.add(t);
        return t;
    }
    
    public Place createPlace(String name) {
        Place p = new Place(name);
        places.add(p);
        return p;
    }
    
    public Arc createArc(String name, Place p, Transition t) {
        Arc arc = new Arc(name, p, t);
        arcs.add(arc);
        return arc;
    }
    
    public Arc createArc(String name, Transition t, Place p) {
        Arc arc = new Arc(name, t, p);
        arcs.add(arc);
        return arc;
    }

    public Place getPlace(String placeName) {
        Place place = null;
        for (Place p : places)
            if (p.getName() == placeName) {
                place = p;
                break;
            }
        return place;
    }

    public Transition getTransition(String transitionName) {
        Transition transition = null;
        for (Transition t : transitions)
            if (t.getName() == transitionName) {
                transition = t;
                break;
            }
        return transition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Petrinet ");
        sb.append(super.toString()).append(nl);
        sb.append("---Transitions---").append(nl);
        for (Transition t : transitions) {
            sb.append(t).append(nl);
        }
        sb.append("---Places---").append(nl);
        for (Place p : places) {
            sb.append(p).append(nl);
        }
        return sb.toString();
    }


}
