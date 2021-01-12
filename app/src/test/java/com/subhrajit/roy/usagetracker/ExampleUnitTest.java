package com.subhrajit.roy.usagetracker;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testUUID(){
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        UUID uuidFromString = UUID.fromString(uuidAsString);
        System.out.println(uuidAsString);
        System.out.println(uuidFromString);
        assertEquals(uuid,uuidFromString);
    }
}