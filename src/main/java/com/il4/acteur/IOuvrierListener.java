package com.il4.acteur;

/**
 * Created by Argon on 02.04.17.
 */
public interface IOuvrierListener {

    void onTakeBenne(String benneName);
    void onRemoveBoisToBenne();
    void onGiveBenne(String benneName);

}
