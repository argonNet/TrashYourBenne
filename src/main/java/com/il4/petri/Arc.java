package com.il4.petri;

public class Arc
extends PetrinetObject {

    enum Direction {

        PLACE_TO_TRANSITION,
        TRANSITION_TO_PLACE
    }

    Place place;
    Transition transition;
    Direction direction;
    int weight = 1;
    

    private Arc(String name, Direction d, Place p, Transition t) {
        super(name);
        this.direction = d;
        this.place = p;
        this.transition = t;
    }

    protected Arc(String name, Place p, Transition t) {
        this(name, Direction.PLACE_TO_TRANSITION, p, t);
        t.addIncoming(this);
    }

    protected Arc(String name, Transition t, Place p) {
        this(name, Direction.TRANSITION_TO_PLACE, p, t);
        t.addOutgoing(this);
    }

    public boolean canFire() throws Exception {

        if (direction == Direction.PLACE_TO_TRANSITION) {
           return place.hasAtLeastTokens(weight);
        }
        else if (direction == Direction.TRANSITION_TO_PLACE) {
            return !place.maxTokensReached(weight);
        }
        else throw new Exception("Le mode de direction est incorrect");
    }
    
    public void fire() throws Exception{
        if (direction == Direction.PLACE_TO_TRANSITION) {
             place.removeTokens(weight);
        }
        else if (direction == Direction.TRANSITION_TO_PLACE){
            place.addTokens(weight);
        }
        else throw new Exception("Le mode de direction est incorrect");
    }

}
