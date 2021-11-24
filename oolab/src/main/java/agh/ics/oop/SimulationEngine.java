package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine{
        private MoveDirection[] tabMove;
        private IWorldMap map;

        private List<Animal> zwierzeta=new ArrayList<>();

    SimulationEngine(MoveDirection[] tabMove, IWorldMap map,  Vector2d[] vectPos, IPositionChangeObserver observer){
        this.tabMove=tabMove;
        this.map=map;
        for(var i:vectPos){
            Animal zwierze=new Animal(map, i);
            if(observer!=null)zwierze.addObserver(observer);
            if(map.place(zwierze))zwierzeta.add(zwierze);
        }
    }

    SimulationEngine(MoveDirection[] tabMove, IWorldMap map,  Vector2d[] vectPos){
        this(tabMove,map,vectPos,null);
    }



    @Override
    public void run() {
        for(int i=0; i<tabMove.length; i++){
            if(i%zwierzeta.size()==0){
                System.out.println(map.toString());
            }
            int zwierzeIdx=i%zwierzeta.size();
            zwierzeta.get(zwierzeIdx).move(tabMove[i]);
        }
        System.out.println(map.toString());
    }
}
