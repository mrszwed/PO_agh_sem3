package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testToString() {
        Vector2d v=new Vector2d(12,44);
        String s=v.toString();
        assertEquals(s,"(12,44)");
    }

    @Test
    void precedes() {
        Vector2d v1=new Vector2d(12,44);
        Vector2d v2=new Vector2d(113,45);
        assertTrue(v1.precedes(v2));

        v1=new Vector2d(12,43);
        v2=new Vector2d(11,44);
        assertFalse(v1.precedes(v2));
        assertFalse(v2.precedes(v1));

    }

    @Test
    void follows() {
        Vector2d v1=new Vector2d(12,44);
        Vector2d v2=new Vector2d(113,45);
        assertTrue(v2.follows(v1));

        v1=new Vector2d(12,43);
        v2=new Vector2d(11,44);
        assertFalse(v1.follows(v2));
        assertFalse(v2.follows(v1));
    }

    @Test
    void upperRight() {
        Random r=new Random();
        for(int i=0; i<1000; i++) {
            int x1 = r.nextInt();
            int y1 = r.nextInt();
            int x2 = r.nextInt();
            int y2 = r.nextInt();
            Vector2d v1 = new Vector2d(x1, y1);
            Vector2d v2 = new Vector2d(x2, y2);
            Vector2d ur1 = v1.upperRight(v2);
            Vector2d ur2 = v2.upperRight(v1);
            assertEquals(ur1, ur2);
            assertEquals(ur1.x, Math.max(x1, x2));
            assertEquals(ur1.y, Math.max(y1, y2));
        }
    }

    @Test
    void lowerLeft() {
        Random r=new Random();
        for(int i=0; i<1000; i++) {
            int x1 = r.nextInt();
            int y1 = r.nextInt();
            int x2 = r.nextInt();
            int y2 = r.nextInt();
            Vector2d v1 = new Vector2d(x1, y1);
            Vector2d v2 = new Vector2d(x2, y2);
            Vector2d ll1 = v1.lowerLeft(v2);
            Vector2d ll2 = v2.lowerLeft(v1);
            assertEquals(ll1, ll2);
            assertEquals(ll1.x, Math.min(x1, x2));
            assertEquals(ll1.y, Math.min(y1, y2));
        }
    }

    @Test
    void add() {
        Random r=new Random();
        for(int i=0; i<1000; i++) {
            int x1 = r.nextInt();
            int y1 = r.nextInt();
            int x2 = r.nextInt();
            int y2 = r.nextInt();
            Vector2d v1 = new Vector2d(x1, y1);
            Vector2d v2 = new Vector2d(x2, y2);
            Vector2d dod1 = v1.add(v2);
            Vector2d dod2 = v2.add(v1);
            assertEquals(dod1, dod2);
            assertEquals(dod1.x, x1+x2);
            assertEquals(dod1.y, y1+y2);
        }
    }

    @Test
    void subtract() {
        Random r=new Random();
        for(int i=0; i<1000; i++) {
            int x1 = r.nextInt();
            int y1 = r.nextInt();
            int x2 = r.nextInt();
            int y2 = r.nextInt();
            Vector2d v1 = new Vector2d(x1, y1);
            Vector2d v2 = new Vector2d(x2, y2);
            Vector2d sub = v1.subtract(v2);
            assertEquals(sub, new Vector2d(x1-x2,y1-y2));
        }
    }

    @Test
    void testEquals() {
        Random r=new Random();
        for(int i=0; i<1000; i++) {
            int x1 = r.nextInt();
            int y1 = r.nextInt();
            Vector2d v1 = new Vector2d(x1, y1);
            Vector2d v2 = new Vector2d(x1, y1);
            assertEquals(v1,v2);
        }
    }

    @Test
    void opposite() {
        Random r=new Random();
        for(int i=0; i<1000; i++) {
            int x1 = r.nextInt();
            int y1 = r.nextInt();
            int x2 = r.nextInt();
            int y2 = r.nextInt();
            Vector2d v1 = new Vector2d(x1, y1);
            Vector2d v2 = new Vector2d(x2, y2);
            Vector2d sub1 = v1.subtract(v2);
            Vector2d sub2 = v2.subtract(v1);
            assertEquals(sub1, sub2.opposite());
        }
    }
}