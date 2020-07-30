package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;

public class Day_1 implements Day {

    public Object part1() throws IOException {
        String input = readDay(1);
        return matchDigit(input, 1);
    }

    public Object part2() throws IOException {
        String input = readDay(1);
        return matchDigit(input, input.length() / 2);
    }

    private int matchDigit(String input, int offset) {
        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt((i + offset) % input.length()) == input.charAt(i)) {
                sum += Character.getNumericValue(input.charAt(i));
            }
        }
        return sum;
    }

}
