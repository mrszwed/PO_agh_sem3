package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void placeGrass() {
        GrassField gf=new GrassField(100);
        Set<Vector2d> grassPosition = new HashSet<>();
        for(var i:gf.grassClamps){
          grassPosition.add(i.getPosition());
        }
        assertEquals(grassPosition.size(), gf.grassClamps.size());
    }

    @Test
    void objectAt(){
        int n=10;
        GrassField gf=new GrassField(n);
        Set <Animal> animals= new HashSet<>();
        Random rand = new Random();
        for(int i=0 ; i<5; i++){
            Vector2d pos = new Vector2d(rand.nextInt((int) Math.sqrt(n * 10) +1), rand.nextInt((int) Math.sqrt(n * 10)+1));
            Animal animal = new Animal(gf, pos);
            if(gf.place(animal))animals.add(animal);
        }
        int animalCounter=0;
        for(int h=0; h<gf.height; h++){
            for(int w=0; w<gf.width; w++){
                Object obj= gf.objectAt(new Vector2d(w,h));
                if(obj==null)continue;
                if(obj instanceof Animal){
                    assertTrue(animals.contains(obj));
                    animalCounter++;
                }
                if(obj instanceof Grass){
                    assertTrue(gf.grassClamps.contains(obj));
                }
            }
        }
        assertEquals(animalCounter, animals.size());
    }

    @Test
    void isOccupied(){
        int n=10;
        GrassField gf=new GrassField(n);
        for(var g:gf.grassClamps){
            assertTrue(gf.isOccupied(g.getPosition()));
        }
        Set <Animal> animals= new HashSet<>();
        Random rand = new Random();
        for(int i=0 ; i<5; i++){
            Vector2d pos = new Vector2d(rand.nextInt((int) Math.sqrt(n * 10) +1), rand.nextInt((int) Math.sqrt(n * 10)+1));
            Animal animal = new Animal(gf, pos);
            if(gf.place(animal))animals.add(animal);
        }
        for(var a:animals){
            assertTrue(gf.isOccupied(a.getPosition()));
        }
    }
}