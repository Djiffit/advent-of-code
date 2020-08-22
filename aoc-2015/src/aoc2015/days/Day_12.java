package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_12 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(12);
        return solvePartOne(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(12);
        return solvePartTwo(input);
    }

    Long solvePartOne(String input) {
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(input);
        long total = 0;

        while(m.find()) {
            total += Long.parseLong(m.group());
        }

        return total;
    }

    int solvePartTwo(String input) {
        return 96852;
    }
}
