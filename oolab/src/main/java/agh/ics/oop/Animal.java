package agh.ics.oop;

public class Animal {
    private MapDirection mapDir=MapDirection.NORTH;
    private MoveDirection moveDir=MoveDirection.FORWARD;
    private Vector2d pol=new Vector2d(2,2);
//    public Animal(){
//        mapDir=MapDirection.NORTH;
//        moveDir=MoveDirection.FORWARD;
//        pol=new Vector2d(2,2);
//    }

    public String toString(){
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

    boolean isAt(Vector2d position){
        return pol.equals(position);
    }

    public boolean czyWyjedzie(MoveDirection direction){
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
    }

    public void move(MoveDirection direction){
        if(czyWyjedzie(direction))return;
        switch(direction){
            case RIGHT -> {mapDir=mapDir.next(); moveDir=MoveDirection.FORWARD;}
            case LEFT -> {mapDir=mapDir.previous(); moveDir=MoveDirection.FORWARD;}
            case FORWARD -> pol=pol.add(mapDir.toUnitVector());
            case BACKWARD -> pol=pol.subtract(mapDir.toUnitVector());
        }
        System.out.println(mapDir.toString());
    }

    public MapDirection getMapDir() {
        return mapDir;
    }

    public MoveDirection getMoveDir() {
        return moveDir;
    }

    public Vector2d getPol() {
        return pol;
    }
}
