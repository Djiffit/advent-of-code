package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;

public class Day_10 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(10);
        return runSteps(input, 40);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(10);
        return runSteps(input, 50);
    }

    String lookAndSee(String input) {
        StringBuilder next = new StringBuilder();
        Character prev = null;
        int counter = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (i > 0 && c != prev) {
                next.append(counter);
                next.append(prev);
                counter = 0;
            }
            prev = c;
            counter += 1;
        }
        next.append(counter);
        next.append(prev);

        return next.toString();
    }

    int runSteps(String input, int iters) {
        for (int i = 0; i < iters; i++)
            input = lookAndSee(input);

        return input.length();
    }

}
