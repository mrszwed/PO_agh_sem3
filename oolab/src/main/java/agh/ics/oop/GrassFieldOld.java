package agh.ics.oop;

import java.util.*;

public class GrassFieldOld implements IWorldMap{
    int n;
    private List<Animal> zwierzeta=new ArrayList<>();
    List<Grass> grassClamps=new ArrayList<>();
    GrassFieldOld(int n){
        this.n=n;
        placeGrass(n);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if(position.x<0 || position.x>=(int)Math.sqrt(n*10))return false;
        if(position.y<0 || position.y>=(int)Math.sqrt(n*10))return false;
        if(isOccupied(position))return false;
        return true;
    }

    @Override
    public boolean place(Animal animal){
        if(!canMoveTo(animal.getPosition()))return false;
        zwierzeta.add(animal);
        return true;
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
                }
            }
            while (freeSpace==false);
        }
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position){
        if(objectAt(position)==null)return false;
        return true;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal a : zwierzeta) {
            if (a.getPosition().equals(position)) return a;
        }
        for (Grass g : grassClamps) {
            if (g.getPosition().equals(position)) return g;
        }
        return null;
    }

    public String toString() {
        MapVisualizer visualizer=new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0,0), new Vector2d((int)Math.sqrt(n*10), (int)Math.sqrt(n*10)));
    }
}
