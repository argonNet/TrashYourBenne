package com.il4.acteur.listener;

/**
 * Created by Argon on 02.04.17.
 */
public interface ITransporteurListener {

    void onTakeBenne(String benneName,String side);
    void onGoOuvrier();
    void onGoBucheron();
    void onGiveBenne(String benneName,String side);


}
