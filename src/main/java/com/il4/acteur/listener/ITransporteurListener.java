package com.il4.acteur.listener;

/**
 * Created by Argon on 02.04.17.
 */
public interface ITransporteurListener extends IActeurListener{

    void onTakeBenne(String benneName);
    void onGoOuvrier();
    void onGoBucheron();
    void onGiveBenne(String benneName);


}
