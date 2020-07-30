package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day_5 implements Day {

    public Object part1() throws IOException {
        String input = readDay(5);
        return numJumps(input, false);
    }

    public Object part2() throws IOException {
        String input = readDay(5);
        return numJumps(input, true);
    }

    private int numJumps(String input, boolean weird) {
        int total = 0;
        int pos = 0;
        List<Integer> numbers = Arrays.stream(input.split("\n")).map(Integer::valueOf).collect(Collectors.toList());
        while (pos < numbers.size()) {
            int change = weird && numbers.get(pos) >= 3 ? -1 : 1;
            numbers.set(pos, numbers.get(pos) + change);
            pos = Math.max(0, numbers.get(pos) - change + pos);
            total += 1;
        }
        return total;
    }

}
