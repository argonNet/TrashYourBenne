package com.il4.tool.listener;

import com.il4.acteur.listener.IWorkerListener;
import com.il4.tool.Benne;

/**
 * Created by Argon on 24.05.17.
 */
public interface IWorkingBenneListener {
    void addWorkingBenne(Benne benne, String side);
    void removeWorkingBenne(Benne benne, String side);
}
