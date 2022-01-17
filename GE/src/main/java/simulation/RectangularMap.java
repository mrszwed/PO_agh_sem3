package simulation;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class RectangularMap {
    private Boundary mapBoundary;
    private Boundary jungleBoundary;

    List<Animal> cemetery=new ArrayList<>();

    Map<Vector2d, List<Animal>> animals = new HashMap<>();
    Map<Vector2d, Grass> grassClumps=new HashMap<>();
    List<MapObserver> observers=new ArrayList<>();
    int maxJungleGrass=0;
    int maxSavannaGrass=0;
    int currentJungleGrass=0;
    int currentSavannaGrass=0;
    int globalTime=0;
    public boolean magic=false;
    int magicsLeft=3;
    
    RectangularMap(int height, int width, int jungleHeight, int jungleWidth){
        this.mapBoundary=new Boundary();
        mapBoundary.xmin=0;
        mapBoundary.xmax=width;
        mapBoundary.ymin=0;
        mapBoundary.ymax=height;
        this.jungleBoundary=new Boundary();
        jungleBoundary.xmin=width/2-jungleWidth/2;
        jungleBoundary.xmax=width/2+jungleWidth/2;
        jungleBoundary.ymin=height/2-jungleHeight/2;
        jungleBoundary.ymax=height/2+jungleHeight/2;
        maxJungleGrass=jungleHeight*jungleWidth;
        maxSavannaGrass=width*height-maxJungleGrass;
    }


    void placeAnimal(Animal a){
        var list=animals.get(a.position);
        if(list==null)list=new ArrayList<Animal>();
        list.add(a);
        animals.put(a.position, list);
    }

    void unplaceAnimal(Vector2d oldPos, Animal a){
        var list=animals.get(oldPos);
        if(list==null)return;
        list.remove(a);
    }

    void placeGrass(Grass grass){
        grassClumps.put(grass.position,grass);
        if(jungleBoundary.contains(grass.position))currentJungleGrass++;
        else currentSavannaGrass++;
    }
    void collectDeadAnimalsAll(){
        for(var lists:animals.values()){
            var it=lists.iterator();
            while(it.hasNext()){
                Animal a=it.next();
                if(a.isDead()){cemetery.add(a); it.remove();}
            }
        }
    }
    void applyBoundaryConstraint(Animal a, Vector2d previousPosition){}


    void moveAll(){
        List<Pair<Vector2d,Animal>> moved = new ArrayList<>();
        for(var l:animals.values()){
            for(var a:l){
                Vector2d previousPosition=a.position;
                previousPosition=a.step();
                applyBoundaryConstraint(a, previousPosition);
                if(!previousPosition.equals(a.position)){
                    moved.add(new Pair<Vector2d,Animal>(previousPosition,a));
                }
            }
        }
        for(var p:moved) {
            unplaceAnimal(p.getKey(), p.getValue());
            placeAnimal(p.getValue());
        }
    }

    void __crossCheckGrassAnimals(){    // to nie Python; tu się pisze private
        for(var g:grassClumps.entrySet()){
            if(!g.getKey().equals(g.getValue().position))System.err.println("TRAWA blad"+g.getKey()+" "+g.getValue().position);
        }
        for(var al:animals.entrySet()){
            for(var a:al.getValue()){
                if(!al.getKey().equals(a.position))System.err.println("ZWIERZE blad"+al.getKey()+" "+a.position);
            }

        }
    }

    void eatAll(){
//        __crossCheckGrassAnimals();
        for(var e:animals.entrySet()){
            if(e.getValue().size()==0)continue;
            Grass g=grassClumps.get(e.getKey());
             if(g!=null){
                e.getValue().sort((a,b)->{return -Double.compare(a.energy,b.energy);});
                int cnt=1;
                while(cnt<e.getValue().size() && e.getValue().get(0).energy==e.getValue().get(cnt).energy){
                    cnt++;
                }
                double partOfEnergy=g.getEnergy()/cnt;
                for(int i=0; i<cnt; i++){
                    e.getValue().get(i).energy+=partOfEnergy;
                }
                grassClumps.remove(g.position);
                if(jungleBoundary.contains(g.position))currentJungleGrass--;
                else currentSavannaGrass--;
//                System.out.println("Trawa zjedzona at:"+g.position+"["+grassClumps.size()+"]");
            }
        }

    }

    void crosoverringAll(){ // raczej crossingOver
        List<Animal> newAnimals=new ArrayList<>();
        for(var l:animals.values()){
            if(l.size()<2)continue;
            l.sort((a,b)->{return -Double.compare(a.energy, b.energy);});
            if(!l.get(1).canCrossover())continue;
            System.out.println("Crossover at:"+l.get(0).position);
            int j=1;
            for(; j<l.size(); j++){
                if(l.get(j).energy!=l.get(0).energy)break;
            }
            Animal newAnimal;
            if(j==1){
                newAnimal=Animal.crossover(l.get(0),l.get(1));
                newAnimal.birthDate=globalTime;
            }
            else{
                Random r=new Random();  // potencjalnie nowy obiekt co obrót pętli
                int a=r.nextInt(j);
                int b=r.nextInt(j);
                while(a==b)b=r.nextInt(j);
                newAnimal=Animal.crossover(l.get(a), l.get(b));
            }
            newAnimals.add(newAnimal);
        }
        for(var a:newAnimals){
            placeAnimal(a);
        }
    }
    
    
    
    void addGrassAll(){
        Random r=new Random();  // nowy obiekt co wywołanie
        int inJungle=(int)grassClumps.entrySet().stream().filter(e->jungleBoundary.contains(e.getKey())).count();
        if(inJungle<maxJungleGrass)addGrassToJungle(r);
        int inSavanna=grassClumps.size()-inJungle;
        if(inSavanna<maxSavannaGrass)addGrassToSavanna(r);

    }

    void addGrassToSavanna(Random r) {
        Vector2d pos;
        int cnt=0;
        do {
            int g1x=mapBoundary.xmin+ r.nextInt(mapBoundary.getWidth());
            int g1y=mapBoundary.ymin+ r.nextInt(mapBoundary.getHeight());
            pos=new Vector2d(g1x,g1y);
            cnt++;
            if(cnt==1000)break;
        }
        while(grassClumps.get(pos)!=null || jungleBoundary.contains(pos));
        Grass g2=new Grass(pos);
        grassClumps.put(g2.position, g2);

    }

    void addGrassToJungle(Random r) {
        Vector2d pos;
        int cnt=0;
        do {
            int g1x=jungleBoundary.xmin+ r.nextInt(jungleBoundary.getWidth());
            int g1y=jungleBoundary.ymin+ r.nextInt(jungleBoundary.getHeight());
            pos=new Vector2d(g1x,g1y);
            cnt++;
            if(cnt==1000)break;
        }
        while(grassClumps.get(pos)!=null);
        Grass g1=new Grass(pos);
        grassClumps.put(g1.position, g1);
    }

    void doMagic(){ // czy to na pewno jest zadanie dla mapy?

        if(!magic)return;
        if(magicsLeft==0)return;
        int ac=0;
        for(var l:animals.entrySet()){
            ac+=l.getValue().size();
        }
        if(ac!=5)return;
        List<Animal> list=getAllAnimals();
        Random r=new Random();
        for(var a:list){

            Animal b=(Animal)a.clone();
            b.energy=Animal.startEnergy;
            Vector2d pos=null;
            boolean stop=false;
            for(;!stop;){
                pos=new Vector2d(r.nextInt(getMapBoundary().getWidth()), r.nextInt(getMapBoundary().getHeight()));
                if(animals.get(pos)==null && grassClumps.get(pos)==null)stop=true;
                if(animals.get(pos)==null && this.currentSavannaGrass+currentJungleGrass==mapBoundary.getHeight()*mapBoundary.getWidth()-5)stop=true;
            }
            b.position=pos;
            placeAnimal(b);

        }
        magicsLeft--;
        fireMagic();
    }

    void mapStep(){
        collectDeadAnimalsAll();
        moveAll();
        eatAll();
        crosoverringAll();
        addGrassAll();
        fireStep();
        doMagic();
        globalTime++;
    }

    List<Animal> getAllAnimals(){
        List<Animal> list=new ArrayList<>();
        for(var l:animals.values()){
            list.addAll(l);
        }
        return list;
    }
    public List<Animal> getAnimalsAt(Vector2d pos){
        return animals.get(pos);
    }
    public List<Grass> getAllGrasses(){
        List<Grass> list=new ArrayList<>();
        for(var l:grassClumps.values()){
            list.add(l);
        }
        return list;
    }

    public Boundary getMapBoundary() {
        return mapBoundary;
    }

    public Boundary getJungleBoundary() {
        return jungleBoundary;
    }
    public List<Animal> getAnimalsWithHighestEnergy(){
        List<Animal> list=new ArrayList<>();
        for(var l:animals.values()){
            if(l.isEmpty())continue;
            l.sort((a,b)->{return -Double.compare(a.energy,b.energy);});
            list.add(l.get(0));
        }
        return list;
    }
    public void addObserver(MapObserver obs){
        observers.add(obs);
    }

    MapObserver.Data computeData(){
        MapObserver.Data data=new MapObserver.Data();
        for(var l:animals.values()){
            if(l.isEmpty())continue;
            data.numberOfAnimals+=l.size();
            for(int i=0; i<l.size(); i++){
                Animal a = l.get(i);
                data.averageEnergy+=a.energy;
                data.averageNumberOfChildren+=a.numberOfChildren;
                for(int g=0; g<a.genotyp.length; g++){
                    data.genotype[a.genotyp[g]]++;
                }
            }
        }
        if(data.numberOfAnimals!=0) {
            for (int i = 0; i < data.genotype.length; i++) {
                data.genotype[i] /= data.numberOfAnimals;
            }
            data.averageEnergy /= data.numberOfAnimals;
            data.averageNumberOfChildren /= data.numberOfAnimals;
        }
        data.numberOfGrass=grassClumps.size();
        if(!cemetery.isEmpty()) {
            for (int i = 0; i < cemetery.size(); i++) {
                data.averageLifeSpan += cemetery.get(i).lifeSpan;
            }
            data.averageLifeSpan /= cemetery.size();
        }
        data.dominantGenom=dominantGenom();
        return data;
    }

    void fireStart(){
        MapObserver.Data data=computeData();
        for(var o:observers){
            o.onStart(data);
        }
    }
    void fireStep(){
        MapObserver.Data data=computeData();
        for(var o:observers){
            o.onStep(data);
        }
    }

    void fireMagic(){
        for(var o:observers){
            o.onMagic(magicsLeft);
        }
    }

    List<Integer> dominantGenom(){  // czy to na pewno jest zadanie dla mapy?
        Map<List<Integer>, Integer> genomToCnt=new HashMap<>();
        for(var e:animals.entrySet()){
            for(var a:e.getValue()){
                List<Integer> l = Arrays.stream(a.genotyp).boxed().collect(Collectors.toList());
                Integer cnt=genomToCnt.get(l);
                if(cnt!=null)genomToCnt.put(l,cnt+1);
                else genomToCnt.put(l,1);
            }
        }
        if(genomToCnt.isEmpty())return null;
        return Collections.max(genomToCnt.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();

    }

    public List<Animal> animalsWithDominantGenotype(){
        List<Animal> toReturn=new ArrayList<>();
        List<Integer> dg=dominantGenom();
        int[] pattern=new int[32];
        for(int i=0; i<dg.size(); i++){
            pattern[i]=dg.get(i);
        }
        for(var e:animals.entrySet()){
            for(var a:e.getValue()){
                if(Arrays.equals(a.genotyp, pattern)){
                    toReturn.add(a);
                }
            }
        }
        return toReturn;
    }


}
