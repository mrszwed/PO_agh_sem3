package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void next() {
        var kierunek=MapDirection.SOUTH.next();
        assertTrue(kierunek==MapDirection.WEST);
        kierunek=kierunek.next();
        assertTrue(kierunek==MapDirection.NORTH);
        kierunek=kierunek.next();
        assertTrue(kierunek==MapDirection.EAST);
        kierunek=kierunek.next();
        assertTrue(kierunek==MapDirection.SOUTH);
    }

    @Test
    void previous() {
        var kierunek=MapDirection.SOUTH;
        for(int i=0; i<1000; i++){
            if(i%4==0)assertTrue(kierunek==MapDirection.SOUTH);
            if(i%4==1)assertTrue(kierunek==MapDirection.EAST);
            if(i%4==2)assertTrue(kierunek==MapDirection.NORTH);
            if(i%4==3)assertTrue(kierunek==MapDirection.WEST);
            kierunek=kierunek.previous();
        }
    }
}