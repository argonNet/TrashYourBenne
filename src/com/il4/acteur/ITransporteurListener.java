package com.il4.acteur;

/**
 * Created by Argon on 02.04.17.
 */
public interface ITransporteurListener {

    void onTakeBenne(String benneName);
    void onGoOuvrier();
    void onGoBucheron();
    void onGiveBenne(String benneName);


}
