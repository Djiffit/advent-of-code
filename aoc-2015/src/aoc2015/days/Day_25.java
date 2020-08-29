package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;

public class Day_25 implements Day {

    @Override
    public Object part1() throws IOException {
        return getTheWeirdNumber(2978, 3083);
    }

    @Override
    public Object part2() throws IOException {
        return "Done xD";
    }

    String getTheWeirdNumber(int row, int col) {
        long position = solvePosition(row, col);

        return "" + getNumAtPos(position);
    }

    long getNumAtPos(long pos) {
        long num = 20151125;

        for (int i = 1; i < pos; i++) {
            num = (num * 252533) % 33554393;
        }

        return num;
    }

    private long solvePosition(int row, int col) {
        long i = 1;
        long total = 0;

        while (true) {
            long y = i;
            long x = 1;
            if (y - row == col - x)
                break;
            total += i;
            i += 1;
        }
        return total + col;
    }
}
