package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Lab3Test {
    @Test
    void testOrientation(){
        Animal zwierze=new Animal(new RectangularMap(5,5));
        assertEquals(zwierze.getMapDir(), MapDirection.NORTH);
        zwierze.move(MoveDirection.RIGHT);
        assertEquals(zwierze.getMapDir(), MapDirection.EAST);
        zwierze.move(MoveDirection.RIGHT);
        assertEquals(zwierze.getMapDir(), MapDirection.SOUTH);
        zwierze.move(MoveDirection.RIGHT);
        assertEquals(zwierze.getMapDir(), MapDirection.WEST);
        zwierze.move(MoveDirection.RIGHT);
        assertEquals(zwierze.getMapDir(), MapDirection.NORTH);
    }

    @Test
    void testPosition(){
        Animal zwierze=new Animal(new RectangularMap(5,5));
        zwierze.move(MoveDirection.RIGHT);
        zwierze.move(MoveDirection.FORWARD);
        assertEquals(zwierze.getPosition(), new Vector2d(3,2));
        zwierze.move(MoveDirection.RIGHT);
        zwierze.move(MoveDirection.FORWARD);
        assertEquals(zwierze.getPosition(), new Vector2d(3,1));
        zwierze.move(MoveDirection.FORWARD);
        assertEquals(zwierze.getPosition(), new Vector2d(3,0));

        Animal zwierze1=new Animal(new RectangularMap(5,5));
        String polecenia1="b b l b";
        String[] tabp1=polecenia1.split(" ");
        var tabkierunki1=OptionsParser.parse(tabp1);
        for(var i:tabkierunki1){
            zwierze1.move(i);
        }
        assertEquals(zwierze1.getPosition(), new Vector2d(3,0));

        Animal zwierze2=new Animal(new RectangularMap(5,5));
        String polecenia2="l f f l b";
        String[] tabp2=polecenia2.split(" ");
        var tabkierunki2=OptionsParser.parse(tabp2);
        for(var i:tabkierunki2){
            zwierze2.move(i);
        }
        assertEquals(zwierze2.getPosition(), new Vector2d(0,3));
    }

    @Test
    void testMapBorder(){
        Animal zwierze=new Animal(new RectangularMap(5,5));
        zwierze.move(MoveDirection.FORWARD);
        for(int i=0; i<10; i++)zwierze.move(MoveDirection.FORWARD);
        assertEquals(zwierze.getPosition(), new Vector2d(2,4));
        zwierze.move(MoveDirection.RIGHT);
        for(int i=0; i<10; i++)zwierze.move(MoveDirection.FORWARD);
        assertEquals(zwierze.getPosition(), new Vector2d(4,4));
        zwierze.move(MoveDirection.RIGHT);
        for(int i=0; i<10; i++)zwierze.move(MoveDirection.FORWARD);
        assertEquals(zwierze.getPosition(), new Vector2d(4,0));
        zwierze.move(MoveDirection.RIGHT);
        for(int i=0; i<10; i++)zwierze.move(MoveDirection.FORWARD);
        assertEquals(zwierze.getPosition(), new Vector2d(0,0));
        zwierze.move(MoveDirection.RIGHT);
        for(int i=0; i<10; i++)zwierze.move(MoveDirection.FORWARD);
        assertEquals(zwierze.getPosition(), new Vector2d(0,4));
    }

}