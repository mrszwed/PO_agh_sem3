package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends Application implements ISimulationObserver {
    MyCanvas myCanvas;
    Label statusBarLabel;
    GrassField map=new GrassField(10);
    SimulationEngine engine;
    Thread animationThread;

    SimpleBooleanProperty simulationIsOn = new SimpleBooleanProperty(false);

    public void start(Stage primaryStage){
        setupWorld();
        primaryStage.show();

        var root=new BorderPane();

        ToolBar toolbar  = new ToolBar();
        Button button1=new Button("Step");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                engine.step();
                if(engine.atEnd())engine.reset();
            }
        });
        button1.disableProperty().bind(simulationIsOn); //wyszarzanie
        toolbar.getItems().add(button1);


        Button button2=new Button("Run");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onStart();
            }
        });
        button2.disableProperty().bind(simulationIsOn);
        toolbar.getItems().add(button2);
        Button button3=new Button("Stop");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onStop();
            }
        });
        button3.disableProperty().bind(simulationIsOn.not());
        toolbar.getItems().add(button3);
        toolbar.setStyle("-fx-background-color: lightgray;-fx-border-color: darkgray;-fx-border-width: 3px;-fx-padding: 5");
        root.setTop(toolbar);

        ScrollPane center=new ScrollPane();
        myCanvas=new MyCanvas(map, 2000,2000);
        center.setContent(myCanvas);
//        myCanvas.widthProperty().bind(center.widthProperty());
//        myCanvas.heightProperty().bind(center.heightProperty());    //zmiana rozmiarow canvas przy zmianie rozmiaru okna

        myCanvas.draw();
        root.setCenter(center);
//        center.setVvalue(0.5);
//        center.setHvalue(0.5);

        HBox statusbar=new HBox();
        statusbar.setStyle("-fx-background-color: lightgray;-fx-border-color: darkgray;-fx-border-width: 3px;-fx-padding: 5");
        statusBarLabel=new Label("text w statusbar");
        statusbar.getChildren().add(statusBarLabel);
        root.setBottom(statusbar);




        Scene scene = new Scene(root, 900, 900);

        primaryStage.setScene(scene);
        primaryStage.setTitle("test javafx");
        primaryStage.show();
    }

    @Override
    public void stepDone(String message) {
        myCanvas.redraw();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                statusBarLabel.setText(message);
            }
        });

    }


    void setupWorld(){
        String polecenia="f b r l f f r r f f f f f f f f l f f r b b r r f f l l f f b l r";
        String[] args=polecenia.split(" ");
        MoveDirection[] directions = new OptionsParser().parse(args);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4), new Vector2d(8,8)};
        engine = new SimulationEngine(directions, map, positions, Arrays.asList(map, map.getBoundary()));
        engine.addSimulationObserver(this);
    }

    void onStart(){
        animationThread=new Thread((Runnable) engine);
        animationThread.start();
        simulationIsOn.set(true);
    }
    void onStop(){
        if(animationThread!=null){engine.stop(); animationThread=null;}
        simulationIsOn.set(false);
        engine.reset();
    }

    public static void init(String [] args){
        App.launch(App.class, args);
    }
}
