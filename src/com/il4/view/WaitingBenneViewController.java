package com.il4.view;

import com.il4.Benne;
import javafx.scene.control.ListView;

/**
 * Created by Argon on 02.04.17.
 */
public class WaitingBenneViewController {

    private ListView<String> listView;

    public WaitingBenneViewController(ListView<String> listView){
        this.listView = listView;
    }

    public void AddBenne(Benne benne){
        listView.getItems().add(benne.name);
    }

    public void RemoveBenne(Benne benne){
        listView.getItems().remove(benne.name);
    }



}
