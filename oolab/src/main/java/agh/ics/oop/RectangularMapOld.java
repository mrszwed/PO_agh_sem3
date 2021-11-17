package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;


public class RectangularMapOld implements IWorldMap{
    private List<Animal> zwierzeta=new ArrayList<>();
    private int width;
    private int height;
    RectangularMapOld(int width, int height){
        this.width=width;
        this.height=height;
    }

    public boolean canMoveTo(Vector2d position) {
        if(position.x<0 || position.x>=width)return false;
        if(position.y<0 || position.y>=height)return false;
        if(isOccupied(position))return false;
        return true;
    }
    public boolean place(Animal animal){
        if(!canMoveTo(animal.getPosition()))return false;
        zwierzeta.add(animal);
        return true;
    }
    public boolean isOccupied(Vector2d position){
        if(objectAt(position)==null)return false;
        return true;
    }   ////////////<--------------
    public Object objectAt(Vector2d position){
        for (Animal a : zwierzeta) {
            if (a.getPosition().equals(position)) return a;
        }
        return null;
    }

    public String toString(){
        MapVisualizer visualizer=new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0,0), new Vector2d(width-1, height-1));
    }

}
