package com.il4.view.component;

import com.il4.acteur.Acteur;
import com.il4.acteur.listener.IPetrinetListener;
import com.il4.acteur.listener.ITransporteurListener;
import com.il4.petri.Petrinet;
import com.il4.tool.Benne;
import com.il4.tool.PetrinetUtils;
import com.il4.tool.listener.IWorkingBenneListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Aoi on 14/06/2017.
 */
public class PetrinetView implements IPetrinetListener {

    private static final PetrinetView instance = new PetrinetView();
    public AnchorPane pane;
    @FXML public Label countBenneVidePourBucheron;
    @FXML public Label countBenneRempliePourTransporteur;
    @FXML public Label countBenneTransportForetUsine;
    @FXML public Label countBenneTransportLivreUsine;
    @FXML public Label countBenneRempliePourOuvrier;
    @FXML public Label countBenneVidePourTransporteur;
    @FXML public Label countBenneTransportUsineForet;
    @FXML public Label countBenneTransportLivreForet;

    private PetrinetView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("petrinetView.fxml"));
        fxmlLoader.setController(this);

        try {
            pane = (AnchorPane) fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void OnChange(Petrinet petrinet) {
        updatePlace(petrinet,countBenneVidePourBucheron,PetrinetUtils.PLACE_BENNE_VIDE_POUR_BUCHERON);
        updatePlace(petrinet,countBenneRempliePourTransporteur,PetrinetUtils.PLACE_BENNE_REMPLIE_POUR_TRANSPORTEUR);

        updatePlace(petrinet,countBenneTransportForetUsine,PetrinetUtils.PLACE_TRANSPORT_BENNE_FORET_A_USINE);
        updatePlace(petrinet,countBenneTransportLivreUsine,PetrinetUtils.PLACE_TRANSPORT_BENNE_LIVREE_A_USINE);
        updatePlace(petrinet,countBenneRempliePourOuvrier,PetrinetUtils.PLACE_BENNE_REMPLIE_POUR_OUVRIER);
        updatePlace(petrinet,countBenneVidePourTransporteur,PetrinetUtils.PLACE_BENNE_VIDE_POUR_TRANSPORTEUR);
        updatePlace(petrinet,countBenneTransportUsineForet,PetrinetUtils.PLACE_TRANSPORT_BENNE_USINE_A_FORET);
        updatePlace(petrinet,countBenneTransportLivreForet,PetrinetUtils.PLACE_TRANSPORT_BENNE_LIVREE_A_FORET);
    }

    @Override
    public void OnExceptionOccured(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur réseau petri");
        alert.setHeaderText("Erreur dans le réseau de petri");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static PetrinetView getInstance(){
        return instance;
    }

    private void updatePlace(Petrinet petrinet,Label placeLabel, String placeName)
    {
        placeLabel.setText(Integer.toString(petrinet.getPlace(placeName).getTokens()));
    }
}
