package com.il4.view.component;

import com.il4.acteur.Acteur;
import com.il4.acteur.listener.IActeurListener;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import sun.plugin.javascript.navig.Anchor;

/**
 * Created by Argon on 05.06.17.
 */
public class ActeurView extends AnchorPane implements IActeurListener {

    @FXML AnchorPane mainPane;

    @Override
    public void statusChange(Acteur.ThreadStatus status) {

        mainPane.getStyleClass().clear();

        switch (status){
            case Await:
                mainPane.getStyleClass().add("waiting");
                break;

            case AwaitAlone:
                mainPane.getStyleClass().add("waitingAlone");
                break;

            case AwaitInQueue:
                mainPane.getStyleClass().add("waitingInQueue");
                break;
            case Running:
                mainPane.getStyleClass().add("running");
                break;
        }

    }
}
