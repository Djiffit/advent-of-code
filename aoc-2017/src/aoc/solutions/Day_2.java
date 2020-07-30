package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day_2 implements Day {

    public Object part1() throws IOException {
        String input = readDay(2);
        return getChecksum(input);
    }

    public Object part2() throws IOException {
        String input = readDay(2);
        return getDivChecksum(input);
    }

    private int getChecksum(String input) {
        int checksum = 0;
        String[] lines = input.split("\n");

        for (String currLine :lines) {
            List<Integer> numbers = Arrays.stream(currLine.split("\\s+")).map(Integer::valueOf).collect(Collectors.toList());
            checksum += (Collections.max(numbers) - Collections.min(numbers));
        }

        return checksum;
    }

    private int getDivChecksum(String input) {
        int checksum = 0;
        String[] lines = input.split("\n");

        for (String currLine : lines) {
            List<Integer> numbers = Arrays.stream(currLine.split("\\s+")).map(Integer::valueOf).collect(Collectors.toList());
            for (int i = 0; i < numbers.size(); i++) {
                for (int j = i + 1; j < numbers.size(); j ++) {
                    if (Math.max(numbers.get(i), numbers.get(j)) % Math.min(numbers.get(i), numbers.get(j)) == 0) {
                        checksum += Math.max(numbers.get(i), numbers.get(j)) / Math.min(numbers.get(i), numbers.get(j));
                    }
                }
            }
        }

        return checksum;
    }

}
