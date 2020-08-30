package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day_06 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(6);
        return decodeMessage(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(6);
        part2 = true;
        return decodeMessage(input);
    }

    Map<Pair<Integer, Character>, Integer> countsByPos = new HashMap();
    boolean part2 = false;

    String decodeMessage(String input) {
        for (String line : input.split("\n")) {
            for (int i = 0; i < line.length(); i++) {
                Pair<Integer, Character> pair = new Pair(i, line.charAt(i));
                countsByPos.put(pair, countsByPos.getOrDefault(pair, 0) + 1);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int record = part2 ? Integer.MAX_VALUE : 0;
            char recordChar = 'a';
            for (char c = 'a'; c <= 'z'; c++) {
                if (part2) {
                    if (countsByPos.getOrDefault(new Pair(i, c), Integer.MAX_VALUE) < record) {
                        record = countsByPos.get(new Pair(i, c));
                        recordChar = c;
                    }
                } else {
                    if (countsByPos.getOrDefault(new Pair(i, c), 0) > record) {
                        record = countsByPos.get(new Pair(i, c));
                        recordChar = c;
                    }
                }
            }
            sb.append(recordChar);
        }
        return sb.toString();
    }
}
