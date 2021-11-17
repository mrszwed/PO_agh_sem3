package agh.ics.oop;

public class Animal {
    private MapDirection mapDir=MapDirection.NORTH;
    private Vector2d position =new Vector2d(2,2);
    private IWorldMap map;

    Animal(){}

    Animal(IWorldMap map){
        this.map=map;
    }

    Animal(IWorldMap map, Vector2d initialPosition){
        this.map=map;
        position =initialPosition;
    }

    public String toStringOld(){
        StringBuilder s=new StringBuilder();
        s.append("kierunek: ");
        s.append(mapDir.toString());
        s.append(", położenie: (");
        s.append(position.x);
        s.append(",");
        s.append(position.y);
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
        return this.position.equals(position);
    }

    Vector2d calculateNewPosition(MoveDirection direction){
        if(direction==MoveDirection.FORWARD)return position.add(mapDir.toUnitVector());
        if(direction==MoveDirection.BACKWARD)return position.subtract(mapDir.toUnitVector());
        return position;
    }

    public void move(MoveDirection direction){
        if(position ==null || map==null){
            throw new RuntimeException("zwierzę bez mapy i położenia");
        }
        Vector2d newPosition=calculateNewPosition(direction);
        if(!position.equals(newPosition) && !map.canMoveTo(newPosition))return;
        switch(direction){
            case RIGHT -> mapDir=mapDir.next();
            case LEFT -> mapDir=mapDir.previous();
            case FORWARD -> {
                position = newPosition;
                map.onPositionChanged(position);
            }
            case BACKWARD -> {
                position =newPosition;
                map.onPositionChanged(position);
            }
        }

        System.out.println(mapDir.toString());
    }

    public MapDirection getMapDir() {
        return mapDir;
    }

    public Vector2d getPosition() {
        return position;
    }
}
