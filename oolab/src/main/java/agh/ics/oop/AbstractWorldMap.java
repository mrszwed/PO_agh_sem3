package agh.ics.oop;

import java.util.HashMap;

abstract public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{

    protected HashMap<Vector2d,Animal> animals = new HashMap<>();

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

//    @Override
    public boolean placeOld(Animal animal){
        if(!canMoveTo(animal.getPosition()))return false;
        animals.put(animal.getPosition(), animal);
        return true;
    }

    public boolean place(Animal animal){
        if(!canMoveTo(animal.getPosition()))throw new IllegalArgumentException("błędne pole" + animal.getPosition());
        animals.put(animal.getPosition(), animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position){
        if(objectAt(position)==null)return false;
        return true;
    }

    @Override
    public Object objectAt(Vector2d position){
        return animals.get(position);
    }

    protected boolean isOccupiedByAnimal(Vector2d position) {
        if (animals.get(position)!=null) return true;
        return false;
    }

    public String toString(){
        MapVisualizer visualizer=new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0,0), new Vector2d(width-1, height-1));
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        animals.put(newPosition, animals.get(oldPosition));
        animals.remove(oldPosition);

    }
}
