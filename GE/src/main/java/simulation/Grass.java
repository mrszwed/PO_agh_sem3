package simulation;

public class Grass implements Cloneable {
    static double energy=4;
    public Vector2d position;
    Grass(Vector2d position){
        this.position=position;
    }
    double getEnergy(){
        return energy;
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
}
