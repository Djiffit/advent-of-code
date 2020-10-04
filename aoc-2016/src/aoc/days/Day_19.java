package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day_19 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(1);
        return solvePartOne(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(1);
        return solvePartOne(input);
    }

    String solvePartOne(String input) {
        int t = 1;

        while (t * 3 < 3005290) {
            t = t * 3;
        }
        return "" + (3005290 - t);
    }
}