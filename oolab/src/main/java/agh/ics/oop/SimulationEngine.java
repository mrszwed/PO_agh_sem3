package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine{
        private MoveDirection[] tabMove;
        private IWorldMap map;

        private List<Animal> zwierzeta=new ArrayList<>();

    SimulationEngine(MoveDirection[] tabMove, IWorldMap map, Vector2d[] vectPos){
        this.tabMove=tabMove;
        this.map=map;
        for(var i:vectPos){
            Animal zwierze=new Animal(map, i);
            if(map.place(zwierze))zwierzeta.add(zwierze);
        }
    }


    @Override
    public void run() {
//        RectangularMap rMap;
//        if(map instanceof RectangularMap)rMap=(RectangularMap)map;
//        else throw new RuntimeException("mapa nie zapewnia dostępu do zwierząt");
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
