package com.il4.tool.listener;

import java.util.ArrayList;

/**
 * Created by Argon on 02.04.17.
 */
public interface IWaitingBenneListener {

    void onBenneGiven(String benneName);
    void onBenneTaken(String benneName);
    void onWaitingBennesChange(ArrayList<String> bennes);


}
