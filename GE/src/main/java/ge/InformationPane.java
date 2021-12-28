package ge;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import simulation.MapObserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

public class InformationPane extends Pane implements MapObserver {
    LineChart<Number,Number> lineChartAnimalGrass;
    LineChart<Number,Number> lineChartEnergy;
    LineChart<Number,Number> lineChartAverageLifeSpan;
    LineChart<Number,Number> lineChartAverageNumberOfChildren;
    BarChart<String,Number>  barCHartGenotype;
    BarChart<String,Number>  barCHartDominantGenotype;
    XYChart.Series<Number,Number> numberOfAnimals=new XYChart.Series<>();
    XYChart.Series<Number,Number> numberOfGrass=new XYChart.Series<>();
    XYChart.Series<Number,Number> averageEnergy=new XYChart.Series<>();
    XYChart.Series<Number,Number> averageLifeSpan=new XYChart.Series<>();
    XYChart.Series<Number,Number> averageNumberOfChildren=new XYChart.Series<>();
    Label infoMagic =new Label("");

    InformationPane(){
        super();
        VBox vbox=new VBox();
        this.getChildren().add(vbox);
        infoMagic.setStyle("-fx-pref-width: 360;-fx-alignment:center;-fx-font-size: 16px;-fx-font-family: \"Arial Black\";-fx-background-color: red;-fx-text-fill:white;-fx-padding: 10;-fx-border-color:blue;-fx-border-width: 2");
        infoMagic.setVisible(false);
        lineChartAnimalGrass =new LineChart<Number,Number>(new NumberAxis(), new NumberAxis());
        lineChartAnimalGrass.setTitle("Animals & Grass");
        lineChartEnergy =new LineChart<Number,Number>(new NumberAxis(), new NumberAxis());
        lineChartEnergy.setTitle("Average energy");
        lineChartAverageLifeSpan =new LineChart<Number,Number>(new NumberAxis(), new NumberAxis());
        lineChartAverageLifeSpan.setTitle("Average life span");
        lineChartAverageNumberOfChildren =new LineChart<Number,Number>(new NumberAxis(), new NumberAxis());
        lineChartAverageNumberOfChildren.setTitle("Average number of children");

        barCHartGenotype = new BarChart<>(new CategoryAxis(),new NumberAxis());
        barCHartGenotype.setTitle("Gene statistics");
        barCHartGenotype.setCategoryGap(0);
        barCHartGenotype.setBarGap(5);

        barCHartDominantGenotype = new BarChart<>(new CategoryAxis(),new NumberAxis());
        barCHartDominantGenotype.setTitle("Dominant genotype");
        barCHartDominantGenotype.setCategoryGap(0);
        barCHartDominantGenotype.setBarGap(5);

        vbox.getChildren().add(infoMagic);
        vbox.getChildren().add(lineChartAnimalGrass);
        vbox.getChildren().add(lineChartEnergy);
        vbox.getChildren().add(lineChartAverageLifeSpan);
        vbox.getChildren().add(lineChartAverageNumberOfChildren);
        vbox.getChildren().add(barCHartGenotype);
        vbox.getChildren().add(barCHartDominantGenotype);

        lineChartAnimalGrass.setCreateSymbols(false);
        lineChartEnergy.setCreateSymbols(false);
        lineChartAverageLifeSpan.setCreateSymbols(false);
        lineChartAverageNumberOfChildren.setCreateSymbols(false);

        numberOfAnimals.setName("number of animals");
        numberOfGrass.setName("number of grass clumps");
        averageEnergy.setName("average energy");
        averageLifeSpan.setName("average life span");
        averageNumberOfChildren.setName("average number of children");
    }


    void setSeries(int xValue, Data data){
        numberOfAnimals.getData().add(new XYChart.Data<Number,Number>(xValue,data.numberOfAnimals));
        numberOfGrass.getData().add(new XYChart.Data<Number,Number>(xValue,data.numberOfGrass));
        averageEnergy.getData().add(new XYChart.Data<Number,Number>(xValue,data.averageEnergy));
        averageLifeSpan.getData().add(new XYChart.Data<Number,Number>(xValue,data.averageLifeSpan));
        averageNumberOfChildren.getData().add(new XYChart.Data<Number,Number>(xValue,data.averageNumberOfChildren));
        XYChart.Series<String,Number> genotypeSeries = new XYChart.Series<String,Number>();
        for(int i=0;i<data.genotype.length;i++)genotypeSeries.getData().add(new XYChart.Data<String,Number>(Integer.toString(i), data.genotype[i]));
        ObservableList<XYChart.Series<String,Number>> obslist = FXCollections.observableArrayList();
        obslist.add(genotypeSeries);
        barCHartGenotype.setData(obslist);
        if(data.dominantGenom!=null) {
            XYChart.Series<String, Number> dominantGenotypeSeries = new XYChart.Series<String, Number>();
            for (int i = 0; i < data.dominantGenom.size(); i++)
                dominantGenotypeSeries.getData().add(new XYChart.Data<String, Number>(Integer.toString(i), data.dominantGenom.get(i)));
            ObservableList<XYChart.Series<String, Number>> obslist2 = FXCollections.observableArrayList();
            obslist2.add(dominantGenotypeSeries);
            barCHartDominantGenotype.setData(obslist2);
        }

    }

    @Override
    public void onStart(Data data) {
        setSeries(0,data);
        lineChartAnimalGrass.getData().add(numberOfAnimals);
        lineChartAnimalGrass.getData().add(numberOfGrass);
        lineChartEnergy.getData().add(averageEnergy);
        lineChartAverageLifeSpan.getData().add(averageLifeSpan);
        lineChartAverageNumberOfChildren.getData().add(averageNumberOfChildren);
    }

    int stepCounter=0;
    @Override
    public void onStep(Data data) {
        stepCounter++;
        System.out.println(stepCounter);
        setSeries(stepCounter, data);
    }

    void onSaveData(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            saveData(file);
        }
    }
    void saveData(File file){
        try(PrintWriter writer=new PrintWriter(file)){
            writer.printf("time;numberOfAnimals;numberofGrass;averageEnergy;averageLifeSpan;averageNumberOfChildren\n");
            double naA=0,ngA=0,aeA=0,alsA=0,anocA=0;
            int i=0;
            for(;i<numberOfAnimals.getData().size(); i++){
                int na= (int)numberOfAnimals.getData().get(i).getYValue();
                int ng= (int)numberOfGrass.getData().get(i).getYValue();
                double ae= (double)averageEnergy.getData().get(i).getYValue();
                double als= (double)averageLifeSpan.getData().get(i).getYValue();
                double anoc= (double)averageNumberOfChildren.getData().get(i).getYValue();

                naA+=na;
                ngA+=ng;
                aeA+=ae;
                alsA+=als;
                anocA+=anoc;

                writer.printf(Locale.US,"%d;%d;%d;%f;%f;%f\n",i,na,ng,ae,als,anoc);
            }
            naA/=i;
            ngA/=i;
            aeA/=i;
            alsA/=i;
            anocA/=i;
            writer.printf(Locale.US,"%d;%f;%f;%f;%f;%f\n",-1,naA,ngA,aeA,alsA,anocA);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void onMagic(int left){
        infoMagic.setText("Wykonano magic, zostało:" + left);
        infoMagic.setVisible(true);
        System.out.println("MAGIC");
    }
}
