package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;

public class Day_01 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(1);
        return solvePartOne(input);
    }

    @Override
    public Object part2() throws IOException {
        return firstBasement;
    }

    int firstBasement = -1;

    Long solvePartOne(String input) {
        long floor = 0L;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            floor += c == ')' ? -1 : 1;
            if (floor < 0 && firstBasement == -1)
                firstBasement = i + 1;
        }

        return floor;
    }
}
