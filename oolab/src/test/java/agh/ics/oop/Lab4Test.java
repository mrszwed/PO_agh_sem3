package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Lab4Test {
    @Test
    void testSymulacja1(){
        RectangularMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };

        for(var i:positions){
            Animal zwierze=new Animal(map, i);
            map.place(zwierze);
        }
        String expected=" y\\x  0 1 2 3 4 5 6 7 8 910" +System.lineSeparator()+
                "  6: -----------------------" +System.lineSeparator()+
                "  5: | | | | | | | | | | | |" +System.lineSeparator()+
                "  4: | | | |^| | | | | | | |" +System.lineSeparator()+
                "  3: | | | | | | | | | | | |" +System.lineSeparator()+
                "  2: | | |^| | | | | | | | |" +System.lineSeparator()+
                "  1: | | | | | | | | | | | |" +System.lineSeparator()+
                "  0: | | | | | | | | | | | |" +System.lineSeparator()+
                " -1: -----------------------" +System.lineSeparator();
        String actual=map.toString();
//        assertEquals(actual.length(), expected.length());
        assertEquals(expected, actual);

    }

    @Test
    void testSymulacja2(){
        RectangularMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(0,0)};

        String polecenia="f f f r f f l b b l";
        String[] args=polecenia.split(" ");
        MoveDirection[] directions = new OptionsParser().parse(args);

        for(var i:positions){
            Animal zwierze=new Animal(map, i);
            map.place(zwierze);
        }
        String expected=" y\\x  0 1 2 3 4 5 6 7 8 910" +System.lineSeparator()+
                "  6: -----------------------" +System.lineSeparator()+
                "  5: | | | | | | | | | | | |" +System.lineSeparator()+
                "  4: | | | | | | | | | | | |" +System.lineSeparator()+
                "  3: | | | | | | | | | | | |" +System.lineSeparator()+
                "  2: | | | | | | | | | | | |" +System.lineSeparator()+
                "  1: | | | | | | | | | | | |" +System.lineSeparator()+
                "  0: |^| | | | | | | | | | |" +System.lineSeparator()+
                " -1: -----------------------" +System.lineSeparator();
        String actual=map.toString();
        assertEquals(expected, actual);

        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        expected=" y\\x  0 1 2 3 4 5 6 7 8 910" +System.lineSeparator()+
                "  6: -----------------------" +System.lineSeparator()+
                "  5: | | | | | | | | | | | |" +System.lineSeparator()+
                "  4: | | | | | | | | | | | |" +System.lineSeparator()+
                "  3: | | | | | | | | | | | |" +System.lineSeparator()+
                "  2: | | | | | | | | | | | |" +System.lineSeparator()+
                "  1: | | |<| | | | | | | | |" +System.lineSeparator()+
                "  0: | | | | | | | | | | | |" +System.lineSeparator()+
                " -1: -----------------------" +System.lineSeparator();
        actual=map.toString();
        assertEquals(expected, actual);
    }

}
