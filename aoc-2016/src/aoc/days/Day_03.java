package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.*;

public class Day_03 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(3);
        return validTriangles(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(3);
        return validVerticalTriangles(input);
    }

    Map<Pair<Integer, Integer>, Integer> positions = new HashMap<>();

    String validTriangles(String input) {
        int total = 0;
        for (String line : input.split("\n")) {
            String[] sides = line.trim().split("\\s+");
            List<Integer> nums = Arrays.asList(Integer.parseInt(sides[0]), Integer.parseInt(sides[1]), Integer.parseInt(sides[2]));
            Collections.sort(nums);
            if (nums.get(0) + nums.get(1) > nums.get(2))
                total += 1;
        }
        return "" + total;
    }

    String validVerticalTriangles(String input) {
        int total = 0;
        int lineNumber = 0;
        for (String line : input.split("\n")) {
            String[] sides = line.trim().split("\\s+");
            List<Integer> nums = Arrays.asList(Integer.parseInt(sides[0]), Integer.parseInt(sides[1]), Integer.parseInt(sides[2]));
            positions.put(new Pair(lineNumber, 0), nums.get(0));
            positions.put(new Pair(lineNumber, 1), nums.get(1));
            positions.put(new Pair(lineNumber, 2), nums.get(2));
            lineNumber += 1;
        }

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < lineNumber; row+= 3) {
                List<Integer> nums = Arrays.asList(positions.get(new Pair(row, col)),positions.get(new Pair(row + 1, col)), positions.get(new Pair(row + 2, col)));
                Collections.sort(nums);
                if (nums.get(0) + nums.get(1) > nums.get(2))
                    total += 1;
            }

        }
        return "" + total;
    }
}
