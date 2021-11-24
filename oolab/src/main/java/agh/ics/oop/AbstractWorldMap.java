package agh.ics.oop;

import java.util.HashMap;

abstract public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{

    HashMap<Vector2d,Animal> zwierzeta = new HashMap<>();

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
        zwierzeta.put(animal.getPosition(), animal);
        return true;
    }


    @Override
    public boolean isOccupied(Vector2d position){
        if(objectAt(position)==null)return false;
        return true;
    }

    @Override
    public Object objectAt(Vector2d position){
        return zwierzeta.get(position);
    }

    private boolean isOccupiedByAnimal(Vector2d position) {
        if (zwierzeta.get(position)!=null) return true;
        return false;
    }

    public String toString(){
        MapVisualizer visualizer=new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0,0), new Vector2d(width-1, height-1));
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        zwierzeta.put(newPosition, zwierzeta.get(oldPosition));
        System.out.println(oldPosition+"->"+newPosition);
        zwierzeta.remove(oldPosition);
    }
}
