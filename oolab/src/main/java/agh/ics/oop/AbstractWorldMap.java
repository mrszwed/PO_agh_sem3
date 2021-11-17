package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractWorldMap implements IWorldMap{

    private List<Animal> zwierzeta=new ArrayList<>();
    protected int width;
    protected int height;
    AbstractWorldMap(int width, int height){
        this.width=width;
        this.height=height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if(position.x<0 || position.x>=width)return false;
        if(position.y<0 || position.y>=height)return false;
        if(isOccupiedByAnimal(position))return false;
        return true;
    }



    @Override
    public boolean place(Animal animal){
        if(!canMoveTo(animal.getPosition()))return false;
        zwierzeta.add(animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position){
        if(objectAt(position)==null)return false;
        return true;
    }

    @Override
    public Object objectAt(Vector2d position){
        for (Animal a : zwierzeta) {
            if (a.getPosition().equals(position)) return a;
        }
        return null;
    }

    private boolean isOccupiedByAnimal(Vector2d position) {
        for (Animal a : zwierzeta) {
            if (a.getPosition().equals(position)) return true;
        }
        return false;
    }

    Object animalAt(Vector2d position){
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
