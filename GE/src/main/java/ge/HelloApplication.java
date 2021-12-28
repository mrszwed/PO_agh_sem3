package ge;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.ParallelSimulation;

public class HelloApplication extends Application {

    MapPane pane1;              //widok non wrappable map
    InformationPane infPane1;
    MapPane pane2;              //widok wrappable map
    InformationPane infPane2;
    BorderPane root=new BorderPane();
    ParallelSimulation simulation;
    TabPane tabPane=new TabPane();
    SimpleBooleanProperty simulationIsOn=new SimpleBooleanProperty(false);
    SimpleBooleanProperty emptyViewIsOn=new SimpleBooleanProperty(true);

    public void start(Stage primaryStage){
        primaryStage.show();

        ToolBar toolbar  = new ToolBar();
        Button buttonSetup=new Button("Setup Simulation");
        buttonSetup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setupSimulation();
            }
        });
        buttonSetup.disableProperty().bind(simulationIsOn);   //wyszarzanie
        toolbar.getItems().add(buttonSetup);

        Button buttonStep=new Button("Step");
        buttonStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                step();
            }
        });
        buttonStep.disableProperty().bind(simulationIsOn.or(emptyViewIsOn));
        toolbar.getItems().add(buttonStep);

        Button buttonRun=new Button("Run");
        buttonRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onRun();
            }
        });
        buttonRun.disableProperty().bind(simulationIsOn.or(emptyViewIsOn));
        toolbar.getItems().add(buttonRun);

        Button buttonPause=new Button("Pause");
        buttonPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onPause();
            }
        });
        buttonPause.disableProperty().bind(simulationIsOn.not().or(emptyViewIsOn));
        toolbar.getItems().add(buttonPause);

        Button buttonDom=new Button("Dominant genotype");
        buttonDom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onMarkDominantGenotype();
            }
        });
        buttonDom.disableProperty().bind(simulationIsOn.or(emptyViewIsOn));
        toolbar.getItems().add(buttonDom);

        Button buttonSave=new Button("Save data");
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onSaveData();
            }
        });
        buttonSave.disableProperty().bind(simulationIsOn.or(emptyViewIsOn));
        toolbar.getItems().add(buttonSave);

        toolbar.setStyle("-fx-background-color: lightgray;-fx-border-color: darkgray;-fx-border-width: 3px;-fx-padding: 5");
        root.setTop(toolbar);



        HBox statusbar=new HBox();
        statusbar.setStyle("-fx-background-color: lightgray;-fx-border-color: darkgray;-fx-border-width: 3px;-fx-padding: 5");
        Label statusBarLabel=new Label("Autor: Marcin Szwed");
        statusbar.getChildren().add(statusBarLabel);
        root.setBottom(statusbar);

        Scene scene = new Scene(root, 1200, 800, Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Generator ewolucyjny");


        primaryStage.show();
    }

    private void setupSimulation() {
        boolean input = SimulationParameters.display();
        if(input){
            setSimulationView();
            emptyViewIsOn.set(false);
        }
    }

    void setSimulationView(){
        simulation=new ParallelSimulation()
                .size(SimulationParameters.width,SimulationParameters.height,SimulationParameters.jungleRatio)
                .energy(SimulationParameters.startEnergy,SimulationParameters.moveEnergy,SimulationParameters.plantEnergy)
                .initialNumberOfAnimals(SimulationParameters.initialNumberOfAnimals)
                .magic(SimulationParameters.nwMapMagic,SimulationParameters.wMapMagic)
                .setup();

        pane1=new MapPane(simulation.getNwMap());
        infPane1=new InformationPane();
        simulation.getNwMap().addObserver(infPane1);
        pane2=new MapPane(simulation.getWMap());
        infPane2=new InformationPane();
        simulation.getWMap().addObserver(infPane2);
        simulation.fireStartEvent();

        SplitPane splitPane1=new SplitPane();
        ScrollPane scrollPane1left=new ScrollPane();
        scrollPane1left.setContent(infPane1);
        ScrollPane scrollPane1right=new ScrollPane();

        scrollPane1right.setContent(pane1);
        pane1.autosize();
        splitPane1.getItems().addAll(scrollPane1left,scrollPane1right);
        splitPane1.setDividerPositions(0.32);


        SplitPane splitPane2=new SplitPane();
        ScrollPane scrollPane2left=new ScrollPane();
        scrollPane2left.setContent(infPane2);
        ScrollPane scrollPane2right=new ScrollPane();

        scrollPane2right.setContent(pane2);
        pane2.autosize();
        splitPane2.getItems().addAll(scrollPane2left,scrollPane2right);
        splitPane2.setDividerPositions(0.32);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab1=new Tab("Non wrappable map", new SplitPane());
        Tab tab2=new Tab("Wrappable map", new SplitPane());

        tab1.setContent(splitPane1);
        tab2.setContent(splitPane2);
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);

        root.setCenter(tabPane);

        double w=simulation.getNwMap().getMapBoundary().getWidth()*MapPane.SIZE;
        double h=simulation.getNwMap().getMapBoundary().getHeight()*MapPane.SIZE;
        pane1.setPrefWidth(w);
        pane1.setPrefHeight(h);
        pane2.setPrefWidth(w);
        pane2.setPrefHeight(h);
        pane1.buildView();
        pane2.buildView();
        if(animationTimer !=null){
            animationTimer.stop();
            animationTimer =null;}
        animationTimer =new MyAnimationTimer();
    }


    void step(){
        simulation.simulationStep();
        pane1.buildView();
        pane2.buildView();
    }

    class MyAnimationTimer extends AnimationTimer{

        @Override
        public void handle(long now) {
            step();
        }
    }
    MyAnimationTimer animationTimer =null;

    void onRun(){
        simulationIsOn.set(true);
        animationTimer.start();
    }

    void onPause(){
        simulationIsOn.set(false);
        animationTimer.stop();
    }

    void onMarkDominantGenotype(){
        int idx=tabPane.getSelectionModel().getSelectedIndex();
        if(idx==0){
            pane1.markWithDominantGenotype();
        }
        else{
            pane2.markWithDominantGenotype();
        }
    }

    void onSaveData(){
        int idx=tabPane.getSelectionModel().getSelectedIndex();
        if(idx==0){
            infPane1.onSaveData();
        }
        else{
            infPane2.onSaveData();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}