package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {

    @Test
    void place() {
        try {
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4), new Vector2d(2, 2)};
            AbstractWorldMap map = new AbstractWorldMap(10, 10) {
            };   //anonimowa klasa dziedzicząca po abstract map
            map.place(new Animal(map, positions[0]));
            map.place(new Animal(map, positions[1]));
            map.place(new Animal(map, positions[2]));
            fail("powinien być wyjątek");
        }
        catch(IllegalArgumentException e){e.printStackTrace();}

    }
}