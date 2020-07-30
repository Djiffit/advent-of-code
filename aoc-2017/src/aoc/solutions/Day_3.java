package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.*;

public class Day_3 implements Day {
    Map<Integer, Map<Integer, Integer>> grid = new HashMap<>();

    public Object part1() throws IOException {
        String input = readDay(3);
        return getSteps(Integer.parseInt(input));
    }

    public Object part2() throws IOException {
        String input = readDay(3);
        return findFirstLarger(Integer.parseInt(input));
    }

    private int getSteps(int num) {
        int sideSize = 1;
        int numsSoFar = 1;
        int depth = 0;

        while (true) {
            depth += 1;
            if (numsSoFar + sideSize * 4 + 4 >= num) {
                sideSize += 2;
                break;
            }
            numsSoFar += sideSize * 4 + 4;
            sideSize += 2;
        }

        int remainingSteps = num - numsSoFar - 1;
        if (remainingSteps <= sideSize - 2) {
            return Math.abs((sideSize - 2 - remainingSteps) - sideSize / 2) + depth;
        }
        remainingSteps -= sideSize - 2;
        for (int i = 0; i < 3; i++) {
            if (remainingSteps <= sideSize - 1) {
                return Math.abs((sideSize - 1 - remainingSteps) - sideSize / 2) + depth;
            }
            remainingSteps -= sideSize - 1;
        }

        return num;
    }

    private void insertToMap(int y, int x, int val) {
        if (!this.grid.containsKey(y)) {
            this.grid.put(y, new HashMap<>());
        }
        this.grid.get(y).put(x, val);
    }

    private int getAndInsertTotal(int y, int x) {
        int total = 0;
        for (int y_d = -1; y_d < 2; y_d++) {
            for (int x_d = -1; x_d < 2; x_d++) {
                if (this.grid.containsKey(y + y_d) && this.grid.get(y + y_d).containsKey(x + x_d)) {
                    total += this.grid.get(y + y_d).get(x + x_d);
                }
            }
        }
        insertToMap(y, x, total);
        return total;
    }

    private int findFirstLarger(int num) {
        int x = 0;
        int y = 0;
        int sideSize = 3;
        insertToMap(0, 0, 1);

        while (true) {
            x += 1;
            int total = getAndInsertTotal(y, x);
            if (total > num)
                return total;
            for (int i = 0; i < sideSize - 2; i++) {
                y -= 1;
                total = getAndInsertTotal(y, x);
                if (total > num)
                    return total;
            }
            for (int i = 0; i < sideSize - 1; i++) {
                x -= 1;
                total = getAndInsertTotal(y, x);
                if (total > num)
                    return total;
            }
            for (int i = 0; i < sideSize - 1; i++) {
                y += 1;
                total = getAndInsertTotal(y, x);
                if (total > num)
                    return total;
            }
            for (int i = 0; i < sideSize - 1; i++) {
                x += 1;
                total = getAndInsertTotal(y, x);
                if (total > num)
                    return total;
            }
            sideSize += 2;
        }
    }

}
