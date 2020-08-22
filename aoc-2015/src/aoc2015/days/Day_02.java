package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day_02 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(2);
        return getPaperArea(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(2);
        return getRibbonLength(input);
    }

    long getArea(long w, long h, long l) {
        return 2 * w * l + 2 * w * h + 2 * h * l + Math.min(w * l, Math.min(h * w, l * h));
    }

    Long getPaperArea(String input) {
        long total = 0L;

        for (String line : input.split("\n")) {
            String[] dims = line.split("x");
            total += getArea(Long.parseLong(dims[0]), Long.parseLong(dims[1]), Long.parseLong(dims[2]));
        }

        return total;
    }

    Long countRibbon(long w, long h, long l) {
        Long[] lengths = new Long[]{h, l, w};
        Arrays.sort(lengths);
        return w * h * l + lengths[0] * 2 + lengths[1] * 2;
    }

    Long getRibbonLength(String input) {
        long total = 0L;

        for (String line : input.split("\n")) {
            String[] dims = line.split("x");
            total += countRibbon(Long.parseLong(dims[0]), Long.parseLong(dims[1]), Long.parseLong(dims[2]));
        }

        return total;
    }
}
