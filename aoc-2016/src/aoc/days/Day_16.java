package aoc.days;

import aoc.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day_16 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = "01111001100111011";
        return findChecksum(input);
    }

    @Override
    public Object part2() throws IOException {
        part2 = true;
        String input = "01111001100111011";
        return findChecksum(input);
    }

    boolean part2;

    List<Character> simulateStep(List<Character> chars) {
        List<Character> b = new ArrayList<>(chars);
        Collections.reverse(b);
        b = b.stream().map(s -> s == '1' ? '0' : '1').collect(Collectors.toList());
        chars.add('0');
        chars.addAll(b);
        return chars;
    }

    String checksum(String word) {
        while (word.length() % 2 == 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < word.length(); i+= 2) {
                if (word.charAt(i) == word.charAt(i + 1))
                    sb.append('1');
                else
                    sb.append('0');
            }
            word = sb.toString();
        }
        return word;
    }

    String findChecksum(String in) {
        int targetSize = part2 ? 35651584 : 272;
        List<Character> state = new ArrayList<>();

        for (var c : in.toCharArray())
           state.add(c);

        while (state.size() < targetSize)
            state = simulateStep(state);

        String word = state.stream().map(String::valueOf).collect(Collectors.joining()).substring(0, targetSize);
        return checksum(word);
    }
}
