package com.subhrajit.roy.usagetracker;

import org.junit.Ignore;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Test
    @Ignore
    public void testStreamUsage(){
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        Item shoe1 = new Item("Shoe", today);
        Item shoe2 = new Item("Shoe", today);
        Item shirt = new Item("Shirt", yesterday);
        Item shirt2 = new Item("Shirt", today);



        List<Item> items = new ArrayList<>(Arrays.asList(shoe1, shoe2, shirt, shirt2));

        Map<String, List<Item>> collect = items.stream().collect(Collectors.groupingBy(i -> i.category));
    }

    class Item{

        public Item(String category, LocalDate date) {
            this.category = category;

            this.date = date;
        }

        String category;

        LocalDate  date;
    }
}