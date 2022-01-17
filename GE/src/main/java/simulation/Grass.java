package simulation;

public class Grass implements Cloneable {
    static double energy=4; // czemu 4?
    public Vector2d position;   // można teleportować trawę
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
            e.printStackTrace();    // zamiana wyjątku na komunikat w konsoli jest mało opłacalna, zwłaszcza w aplikacji z GUI
            return null;
        }
    }
}
