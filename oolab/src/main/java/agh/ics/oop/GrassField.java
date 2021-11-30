package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap{
    List<Grass> grassClamps=new ArrayList<>();  // private
    GrassField(int n){  // public
        super((int)Math.sqrt(n*10), (int)Math.sqrt(n*10));
        placeGrass(n);
    }

    boolean placeGrass(int n) { // private
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
    public Object objectAt(Vector2d position) {
        Object r = super.objectAt(position);
        if(r!=null)return r;
        for (Grass g : grassClamps) {
            if (g.getPosition().equals(position)) return g;
        }
        return null;
    }

    @Override
    public void onPositionChanged(Vector2d nPos){
        for(int i=0; i<grassClamps.size(); i++){
            if(nPos.equals(grassClamps.get(i).getPosition()))grassClamps.remove(i);
        }
    }

}
