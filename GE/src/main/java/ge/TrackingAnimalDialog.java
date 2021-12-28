package ge;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import simulation.Animal;

public class TrackingAnimalDialog {
    Animal animal;
    int startNumberOfChildren;
    int startNumberOfOffsprings;
    GridPane layout=new GridPane();
    Button button = new Button("Close");
    TrackingAnimalDialog(Animal animal){
        this.animal=animal;
        startNumberOfChildren=animal.numberOfChildren;
        startNumberOfOffsprings=animal.numberOfOffsprings;
    }


    public void display() {
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.NONE);

        buildView();

        button.setOnAction(e -> {
            stage.close();
        });




        Scene scene = new Scene(layout, 700, 350);
        stage.setTitle("Szczegóły zwierzęcia");
        stage.setScene(scene);
        stage.showAndWait();
    }

    void buildView(){

        layout.getChildren().clear();
        Label label0 = new Label("Genotyp: ");
        Label label0a = new Label(animal.genotypToString());
        Label label1 = new Label("Data urodzenia:");
        Label label1a = new Label(Integer.toString(animal.birthDate));
        Label label2 = new Label("Czas życia:");
        Label label2a = new Label(Integer.toString(animal.lifeSpan));
        Label label3=new Label("Data zgonu:");
        Label label3a=new Label(animal.isDead()?Integer.toString(animal.birthDate+animal.lifeSpan):"zwierzę żyje");
        Label label4 = new Label("Liczba dzieci od początku symulacji:");
        Label label4a = new Label(Integer.toString(animal.numberOfChildren));
        Label label5 = new Label("Liczba potomków od początku symulacji:");
        Label label5a = new Label(Integer.toString(animal.numberOfOffsprings));
        Label label6 = new Label("Liczba dzieci od rozpoczęcia śledzenia:");
        Label label6a = new Label(Integer.toString(animal.numberOfChildren-startNumberOfChildren));
        Label label7 = new Label("Liczba potomków od rozpoczęcia śledzenia:");
        Label label7a = new Label(Integer.toString(animal.numberOfOffsprings-startNumberOfOffsprings));
        Label label8 = new Label("Pozycja:");
        Label label8a = new Label(animal.position.toString());
        Label label9 = new Label("Energia:");
        Label label9a = new Label(Double.toString(animal.energy));


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
        layout.add(label8, 0, 9);
        layout.add(label9, 0, 10);

        layout.add(label0a, 1,1);
        layout.add(label1a, 1,2);
        layout.add(label2a, 1,3);
        layout.add(label3a, 1,4);
        layout.add(label4a, 1,5);
        layout.add(label5a, 1,6);
        layout.add(label6a, 1,7);
        layout.add(label7a, 1, 8);
        layout.add(label8a, 1, 9);
        layout.add(label9a, 1, 10);

        layout.add(button,0,11);
    }

    void update(){
        buildView();
    }
}
