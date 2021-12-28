package simulation;

import java.util.Arrays;
import java.util.Random;

public class Animal implements Cloneable {
    public static double startEnergy=100;
    static double minEnergyToCrossover=50;
    static double lifeThreashold = 0;
    static double moveEnergy =5;
    public Vector2d position;
    public double energy;
    Orientation orientation=new Orientation();
    public int [] genotyp=new int[32];
    public int lifeSpan=0;
    public int birthDate=0;

    Animal parent1;
    Animal parent2;
    public int numberOfChildren=0;
    public int numberOfOffsprings=0;

    static Random r=new Random();


    Animal(Vector2d position, int[] genotyp){
        this.position=position;
        this.genotyp=genotyp;   //kierunek losowy dac
        energy=startEnergy;
    }

    Animal(Vector2d position){
        this.position=position;     //kierunek losowy
        for(int i=0; i<32; i++){
            genotyp[i]=r.nextInt(8);
        }
        Arrays.sort(genotyp);
        energy=startEnergy;
    }

    Vector2d step(){
        Vector2d previousPostition=position;
        if(energy<=lifeThreashold)return previousPostition;
        int dir=genotyp[r.nextInt(32)];
        switch (dir){
            case 0: position=position.add(orientation.toUnitVector()); break;
            case 4: position=position.add(orientation.toUnitVector().opposite()); break;
            default: orientation.rotate(dir);
        }
        lifeSpan++;
        energy-= moveEnergy;
        return previousPostition;
    }

    boolean canCrossover(){
        return energy>minEnergyToCrossover;
    }

    static Animal crossover(Animal a1, Animal a2){
        Animal newAnimal = new Animal(a1.position);

        boolean fromLeft= r.nextBoolean();
        double se=a1.energy+a2.energy;
        double paW=(a1.energy>a2.energy?a1.energy:a2.energy)/se;
        if(!fromLeft)paW=1-paW;
        int punktPodzialu=(int)(paW*32);
        for(int i=0; i<=punktPodzialu; i++){
            newAnimal.genotyp[i]=a1.energy>a2.energy? a1.genotyp[i] : a2.genotyp[i];
        }
        for(int i=punktPodzialu+1; i<32; i++){
            newAnimal.genotyp[i]=a1.energy<a2.energy? a1.genotyp[i] : a2.genotyp[i];
        }
        newAnimal.energy=0.25*a1.energy+0.25*a2.energy;
        newAnimal.orientation.rotate(r.nextInt(8));
        a1.energy*=0.75;
        a2.energy*=0.75;

        a1.incrementNumberOfChildren();
        a2.incrementNumberOfChildren();
        a1.incrementNumberOfOffsprings();
        a2.incrementNumberOfOffsprings();

        Arrays.sort(newAnimal.genotyp);
        return newAnimal;
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Position: ");
        sb.append(position.toString());
        sb.append(" Energy: ");
        sb.append(energy);
        sb.append(" Time: ");
        sb.append(lifeSpan);

        sb.append(" Genotyp: ");
        sb.append("[");
        for(int i=0; i<32; i++){
            if(i!=0)sb.append(" ");
            sb.append(genotyp[i]);
        }
        sb.append("]");
        return sb.toString();
    }
    public boolean isDead(){
        return energy<=lifeThreashold;
    }
    @Override
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    void incrementNumberOfChildren(){
        numberOfChildren++;
    }

    void incrementNumberOfOffsprings(){
        numberOfOffsprings++;
        if(parent1!=null)parent1.incrementNumberOfOffsprings();
        if(parent2!=null)parent2.incrementNumberOfOffsprings();
    }
    public String genotypToString(){
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for(int i=0; i<32; i++){
            if(i!=0)sb.append(" ");
            sb.append(genotyp[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
