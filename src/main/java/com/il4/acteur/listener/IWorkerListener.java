package com.il4.acteur.listener;

/**
 * Created by Argon on 31.05.17.
 */
public interface IWorkerListener extends  IActeurListener {

    void onStartWorkingOnBenne(String benneName);
    void onStopWorkingOnBenne();

}
