package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;


public class RectangularMap implements IWorldMap{
    List<Animal> zwierzeta=new ArrayList<>();
    int width;
    int height;
    RectangularMap(int width, int height){
        this.width=width;
        this.height=height;
    }

    public boolean canMoveTo(Vector2d position) {
        if(position.x<0 || position.x>width)return false;
        if(position.y<0 || position.y>height)return false;
        if(isOccupied(position))return false;
        return true;
    }
    public boolean place(Animal animal){
        if(!canMoveTo(animal.getPol()))return false;
        zwierzeta.add(animal);
        return true;
    }
    public boolean isOccupied(Vector2d position){
        for (Animal a : zwierzeta) {
            if (a.getPol().x == position.x && a.getPol().y == position.y) return true;
        }
        return false;
    }
    public Object objectAt(Vector2d position){
        for (Animal a : zwierzeta) {
            if (a.getPol().x == position.x && a.getPol().y == position.y) return a;
        }
        return null;
    }

    public String toString(){
        MapVisualizer visualizer=new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0,0), new Vector2d(width, height));
    }

}
