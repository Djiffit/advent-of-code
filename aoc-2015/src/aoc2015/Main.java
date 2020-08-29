package aoc2015;

import aoc2015.days.*;
import aoc2015.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Day> days = new ArrayList<>();
        days.add(new Day_01());
        days.add(new Day_02());
        days.add(new Day_03());
        days.add(new Day_04());
        days.add(new Day_05());
        days.add(new Day_06());
        days.add(new Day_07());
        days.add(new Day_08());
        days.add(new Day_09());
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

        for (int i = 0; i < days.size(); i++) {
            System.out.println("Results for day " + (i + 1));
            days.get(i).printParts();
        }
    }
}
