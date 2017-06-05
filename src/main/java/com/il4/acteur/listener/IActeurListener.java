package com.il4.acteur.listener;

import com.il4.acteur.Acteur;

/**
 * Created by Argon on 05.06.17.
 */
public interface IActeurListener {

    void statusChange(Acteur.ThreadStatus status);

}
