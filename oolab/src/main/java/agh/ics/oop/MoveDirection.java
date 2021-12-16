package agh.ics.oop;

public enum MoveDirection {
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT;
    public String toString(){
        return switch(this){
            case FORWARD -> "FORWARD";
            case BACKWARD -> "BACKWARD";
            case RIGHT -> "RIGHT";
            case LEFT -> "LEFT";
        };
    }
}
