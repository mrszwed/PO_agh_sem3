package agh.ics.oop;

import java.net.SocketOption;

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

    public static void main3(String[] args){
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }
//===================================================================   LAB3
    static void zadanie33(){
        Animal zwierze=new Animal();
        System.out.println(zwierze.toString());
    }
    static void zadanie35(){
        Animal zwierze=new Animal();
        zwierze.move(MoveDirection.RIGHT);
        System.out.println(zwierze.toString());
        zwierze.move(MoveDirection.FORWARD);
        System.out.println(zwierze.toString());
        zwierze.move(MoveDirection.FORWARD);
        System.out.println(zwierze.toString());
        zwierze.move(MoveDirection.FORWARD);
        System.out.println(zwierze.toString());
    }

    static void zadanie37(){
        String polecenia="f f b r l forward backward right right forward left backward r f f f";
        String[] tabp=polecenia.split(" ");
//        var tabkierunki=new OptionsParser().parse(tabp);  bez statica tak obowiazkowo, ze staticiem mozna ale nie trzeba
        var tabkierunki=OptionsParser.parse(tabp);
        Animal zwierze=new Animal();
        for(var i:tabkierunki){
            zwierze.move(i);
            System.out.println(zwierze.toString());
        }
    }

    /* odp na pytanie:
    *
    * utworzyć zbiór zwierząt (set) i po utworzeniu dodawac zwierzeta do zbioru
    * sprawdzać jeżeli zwierzę o takim położeniu istnieje to albo wylosować inną wartość, albo wyrzucić wyjątek
    *
    * class Animal{
    *   static set<Animal> zwierzeta=new HashSet<>();
    *   Animal(int x, int y){
    *       for(Animal a:zwierzeta){
    *           if(a.pol.x==x && a.pol.y==y)throw new OccupiedLocationException();
    *       }
    *       pol.x=x;
    *       pol.y=y;
    *   }
    * }
    *
    * */

    public static void main(String[] args){
        //zadanie33();
//        zadanie35();
        zadanie37();
    }

}
