package aoc2015.days;

import aoc2015.misc.Day;
import aoc2015.misc.Pair;

import java.io.IOException;
import java.util.*;

public class Day_24 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(24);
        return solvePartOne(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(24);
        return solvePartTwo(input);
    }

    List<Integer> packages = new ArrayList<>();

    Pair<Long, Integer> Split(List<Integer> weights, int pos, int unused, long quantumEntanglement, int count)
    {
        if (unused == 0)
            return new Pair(quantumEntanglement, count);
        else if (unused < 0 || pos == weights.size())
            return new Pair(Long.MAX_VALUE, Integer.MAX_VALUE);

        var included = Split(weights, pos + 1, unused - weights.get(pos), quantumEntanglement * weights.get(pos), count + 1);
        var notIncluded = Split(weights, pos + 1, unused, quantumEntanglement, count);

        if (included.second < notIncluded.second)
            return included;
        else if (notIncluded.second < included.second)
            return notIncluded;
        else
            return (included.first < notIncluded.first) ? included : notIncluded;
    }

    String solvePartOne(String input) {
        packages = new ArrayList<>();
        for (String num : input.split("\n"))
            packages.add(Integer.parseInt(num));
        int totalSum = packages.stream().reduce(0, Integer::sum);
        Pair<Long, Integer> pair = Split(packages, 0, totalSum / 3, 1, 0);
        return "" + pair.first;
    }

    String solvePartTwo(String input) {
        packages = new ArrayList<>();
        for (String num : input.split("\n"))
            packages.add(Integer.parseInt(num));
        int totalSum = packages.stream().reduce(0, Integer::sum);
        Pair<Long, Integer> pair = Split(packages, 0, totalSum / 4, 1, 0);
        return "" + pair.first;
    }
}
