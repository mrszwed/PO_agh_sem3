package ge;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SimulationParameters{
    static int width=80;
    static int height=60;
    static double startEnergy=200;
    static double moveEnergy=5;
    static double plantEnergy=200;
    static double jungleRatio=0.5;
    static int initialNumberOfAnimals=100;
    static boolean returnValue=false;
    static boolean nwMapMagic=false;
    static boolean wMapMagic=false;

    public static boolean display() {
        returnValue=false;
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Spinner<Integer> sWidth=new Spinner<>(10,2000,width);
        sWidth.setEditable(true);
        Spinner<Integer> sHeight=new Spinner<>(10,2000,height);
        sHeight.setEditable(true);
        Spinner<Double> sStartEnergy=new Spinner<>(1,100000,startEnergy);
        sStartEnergy.setEditable(true);
        Spinner<Double> sMoveEnergy=new Spinner<>(1,100000,moveEnergy);
        sMoveEnergy.setEditable(true);
        Spinner<Double> sPlantEnergy=new Spinner<>(1,100000,plantEnergy);
        sPlantEnergy.setEditable(true);
        Spinner<Double> sJungleRatio=new Spinner<>(0.01,0.99,jungleRatio,0.01);
        sJungleRatio.setEditable(true);
        Spinner<Integer> sInitialNumberOfAnimals=new Spinner<>(1,500,initialNumberOfAnimals);
        sInitialNumberOfAnimals.setEditable(true);

        CheckBox wMapMg=new CheckBox("Wrappable map magic");
        CheckBox nwMapMg=new CheckBox("Non wrappable map magic");

        Button button = new Button("Ok");
        button.setOnAction(e -> {
            width = sWidth.getValue();
            height = sHeight.getValue();
            startEnergy=sStartEnergy.getValue();
            moveEnergy=sMoveEnergy.getValue();
            plantEnergy=sPlantEnergy.getValue();
            jungleRatio=sJungleRatio.getValue();
            initialNumberOfAnimals=sInitialNumberOfAnimals.getValue();
            nwMapMagic=nwMapMg.isSelected();
            wMapMagic=wMapMg.isSelected();
            returnValue=true;
            stage.close();
        });

        Button button2 = new Button("Cancel");
        button2.setOnAction(E-> {
            returnValue=false;
            stage.close();
        });

        Label label0 = new Label(" Ustaw parametry symulacji ");
        Label label1 = new Label("Szerokość:");
        Label label2 = new Label("Wysokość:");
        Label label3 = new Label("Energia początkowa zwierząt:");
        Label label4 = new Label("Energia tracowa w każdym dniu:");
        Label label5 = new Label("Energia uzyskiwana przy zjedzeniu rośliny:");
        Label label6 = new Label("Proporcje dżungli do sawanny:");
        Label label7=new Label("Liczba początkowych zwierząt:");

        GridPane layout = new GridPane();

        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);

        layout.add(label0, 0,1);
        layout.add(label1, 0,2);
        layout.add(label2, 0,3);
        layout.add(label3, 0,4);
        layout.add(label4, 0,5);
        layout.add(label5, 0,6);
        layout.add(label6, 0,7);
        layout.add(label7, 0, 8);

        layout.add(sWidth, 1,2);
        layout.add(sHeight, 1,3);
        layout.add(sStartEnergy, 1,4);
        layout.add(sMoveEnergy, 1,5);
        layout.add(sPlantEnergy, 1,6);
        layout.add(sJungleRatio, 1,7);
        layout.add(sInitialNumberOfAnimals,1,8);

        layout.add(wMapMg,0,9);
        layout.add(nwMapMg,0,10);

        layout.add(button,0,11);
        layout.add(button2,1,11);

        Scene scene = new Scene(layout, 400, 340);
        stage.setTitle("Parametry symulacji");
        stage.setScene(scene);
        stage.showAndWait();
        return returnValue;
    }
}
