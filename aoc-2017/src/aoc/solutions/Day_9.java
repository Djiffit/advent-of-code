package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_9 implements Day {

    int total = 0;

    public Object part1() throws IOException {
        String input = readDay(9);
        return countScore(input);
    }

    public Object part2() throws IOException {
        return this.total;
    }

    private int countScore(String input) {
        char[] braces = clearGarbage(input).toCharArray();
        int total = 0;
        int depth = 0;

        for (char c : braces) {
            if (c == '}') {
                total += depth;
                depth -= 1;
            } else {
                depth += 1;
            }
        }

        return total;
    }

    private String clearGarbage(String input) {
        char[] chars = input.toCharArray();
        StringBuilder cleanInput = new StringBuilder();
        boolean inGarbage = false;
        boolean skip = false;
        List<Character> validChars = new ArrayList<>();
        validChars.add('{');
        validChars.add('}');

        for (char c : chars) {
            if (skip) {
                skip = false;
                continue;
            }

            if (c == '<' && !inGarbage) {
                inGarbage = true;
                continue;
            }

            if (c == '!' && inGarbage) {
                skip = true;
                continue;
            }

            if (inGarbage) {
                if (c == '>')
                    inGarbage = false;
                else
                    this.total += 1;
                continue;
            }

            if (validChars.contains(c))
                cleanInput.append(c);
        }

        return cleanInput.toString();
    }

}
