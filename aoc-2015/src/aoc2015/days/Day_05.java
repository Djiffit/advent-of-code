package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_05 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(5);
        return solvePartOne(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(5);
        return solvePartTwo(input);
    }

    boolean isNice(String s) {
        String vowels = "aeiou";
        int vowelCount = 0;
        boolean dup = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (vowels.contains(c + ""))
                vowelCount += 1;
            if (i > 0 && s.charAt(i - 1) == s.charAt(i))
                dup = true;
            if (i > 0) {
                String pair = s.charAt(i - 1) + "" + s.charAt(i);
                if (pair.equals("ab") || pair.equals("cd") || pair.equals("pq") || pair.equals("xy"))
                    return false;
            }
        }

        return vowelCount >= 3 && dup;
    }

    int solvePartOne(String input) {
        int total = 0;
        for (String row : input.split("\n"))
            if (isNice(row))
                total += 1;
        return total;
    }

    boolean newIsNice(String s) {
        Set<String> pairs = new HashSet<>();
        boolean dupPair = false;
        boolean dupSkip = false;

        for (int i = 0; i < s.length(); i++) {
            if (i > 1 && s.charAt(i - 2) == s.charAt(i))
                dupSkip = true;
            if (i < s.length() - 1) {
                String pair = s.charAt(i) + "" + s.charAt(i + 1);
                if (pairs.contains(pair))
                    dupPair = true;
                if (s.charAt(i) == s.charAt(i + 1) && i < s.length() - 3 && s.charAt(i + 2) == s.charAt(i))
                    i += 1;
                pairs.add(pair);
            }
        }

        return dupSkip && dupPair;
    }

    int solvePartTwo(String input) {
        int total = 0;
        for (String row : input.split("\n"))
            if (newIsNice(row))
                total += 1;
        return total;
    }
}
