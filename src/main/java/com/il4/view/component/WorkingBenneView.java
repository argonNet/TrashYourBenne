package com.il4.view.component;

import com.il4.tool.Benne;
import com.il4.tool.listener.IWorkingBenneListener;
import javafx.scene.layout.VBox;

/**
 * Created by Argon on 28.05.17.
 */
public class WorkingBenneView implements IWorkingBenneListener {

    private VBox bennePane;

    public WorkingBenneView(VBox BennePane) {
        this.bennePane = BennePane;
    }

    @Override
    public void addWorkingBenne(Benne benne) {
        bennePane.getChildren().add(benne.view);
    }

    @Override
    public void removeWorkingBenne(Benne benne) {
        bennePane.getChildren().remove(benne.view);
    }

}
