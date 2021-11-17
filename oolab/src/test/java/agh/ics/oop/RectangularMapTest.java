package agh.ics.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {
    int n=100;
    RectangularMap map = new RectangularMap(n,n);
    Set<Animal> animals= new HashSet();
    Set<Vector2d> positions = new HashSet<>();

    @BeforeEach
    void setUp() {
        Random rand = new Random();
        for(int i=0 ; i<3000; i++){
            Vector2d pos = new Vector2d(rand.nextInt((int) Math.sqrt(n * 10) +1), rand.nextInt((int) Math.sqrt(n * 10)+1));
            positions.add(pos);
            Animal animal = new Animal(map, pos);
            animals.add(animal);
        }
    }

    @Test
    void canMoveTo() {
        for(var a:animals){
            map.place(a);
        }
        for(var p:positions){
            assertFalse(map.canMoveTo(p));
        }
    }

    @Test
    void place() {
        for(var a:animals){
            boolean zajete=map.isOccupied(a.getPosition());
            assertEquals(map.place(a), !zajete);
        }
    }


    @Test
    void isOccupied() {
        for(var a:animals){
            map.place(a);
        }
        for(var i:positions){
            assertTrue(map.isOccupied(i));
        }
    }
}