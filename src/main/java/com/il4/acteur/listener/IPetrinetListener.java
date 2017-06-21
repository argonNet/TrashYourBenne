package com.il4.acteur.listener;

import com.il4.petri.Petrinet;

/**
 * Created by Aoi on 21/06/2017.
 */
public interface IPetrinetListener {

    void OnChange (Petrinet petrinet);
    void OnExceptionOccured (Exception e);
}
