package com.il4;

import com.il4.view.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    private MainViewController view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //Graphical part
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/il4/view/mainView.fxml"));
            Parent root = loader.load();
            view = loader.getController();

            primaryStage.setTitle("WOOD SIMULATOR");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

