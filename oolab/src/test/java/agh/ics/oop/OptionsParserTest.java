package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void parse() {
        try{
            OptionsParser.parse(new String[]{"f","b","right","up"});
            fail("oczekiwany wyjatek dla up");
        }
        catch(IllegalArgumentException e){}
    }

    @Test
    void parse2() {
        assertThrows(IllegalArgumentException.class, ()->{
            OptionsParser.parse(new String[]{"f","b","right","up"});
        });
    }

    @Test
    void parse3() {
        try{
            OptionsParser.parse(new String[]{"f","b","right","up"});
            fail("oczekiwany wyjatek dla up");
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}