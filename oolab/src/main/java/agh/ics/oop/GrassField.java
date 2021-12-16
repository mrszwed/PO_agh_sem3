package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap{
    List<Grass> grassClamps=new ArrayList<>();
    private MapBoundary boundary=new MapBoundary();
    public GrassField(int n){
        super((int)Math.sqrt(n*10), (int)Math.sqrt(n*10));
        placeGrass(n);
    }

    boolean placeGrass(int n) {
        Random rand=new Random();
        int placed=0;
        Set<Vector2d> occupied = new HashSet<>();
        for(int i=0; i<n; i++) {
            boolean freeSpace=false;
            do {
                Vector2d pos = new Vector2d(rand.nextInt((int) Math.sqrt(n * 10) +1), rand.nextInt((int) Math.sqrt(n * 10)+1));
                if(!occupied.contains(pos)){
                    freeSpace=true;
                    occupied.add(pos);
                    Grass g = new Grass(pos);
                    grassClamps.add(g);
                    boundary.positionChanged(null, pos);
                }
            }
            while (freeSpace==false);
        }
        return true;
    }

    @Override
    public boolean place(Animal animal){
        if(!super.place(animal))return false;
        boundary.positionChanged(null,animal.getPosition());
        return true;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object r = super.objectAt(position);
        if(r!=null)return r;
        for (Grass g : grassClamps) {
            if (g.getPosition().equals(position)) return g;
        }
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if(isOccupiedByAnimal(position))return false;
        return true;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        super.positionChanged(oldPosition, newPosition);

        for(int i=0; i<grassClamps.size(); i++){
            if(newPosition.equals(grassClamps.get(i).getPosition())){
                grassClamps.remove(i);
                boolean done=false;
                Random rand=new Random();
                while(!done){
                    Vector2d pos = new Vector2d(rand.nextInt(boundary.getMaxX()- boundary.getMinX())+ boundary.getMinX(),
                            rand.nextInt(boundary.getMaxY()- boundary.getMinY())+ boundary.getMinY());
                    if(!boundary.x.contains(pos)){
                        done=true;
                        Grass g=new Grass(pos);
                        grassClamps.add(g);
                        boundary.positionChanged(null, pos);
                    }
                }
            }
        }
    }
@Override
    public String toString(){
        MapVisualizer visualizer=new MapVisualizer(this);
        if(boundary.x.size()<1)return "mapa jest pusta";
        int last=boundary.x.size()-1;
        return visualizer.draw(new Vector2d(boundary.getMinX(),boundary.getMinY()), new Vector2d(boundary.getMaxX(), boundary.getMaxY()));
    }

    public MapBoundary getBoundary() {
        return boundary;
    }
}