package ge;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import simulation.Animal;
import simulation.Grass;
import simulation.RectangularMap;
import simulation.Vector2d;
import java.util.List;

public class MapPane extends Pane {
    public static final int SIZE=10;
    Group group=new Group();
    RectangularMap map;
    TrackingAnimalDialog trackingAnimalDialog;
    MapPane(RectangularMap map){
        super();
        setStyle("-fx-background-color: black;");
        setPrefSize(800,800);
        this.getChildren().add(group);
        this.map=map;
    }

    void buildView(){
        double width=getWidth();
        double height=getHeight();
        group.getChildren().clear();
        int N=20;
        int w = (int)(getWidth()/SIZE);
        int h = (int)(getHeight()/SIZE);
        List<Grass> gc=map.getAllGrasses();
        for(int i=0;i<gc.size();i++){
            int x=gc.get(i).position.x*SIZE;
            int y=gc.get(i).position.y*SIZE;;
            Rectangle r = new Rectangle(x,y,SIZE,SIZE);
            var color = Color.GREEN;
            r.setFill(color);
            r.setStroke(Color.YELLOWGREEN);
            group.getChildren().add(r);
        }
        List<Animal> al=map.getAnimalsWithHighestEnergy();
        for(int i=0;i<al.size();i++){
            int x=al.get(i).position.x*SIZE;
            int y=al.get(i).position.y*SIZE;
            Rectangle r = new Rectangle(x,y,SIZE,SIZE);
            var color = energyToColor(al.get(i).energy, Animal.startEnergy);
            r.setFill(color);
            r.setStroke(Color.BLACK);
            group.getChildren().add(r);
        }
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClick(event);
            }
        });

        if(trackingAnimalDialog !=null){
            trackingAnimalDialog.update();
            selectAnimal(trackingAnimalDialog.animal);
        }
    }

    void onClick(MouseEvent event){
        if(trackingAnimalDialog !=null)return;
        int posx = (int)(event.getX()/SIZE);
        int posy = (int)(event.getY()/SIZE);
        Vector2d pos = new Vector2d(posx,posy);
        if(!map.getMapBoundary().contains(pos))return;
        List<Animal> l = map.getAnimalsAt(pos);
        if(l==null||l.isEmpty())return;
        trackingAnimalDialog =new TrackingAnimalDialog(l.get(0));
        selectAnimal(l.get(0));
        trackingAnimalDialog.display();
        trackingAnimalDialog =null;
    }

    void setMap(RectangularMap map){
        this.map=map;
    }

    Color energyToColor(double energy, double startEnergy){
        double max=2*startEnergy;
        int r=255;
        int g=165;
        int b=0;
        if(energy>max)energy=max;
        if(energy<0)energy=0;
        if(energy>startEnergy){
            g+=(int)((energy/startEnergy-1)*(255-g));
            b+=(int)((energy/startEnergy-1)*(255-b));
        }
        else{
            r+=(int)((energy/startEnergy-1)*(128));
            g+=(int)((energy/startEnergy-1)*(g));
        }
        return Color.rgb(r,g,b);
    }

    void selectAnimal(Animal a){
        if(a.isDead())return;
        int x=a.position.x*SIZE;
        int y=a.position.y*SIZE;

        Circle circle=new Circle();
        circle.setCenterX(x+SIZE/2);
        circle.setCenterY(y+SIZE/2);
        circle.setRadius(2*SIZE);
        circle.setFill(null);
        circle.setStrokeWidth(3);
        circle.setStroke(Color.LIGHTBLUE);
        group.getChildren().add(circle);
    }

    void markWithDominantGenotype(){
        List<Animal> animalsWithDominantGenotype=map.animalsWithDominantGenotype();
        for(var a:animalsWithDominantGenotype){
            selectAnimal(a);
        }
    }

}
