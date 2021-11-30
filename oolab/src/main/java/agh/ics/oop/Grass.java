package agh.ics.oop;

public class Grass {
    Vector2d position;  // to powinno być prywatne i może być finalne
    Grass(Vector2d position){   // a to publiczne
        this.position=position;
    }
    public Vector2d getPosition(){
        return position;
    }

    public String toString(){
        return "*";
    }
}
