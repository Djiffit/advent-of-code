package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day_20 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(20);
        return findIntervals(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(20);
        part2 = true;
        return findIntervals(input);
    }

    boolean part2 = false;

    String findIntervals(String input) {
        List<Pair<Long, Long>> ranges = new ArrayList<>();
        for (String line : input.split("\n")) {
            String[] parts = line.split("-");
            ranges.add(new Pair(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
        }
        Collections.sort(ranges);
        long smallest = 0;
        long skipped = 0;

        for (var p : ranges) {
            if (p.first - smallest > 1) {
                skipped += (p.first - smallest) - 1;
                if (!part2) {
                    return "" + (smallest + 1);
                }
            }
            smallest = Math.max(p.second, smallest);
        }

        return "" + skipped;
    }
}
