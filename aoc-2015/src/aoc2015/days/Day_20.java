package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;

public class Day_20 implements Day {

    @Override
    public Object part1() throws IOException {
        return findHouseNumber(36000000, 831550);
    }

    @Override
    public Object part2() throws IOException {
        part2 = true;
        return findHouseNumber(36000000, 884500);
    }

    boolean part2 = false;

    long findHouseNumber(int target, int start) {
        for (int i = start; i < target; i += 2) {
            if (countPresents(i) >= target)
                return i;
        }
        return -1;
    }

    private long countPresents(int mid) {
        long total = 0;
        for (int i = 1; i <= mid; i++) {
            if (mid % i == 0 && (!part2 || mid / i <= 50)) {
                total += i * 11;
            }
        }

        return total;
    }
}
