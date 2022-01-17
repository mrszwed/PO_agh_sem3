package simulation;

import java.util.List;
import java.util.Map;

public interface MapObserver {  // IMapObserver
    public static class Data{
        public int numberOfAnimals;
        public int numberOfGrass;
        public double[] genotype=new double[8];
        public double averageEnergy;
        public double averageLifeSpan;
        public double averageNumberOfChildren;
        public List<Integer> dominantGenom;
    }
    void onStart(Data data);
    void onStep(Data data);
    void onMagic(int left);
}
