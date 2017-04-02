package com.il4.acteur;

/**
 * Created by Argon on 02.04.17.
 */
public interface IBucheronListener {

        void onTakeBenne(String benneName);
        void onAddBoisToBenne();
        void onGiveBenne(String benneName);
}


