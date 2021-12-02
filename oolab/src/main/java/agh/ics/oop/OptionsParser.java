package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionsParser {

    static Map<String,MoveDirection> slownik=new HashMap<>();
    static{
        slownik.put("f",MoveDirection.FORWARD);
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
            if(kierunek==null){
                throw new IllegalArgumentException(s+" is not legal move specification");
            }
            listakierunkow.add(kierunek);
        }
        MoveDirection[] kierunki=new MoveDirection[listakierunkow.size()];
        return listakierunkow.toArray(kierunki);
    }
}
