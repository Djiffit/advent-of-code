package aoc.days;

import aoc.misc.Day;

import java.io.IOException;

public class Day_18 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(1);
        return solvePartOne(input);
    }

    @Override
    public Object part2() throws IOException {
        part2 = true;
        String input = readDay(1);
        return solvePartOne(input);
    }

    String in = "^^^^......^...^..^....^^^.^^^.^.^^^^^^..^...^^...^^^.^^....^..^^^.^.^^...^.^...^^.^^^.^^^^.^^.^..^.^";
    char trap = '^';
    char safe = '.';
    boolean part2 = false;

    String newRow(String curr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < curr.length(); i++) {
            var left = i == 0 ? ' ' : curr.charAt(i - 1);
            var top = curr.charAt(i);
            var right = i == curr.length() - 1 ? ' ' : curr.charAt(i + 1);
            if ((left == trap && top == trap && right != trap) || (top == trap && right == trap && left != trap) || (left == trap && right != trap && top != trap) || (right == trap && top != trap && left != trap)) {
                sb.append(trap);
            } else {
                sb.append(safe);
            }
        }
        return sb.toString();
    }

    int safeSpots(String in) {
        int total = 0;
        for (var sp : in.toCharArray()) {
           if (sp == safe)
               total += 1;
        }
        return total;
    }

    String solvePartOne(String input) {
        int total = safeSpots(in);
        String curr = in;

        for (int i =0 ; i < (part2 ? 399999 : 39); i++) {
            curr = newRow(curr);
            total += safeSpots(curr);
        }

        return "" + total;
    }
}
