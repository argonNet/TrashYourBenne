package com.il4.petri;
public class Place 
extends PetrinetObject {

    private static int MAX_TOKENS = 100;
    private int tokens = 0;

    

    protected Place(String name) {
        super(name);
    }

    public int getTokens() { return this.tokens;}


    public boolean hasAtLeastTokens(int threshold) {
        return (tokens >= threshold);
    }


    public boolean maxTokensReached(int newTokens) {
        return (tokens+newTokens > MAX_TOKENS);
    }


    public void addTokens(int weight) {
        this.tokens += weight;
    }

    public void removeTokens(int weight) {
        this.tokens -= weight;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               " Tokens=" + this.tokens;
    }
}
