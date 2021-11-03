package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine{
        MoveDirection[] tabMove;
        IWorldMap map;
        Vector2d[] vectPos;

    SimulationEngine(MoveDirection[] tabMove, IWorldMap map, Vector2d[] vectPos){
        this.tabMove=tabMove;
        this.map=map;
        this.vectPos=vectPos;
        for(var i:vectPos){
            Animal zwierze=new Animal(map, i);
            map.place(zwierze);
        }
    }


    @Override
    public void run() {
        RectangularMap rMap;
        if(map instanceof RectangularMap)rMap=(RectangularMap)map;
        else throw new RuntimeException("mapa nie zapewnia dostępu do zwierząt");
        for(int i=0; i<tabMove.length; i++){
            if(i%rMap.zwierzeta.size()==0){
                System.out.println(map.toString());
            }
            int zwierzeIdx=i%rMap.zwierzeta.size();
            rMap.zwierzeta.get(zwierzeIdx).move(tabMove[i]);
        }
        System.out.println(map.toString());
    }
}
