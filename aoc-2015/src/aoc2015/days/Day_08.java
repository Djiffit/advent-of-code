package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;

public class Day_08 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(8);
        return countStringDiff(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(8);
        return countExpandedStringDiff(input);
    }

    Long countStringDiff(String input) {
        long total = 0L;
        long totalString = 0L;

        for (String line : input.split("\n")) {
            total += line.length();
            totalString += countCharacters(line);
        }

        return total - totalString;
    }

    private long countCharacters(String line) {
        long total = 0L;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c != '\\')
                total += 1;
            else {
                char nextC = line.charAt(i + 1);
                if (nextC == '"' || nextC == '\\') {
                    total += 1;
                    i += 1;
                } else if (nextC == 'x') {
                    total += 1;
                    i += 3;
                }
            }

        }

        return total - 2;
    }

    private long countExpansionCharacters(String line) {
        long total = 0L;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"' || c == '\\')
                total += 2;
            else
                total += 1;

        }

        return total + 2;
    }

    long countExpandedStringDiff(String input) {
        long total = 0L;
        long expTotal = 0L;

        for (String line : input.split("\n")) {
            total += line.length();
            expTotal += countExpansionCharacters(line);
        }

        return expTotal - total;
    }
}
