package aoc;

import aoc.misc.Day;
import aoc.solutions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Day> days = new ArrayList<>();
        days.add(new Day_1());
        days.add(new Day_2());
        days.add(new Day_3());
        days.add(new Day_4());
        days.add(new Day_5());
        days.add(new Day_6());
        days.add(new Day_7());
        days.add(new Day_8());
        days.add(new Day_9());
        days.add(new Day_10());
        days.add(new Day_11());
        days.add(new Day_12());
        days.add(new Day_13());
        days.add(new Day_14());
        days.add(new Day_15());
        days.add(new Day_16());
        days.add(new Day_17());
        days.add(new Day_18());
        days.add(new Day_19());
        days.add(new Day_20());
        days.add(new Day_21());
        days.add(new Day_22());
        days.add(new Day_23());
        days.add(new Day_24());
        days.add(new Day_25());

        for (int i = 0; i < 25; i++) {
            System.out.println("Results for day " + (i + 1));
            days.get(i).printParts();
        }
    }
}
