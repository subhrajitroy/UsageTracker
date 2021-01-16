package com.subhrajit.roy.usagetracker;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * These have nothing to do with the app in itself
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
    public void testStreamUsage(){

        List<Item> items = listOfItems();

        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> result = numbers.stream().collect(new CustomCollector(2));
        result.forEach(r -> System.out.println(r));

        ArrayList<Long> longs = new ArrayList<>(Arrays.asList(1L, 2L, 3L, 10L,101L));

//        Collectors.counting();
//        Collectors.groupingBy()

        Long sum = longs.stream().collect(new CountCollector());
        Long expected = 117L;
        assertEquals(expected,sum);
    }

    private List<Item> listOfItems() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        Item shoe1 = new Item("Shoe", today);
        Item shoe2 = new Item("Shoe", today);
        Item shirt = new Item("Shirt", yesterday);
        Item shirt2 = new Item("Shirt", today);
        Item shirt3 = new Item("Shirt", today);
        Item shirt4 = new Item("Shirt", yesterday);

        return new ArrayList<>(Arrays.asList(shoe1, shoe2, shirt, shirt2,shirt3,shirt4));
    }

    @Test
    public void groupByCollectorTest(){
        List<Item> items = listOfItems();
        Map<String, List<Item>> collect = items.stream().collect(new GroupByCollector<>(u -> u.category));
        List<Item> shirts = collect.get("Shirt");
        List<Item> shoes = collect.get("Shoe");
        assertEquals(4,shirts.size());
        assertEquals(2,shoes.size());
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