package com.il4.view.component;

import com.il4.acteur.Acteur;
import com.il4.acteur.listener.IActeurListener;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Argon on 05.06.17.
 */
public class ActeurView extends AnchorPane implements IActeurListener {

    @FXML AnchorPane mainPane;

    @Override
    public void statusChange(Acteur.ThreadStatus status) {

        mainPane.getStyleClass().removeAll("waiting","running", "end");

        switch (status){
            case Await:
                mainPane.getStyleClass().add("waiting");
                break;

            case Running:
                mainPane.getStyleClass().add("running");
                break;

            case End:
                mainPane.getStyleClass().add("end");
                break;
        }

    }

    @Override
    public void operationDone(Acteur sender) {
        //No operation to perform
    }
}
