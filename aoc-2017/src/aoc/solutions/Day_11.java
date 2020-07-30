package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_11 implements Day {

    int total = 0;
    int endX;
    int endY;

    public Object part1() throws IOException {
        String input = readDay(11);
        simulateSteps(input);

        System.out.println(this.endX + " " + this.endY);
        return 110;
    }

    public Object part2() throws IOException {
        return this.total;
    }

    void simulateSteps(String input) {
        int x = 0;
        int y = 0;

        for (String cmd : input.split(",")) {
            System.out.println(cmd.equals("ne") + " " + x + " " + y);
            switch (cmd) {
                case "ne":
                    x += 2;
                    y += 1;
                case "nw":
                    x -= 2;
                    y += 1;
                case "n":
                    y += 2;
                case "sw":
                    x -= 2;
                    y -= 1;
                case "s":
                    y -= 2;
                case "se":
                    y -= 1;
                    x += 2;
            }
        }

        this.endX = x;
        this.endY = y;
    }
}
