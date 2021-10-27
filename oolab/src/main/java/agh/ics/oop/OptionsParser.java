package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionsParser {

    static Map<String,MoveDirection> slownik=new HashMap<>(); //pole statyczne dzielone przez wszystkie obiekty danej klasy
    static{                                                   //inicjalizacja pol statycznych wykonywana jest jednokrotnie
        slownik.put("f",MoveDirection.FORWARD);               //przy tworzeniu pierwszego obiektu, lub uzyciu metody/atrybutu statycznego
        slownik.put("forward",MoveDirection.FORWARD);
        slownik.put("b",MoveDirection.BACKWARD);
        slownik.put("backward",MoveDirection.BACKWARD);
        slownik.put("r",MoveDirection.RIGHT);
        slownik.put("right",MoveDirection.RIGHT);
        slownik.put("l",MoveDirection.LEFT);
        slownik.put("left",MoveDirection.LEFT);
    }

    static public MoveDirection[] parse(String[] tabs){
        List<MoveDirection> listakierunkow=new ArrayList<>();
        for(String s:tabs){
            var kierunek=slownik.get(s);
            if(kierunek==null)continue;
            listakierunkow.add(kierunek);
        }
        MoveDirection[] kierunki=new MoveDirection[listakierunkow.size()];
        return listakierunkow.toArray(kierunki);
    }
}
