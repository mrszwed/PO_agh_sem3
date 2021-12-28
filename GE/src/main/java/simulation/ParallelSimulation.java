package simulation;

import java.util.List;
import java.util.Random;

public class ParallelSimulation {
    NonWrappableMap nwMap;
    WrappableMap wMap;
    int width=80;
    int height=60;
    double startEnergy=100;
    double moveEnergy=5;
    double plantEnergy=40;
    double jungleRatio=0.2;
    int initialNumberOfAnimals=50;
    double jungleGrassDensity=0.65;
    double savannahGrassDensity=0.1;
    boolean wMagic=false;
    boolean nwMagic=false;


    public ParallelSimulation(){

    }

    public ParallelSimulation size(int width, int height, double jungleRatio){
        this.width=width;
        this.height=height;
        this.jungleRatio=jungleRatio;
        return this;
    }
    public ParallelSimulation energy(double startEnergy, double moveEnergy, double plantEnergy){
        this.startEnergy=startEnergy;
        this.moveEnergy=moveEnergy;
        this.plantEnergy=plantEnergy;
        return this;
    }
    public ParallelSimulation densities(double jungleGrassDensity, double savannahGrassDensity){
        this.jungleGrassDensity=jungleGrassDensity;
        this.savannahGrassDensity=savannahGrassDensity;
        return this;
    }
    public ParallelSimulation initialNumberOfAnimals(int initialNumberOfAnimals){
        this.initialNumberOfAnimals=initialNumberOfAnimals;
        return this;
    }

    public ParallelSimulation magic(boolean nwMagic, boolean wMagic){
        this.nwMagic=nwMagic;
        this.wMagic=wMagic;
        return this;
    }

    public ParallelSimulation setup(){
        int jw=(int)(width*jungleRatio);
        int jh=(int)(height*jungleRatio);
        nwMap=new NonWrappableMap(height,width,jh,jw);
        wMap=new WrappableMap(height,width,jh,jw);
        Animal.moveEnergy=moveEnergy;
        Animal.startEnergy=startEnergy;
        Grass.energy=plantEnergy;

        setup(wMap);
        copyObjects(wMap, nwMap);
        wMap.magic=this.wMagic;
        nwMap.magic=this.nwMagic;
        return this;
    }

    public void fireStartEvent(){
        nwMap.fireStart();
        wMap.fireStart();
    }

    void setup(RectangularMap rMap){
        Random r=new Random();
        int numberOfGrassToJungle=(int)(rMap.getJungleBoundary().getWidth()* rMap.getJungleBoundary().getHeight()*jungleGrassDensity);
        int numberofGrassToSavannah=(int)((rMap.getMapBoundary().getHeight()* rMap.getMapBoundary().getWidth()- rMap.getJungleBoundary().getWidth()* rMap.getJungleBoundary().getHeight())*savannahGrassDensity);
        for(int i=0; i<numberOfGrassToJungle; i++)rMap.addGrassToJungle(r);
        for(int i=0; i<numberofGrassToSavannah; i++)rMap.addGrassToSavanna(r);

//        System.err.println(rMap.getMapBoundary());
        for(int i=0; i<initialNumberOfAnimals; i++){
//            if(rMap.getMapBoundary().getWidth()<=0|| rMap.getMapBoundary().getHeight()<=0){
//                System.err.println("---------------");
//                System.err.println(rMap.getMapBoundary());
//            }

            Animal animal=new Animal(new Vector2d(r.nextInt(rMap.getMapBoundary().getWidth()), r.nextInt(rMap.getMapBoundary().getHeight())));
            rMap.placeAnimal(animal);
        }
    }

    void copyObjects(RectangularMap source, RectangularMap target){
        List<Animal> al=source.getAllAnimals();
        List<Grass> gl=source.getAllGrasses();
        for(int i=0; i<al.size(); i++){
            Animal a=(Animal)al.get(i).clone();
            target.placeAnimal(a);
        }
        for(int i=0; i<gl.size(); i++){
            Grass g=(Grass)gl.get(i).clone();
            target.placeGrass(g);
        }
    }

    public void simulationStep(){
        nwMap.mapStep();
        wMap.mapStep();
    }

    public RectangularMap getWMap(){
        return wMap;
    }
    public RectangularMap getNwMap(){
        return nwMap;
    }
}
