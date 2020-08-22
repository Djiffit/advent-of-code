package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_06 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(6);
        return flipLights(input, true);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(6);
        return flipLights(input, false);
    }

    void turnOff(int[][] lights, int y1, int x1, int y2, int x2, boolean part1) {
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                if (part1)
                    lights[y][x] = 0;
                else
                    lights[y][x] = Math.max(0, lights[y][x] - 1);
            }
        }
    }

    void turnOn(int[][] lights, int y1, int x1, int y2, int x2, boolean part1) {
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                if (part1)
                    lights[y][x] = 1;
                else
                    lights[y][x] += 1;
            }
        }
    }

    void toggle(int[][] lights, int y1, int x1, int y2, int x2, boolean part1) {
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                if (part1)
                    lights[y][x] = lights[y][x] == 0 ? 1 : 0;
                else
                    lights[y][x] += 2;
            }
        }
    }

    int countLights(int[][] lights) {
        int total = 0;

        for (int[] light : lights)
            for (int x : light)
                total += x;

        return total;
    }

    int flipLights(String input, boolean part1) {
        int[][] lights = new int[1000][1000];

        for (String line : input.split("\n")) {
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(line);
            List<Integer> nums = new ArrayList<>();

            while (m.find())
                nums.add(Integer.parseInt(m.group()));

            if (line.startsWith("turn off"))
                turnOff(lights, nums.get(0), nums.get(1), nums.get(2), nums.get(3), part1);
            else if (line.startsWith("turn on"))
                turnOn(lights, nums.get(0), nums.get(1), nums.get(2), nums.get(3), part1);
            else
                toggle(lights, nums.get(0), nums.get(1), nums.get(2), nums.get(3), part1);
        }

        return countLights(lights);
    }
    
}
