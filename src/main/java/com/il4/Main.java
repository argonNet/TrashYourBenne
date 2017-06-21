package com.il4;

import com.il4.view.MainViewController;
import com.il4.view.component.PetrinetView;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //Graphical part
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("mainView.fxml"));
            Parent root = loader.load();
            Scene primaryScene = new Scene(root);

            primaryStage.setTitle("TrashYourBenne");
            primaryStage.setScene(primaryScene);

            PetrinetView pv = PetrinetView.getInstance();
            Stage monitorStage = new Stage();
            Scene monitorScene = new Scene(pv.pane);
            monitorStage.setScene(monitorScene);
            monitorStage.setTitle("Petri net");

            // show both stages:
            monitorStage.show();
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

