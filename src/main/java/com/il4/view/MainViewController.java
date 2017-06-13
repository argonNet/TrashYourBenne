package com.il4.view;

import com.il4.BackgroundApplication;
import com.il4.acteur.Acteur;
import com.il4.acteur.Bucheron;
import com.il4.acteur.Ouvrier;
import com.il4.acteur.Transporteur;
import com.il4.acteur.listener.IActeurListener;
import com.il4.view.component.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Argon on 01.04.17.
 */
    public class MainViewController implements Initializable, IActeurListener {

    private enum ActeurType{
        Bucheron,
        Transporteur,
        Ouvrier
    }

    @FXML public VBox bucheronsPane;
    @FXML public VBox fillingBennePane;
    @FXML public VBox transporteursPane;
    @FXML public VBox emptyingBennePane;
    @FXML public VBox ouvriersPane;

    @FXML public TextField textFieldBucheronName;
    @FXML public TextField textFieldTransporteurName;
    @FXML public TextField textFieldOuvrierName;

    @FXML public Spinner<Integer> spinnerTotalBenneToFill;

    @FXML public Button buttonStart;

    @FXML public ListView<String> ListTransporteurWaitingBenneBucheron;
    @FXML public ListView<String> ListTransporteurWaitingBenneOuvrier;
    @FXML public ListView<String> ListBucheronWaitingBenne;

    @FXML public ListView<String> ListOuvrierWaitingBenne;

    @FXML public BarChart<String,Integer>  bucheronWorkChart;
    @FXML public CategoryAxis xAxisBucheron;
    private XYChart.Series<String, Integer> bucheronWorkChartSeries = new XYChart.Series<>();

    @FXML public BarChart<String,Integer> transporteurWorkChart;
    @FXML public CategoryAxis xAxisTransporteur;
    private XYChart.Series<String, Integer> transporteurWorkChartSeries = new XYChart.Series<>();

    @FXML public BarChart<String,Integer> ouvrierWorkChart;
    @FXML public CategoryAxis xAxisOuvrier;
    private XYChart.Series<String, Integer> ouvrierWorkChartSeries = new XYChart.Series<>();

    public WaitingBenneViewController transporteurWaitingBenneBucheron;
    public WaitingBenneViewController transporteurWaitingBenneOuvrier;
    public WaitingBenneViewController bucheronWaitingBenne;
    public WaitingBenneViewController ouvrierWaitingBenne;

    public WorkingBenneView fillingBenneView;
    public WorkingBenneView emptyingBenneView;
    private PetriViewController petriNet;

    private XYChart.Data<String,Integer> createNewBarForChart(String name , ActeurType acteurType){
        XYChart.Data<String,Integer> bar = new XYChart.Data<>(name,0);

        bar.nodeProperty().addListener((observable, oldValue, newValue) -> {
            switch (acteurType) {
                case Bucheron:
                    newValue.setStyle("-fx-bar-fill: #1b9b00;");
                    break;
                case Transporteur:
                    newValue.setStyle("-fx-bar-fill: #55259b;");
                    break;
                case Ouvrier:
                    newValue.setStyle("-fx-bar-fill: #272f9b;");
                    break;
            }
        });

        return bar;
    }

    private void addBucheron(String name){
        BucheronView newView = new BucheronView();
        newView.setName(name);
        bucheronsPane.getChildren().add(newView);
        bucheronWorkChartSeries.getData().add(createNewBarForChart(name,ActeurType.Bucheron));

        BackgroundApplication.getInstance().createBucheron(name,newView, this.fillingBenneView,petriNet,this);
    }

    private void addTransporteur(String name){
        TransporteurView newView = new TransporteurView();
        newView.setName(name);
        transporteursPane.getChildren().add(newView);
        transporteurWorkChartSeries.getData().add(createNewBarForChart(name,ActeurType.Transporteur));

        BackgroundApplication.getInstance().createTransporteur(name,newView,petriNet,this);
    }

    private void addOuvrier(String name){
        OuvrierView newView = new OuvrierView();
        newView.setName(name);
        ouvriersPane.getChildren().add(newView);
        ouvrierWorkChartSeries.getData().add(createNewBarForChart(name,ActeurType.Ouvrier));

        BackgroundApplication.getInstance().createOuvrier(name,newView, this.emptyingBenneView, petriNet,this);
    }

    private void addBenne(String name){
        BenneView newView = new BenneView();
        newView.setName(name);
        BackgroundApplication.getInstance().createBenne(name,newView);
        petriNet.addToken();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        petriNet = new PetriViewController();
        spinnerTotalBenneToFill.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100));

        bucheronWaitingBenne = new WaitingBenneViewController(ListBucheronWaitingBenne);
        ouvrierWaitingBenne = new WaitingBenneViewController(ListOuvrierWaitingBenne);

        transporteurWaitingBenneBucheron = new WaitingBenneViewController(ListTransporteurWaitingBenneBucheron);
        transporteurWaitingBenneOuvrier = new WaitingBenneViewController(ListTransporteurWaitingBenneOuvrier);

        fillingBenneView = new WorkingBenneView(fillingBennePane);
        emptyingBenneView = new WorkingBenneView(emptyingBennePane);

        BackgroundApplication.getInstance().createWaitingBenneQueues(
                bucheronWaitingBenne,
                ouvrierWaitingBenne,
                transporteurWaitingBenneBucheron,
                transporteurWaitingBenneOuvrier);


        bucheronWorkChart.getData().add(bucheronWorkChartSeries);
        transporteurWorkChart.getData().add(transporteurWorkChartSeries);
        ouvrierWorkChart.getData().add(ouvrierWorkChartSeries);

        addBenne("Benne 1");
        addBenne("Benne 2");
        addBenne("Benne 3");

        addBucheron("Florian");
        addTransporteur("Robert");
        addOuvrier("Manuel");

        spinnerTotalBenneToFill.getValueFactory().valueProperty().setValue(20);

    }


    public void buttonStartClick(){
        BackgroundApplication.getInstance().setBenneToFillCount(spinnerTotalBenneToFill.getValue());
        BackgroundApplication.getInstance().Start();
        buttonStart.setDisable(true);
    }

    @FXML  public void buttonAddBucheron(){
        addBucheron(textFieldBucheronName.getText());
    }

    @FXML  public void buttonAddTransporteur() { addTransporteur(textFieldTransporteurName.getText());}

    @FXML  public void buttonAddOuvrier(){
        addOuvrier(textFieldOuvrierName.getText());
    }

    @FXML  public void buttonAddBenneClick() {
        addBenne("Benne " + (BackgroundApplication.getInstance().getBennesCount() + 1));
    }


    @Override
    public void statusChange(Acteur.ThreadStatus status) {
        //Nothing to do in it
    }

    @Override
    public void operationDone(Acteur sender) {

        XYChart.Series<String, Integer> workChartSerie = null;

        if(sender instanceof Bucheron){
            workChartSerie = bucheronWorkChartSeries;
        }else if(sender instanceof Transporteur){
            workChartSerie = transporteurWorkChartSeries;
        }else if(sender instanceof Ouvrier){
            workChartSerie = ouvrierWorkChartSeries;
        }

        workChartSerie.getData().filtered((x) -> {
            return x.XValueProperty().getValue() == sender.getNameActeur();
        }).get(0).YValueProperty().setValue(sender.getOperationCount());
    }
}
