package agh.ics.oop;

import agh.ics.oop.gui.App;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SimulationEngine implements IEngine, Runnable{
        private MoveDirection[] tabMove;
        private IWorldMap map;
        private int moveDelay=300;
        private List<Animal> zwierzeta=new ArrayList<>();

    public SimulationEngine(MoveDirection[] tabMove, IWorldMap map,  Vector2d[] vectPos, List<IPositionChangeObserver> observers){
        this.tabMove=tabMove;
        this.map=map;
        for(var i:vectPos){
            Animal zwierze=new Animal(map, i);

            if(map.place(zwierze)){
                zwierzeta.add(zwierze);
                for(var o:observers)zwierze.addObserver(o);

            }
        }
    }

    public SimulationEngine(MoveDirection[] tabMove, IWorldMap map,  Vector2d[] vectPos){
        this(tabMove,map,vectPos,null);
    }



//    @Override
//    public void run() {
//        for(int i=0; i<tabMove.length; i++){
//            if(i%zwierzeta.size()==0){
//                System.out.println(map.toString());
//            }
//            int zwierzeIdx=i%zwierzeta.size();
//            zwierzeta.get(zwierzeIdx).move(tabMove[i]);
//        }
//        System.out.println(map.toString());
//    }

    boolean stop=false;

    public void stop(){
        this.stop=true;
    }

    @Override
    public void run() {
        stop=false;
        commandIdx=0;
        while(!stop){
            if(!step())break;
            try {
                sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<ISimulationObserver> simulationObservers=new ArrayList<>();

    public void addSimulationObserver(ISimulationObserver o){
        simulationObservers.add(o);
    }

    int commandIdx=0;

    public boolean step() {
        if(commandIdx>= tabMove.length)return false;

        int zwierzeIdx=commandIdx%zwierzeta.size();
        Vector2d oldpos = zwierzeta.get(zwierzeIdx).getPosition();
        MapDirection orientation = zwierzeta.get(zwierzeIdx).getMapDir();
        MoveDirection moveDirection = tabMove[commandIdx];
        zwierzeta.get(zwierzeIdx).move(tabMove[commandIdx]);


        commandIdx++;
        String message = String.format("Animal #%d at (%s,%s) executed %s: ",zwierzeIdx,oldpos,orientation,moveDirection);
        for(var o:simulationObservers){
            o.stepDone(message);
        }
        return true;
    }
    public boolean atEnd(){
        return commandIdx>= tabMove.length;
    }

    public void reset(){
        commandIdx=0;
    }

    public void setMoveDelay(int moveDelay) {
        this.moveDelay = moveDelay;
    }
}
