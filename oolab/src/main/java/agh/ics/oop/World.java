package agh.ics.oop;

public class World {
    public static void mainlab1(String[] args) {
        System.out.println("system wystartowal");
//        run(new String[]{"f","b","r","l"});
//        run(new Direction[]{Direction.FORWARD, Direction.RIGHT, Direction.BACKWARD, Direction.LEFT});
        run2(args);
        System.out.println("system zakonczyl dzialanie");
    }

    public static void run_1(String[] tab){
        System.out.println("start");
        for(int i=0; i<tab.length; i++){
            if(i!=0)System.out.print(", ");
            System.out.print(tab[i]);
        }
        System.out.println();
        System.out.println("stop");
    }

    public static void run2(String[] tab){
        System.out.println("start");
        for(var s:tab){
            String message = switch(s){
                case "f" -> "do przodu";
                case "b" -> "do tyłu";
                case "r" -> "w prawo";
                case "l" -> "w lewo";
                default -> "Nieznana komenda";
            };

            System.out.printf("Zwierzak idzie %s\n",message);
        }
        System.out.println("stop");
    }

    static enum Direction{
        FORWARD,
        BACKWARD,
        RIGHT,
        LEFT
    }

    public static void run(Direction[] tab){
        System.out.println("start");
        for(var s:tab){
            String message = switch(s){
                case FORWARD -> "do przodu";
                case BACKWARD -> "do tyłu";
                case RIGHT -> "w prawo";
                case LEFT -> "w lewo";
            };

            System.out.printf("Zwierzak idzie %s\n",message);
        }
        System.out.println("stop");
    }

//==================================================================    LAB2

    public static void main(String[] args){
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }

}
