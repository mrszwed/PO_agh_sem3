package agh.ics.oop;

public class Animal {
    private MapDirection mapDir=MapDirection.NORTH;
    private Vector2d pol=new Vector2d(2,2);
    private IWorldMap map;
//    public Animal(){
//        mapDir=MapDirection.NORTH;
//        moveDir=MoveDirection.FORWARD;
//        pol=new Vector2d(2,2);
//    }

    Animal(){}

    Animal(IWorldMap map){
        this.map=map;
    }

    Animal(IWorldMap map, Vector2d initialPosition){
        this.map=map;
        pol=initialPosition;
    }

    public String toStringOld(){
        StringBuilder s=new StringBuilder();
        s.append("kierunek: ");
        s.append(mapDir.toString());
        s.append(", położenie: (");
        s.append(pol.x);
        s.append(",");
        s.append(pol.y);
        s.append(")");
        return s.toString();
    }

    public String toString(){
        StringBuilder s=new StringBuilder();
        switch(this.mapDir){
            case NORTH -> s.append("^");
            case EAST -> s.append(">");
            case SOUTH -> s.append("v");
            case WEST -> s.append("<");
        }
        return s.toString();
    }

    boolean isAt(Vector2d position){
        return pol.equals(position);
    }

    /*public boolean czyWyjedzie(MoveDirection direction){
        switch(direction){
            case FORWARD -> {
                if(mapDir==MapDirection.NORTH && pol.y==4)return true;
                if(mapDir==MapDirection.SOUTH && pol.y==0)return true;
                if(mapDir==MapDirection.EAST && pol.x==4)return true;
                if(mapDir==MapDirection.WEST && pol.x==0)return true;
            }
            case BACKWARD -> {
                if(mapDir==MapDirection.NORTH && pol.y==0)return true;
                if(mapDir==MapDirection.SOUTH && pol.y==4)return true;
                if(mapDir==MapDirection.EAST && pol.x==0)return true;
                if(mapDir==MapDirection.WEST && pol.x==4)return true;
            }
        }
        return false;
    }*/

    Vector2d calculateNewPosition(MoveDirection direction){
        if(direction==MoveDirection.FORWARD)return pol.add(mapDir.toUnitVector());
        if(direction==MoveDirection.BACKWARD)return pol.subtract(mapDir.toUnitVector());
        return pol;
    }

    public void move(MoveDirection direction){
        if(pol==null || map==null){
            throw new RuntimeException("zwierzę bez mapy i położenia");
        }
        Vector2d newPosition=calculateNewPosition(direction);
        if(!pol.equals(newPosition) && !map.canMoveTo(newPosition))return;
        switch(direction){
            case RIGHT -> mapDir=mapDir.next();
            case LEFT -> mapDir=mapDir.previous();
            case FORWARD -> pol=newPosition;
            case BACKWARD -> pol=newPosition;
        }

        System.out.println(mapDir.toString());
    }

    public MapDirection getMapDir() {
        return mapDir;
    }

    public Vector2d getPol() {
        return pol;
    }
}
