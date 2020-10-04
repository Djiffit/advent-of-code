package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day_09 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(9);
        return compressionMaster(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(9);
        return megaCompressionMaster(input);
    }

    int compressionMaster(String input) {
        StringBuilder sb = new StringBuilder();
        StringBuilder repeat = new StringBuilder();

        var incommand = false;
        var readRepeat = false;

        var repeating = 0;
        var repeatcount = 0;

        for (char c : input.toCharArray()) {
            if (c == '(' && !readRepeat) {
                incommand = true;
            } else if (c == ')' && incommand) {
                incommand = false;
                readRepeat = true;
                repeating = Integer.parseInt(repeat.toString().split("x")[0]);
                repeatcount = Integer.parseInt(repeat.toString().split("x")[1]);
                repeat = new StringBuilder();
            } else {
                if (incommand)
                    repeat.append(c);
                else if (readRepeat) {
                    repeat.append(c);
                    repeating -= 1;
                    if (repeating == 0) {
                        readRepeat = false;
                        sb.append(String.valueOf(repeat).repeat(Math.max(0, repeatcount)));
                        repeat = new StringBuilder();
                    }
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString().length();
    }

    long megaCompressionMaster(String input) {
        int currWeight = 1;
        List<Pair<Integer, Integer>> modifiers = new ArrayList<>();
        long total = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            for (Pair<Integer, Integer> p : modifiers)
                if (p.first < i)
                    currWeight /= p.second;
            final int limit = i;
            modifiers = modifiers.stream().filter(x -> x.first >= limit).collect(Collectors.toList());
            if (c == '(') {
                i += 1;
                String firstNum = "";
                String secondNum = "";
                while (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                    firstNum += input.charAt(i);
                    i += 1;
                }
                i += 1;
                while (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                    secondNum += input.charAt(i);
                    i += 1;
                }
                Pair<Integer, Integer> pair = new Pair(Integer.parseInt(firstNum) + i, Integer.parseInt(secondNum));
                modifiers.add(pair);
                currWeight *= pair.second;
            } else {
                total += currWeight;
            }

        }
        return total;
    }
}
