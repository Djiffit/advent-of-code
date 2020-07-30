package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.*;

public class Day_4 implements Day {
    public Object part1() throws IOException {
        String input = readDay(4);
        return validPassPhrases(input);
    }

    public Object part2() throws IOException {
        String input = readDay(4);
        return validAnagrams(input);
    }

    private int validPassPhrases(String input) {
        String[] phrases = input.split("\n");
        int total = 0;

        for (String phrase : phrases) {
            String[] words = phrase.split("\\s+");
            Set<String> uniques = new HashSet<>();
            for (String w : words)
                uniques.add(w);
            total += uniques.size() == words.length ? 1 : 0;
        }

        return total;
    }

    private int validAnagrams(String input) {
        String[] phrases = input.split("\n");
        int total = 0;

        for (String phrase : phrases) {
            String[] words = phrase.split("\\s+");
            Set<String> uniques = new HashSet<>();
            for (String w : words) {
                char temp[] = w.toCharArray();
                Arrays.sort(temp);
                uniques.add(new String(temp));
            }
            total += uniques.size() == words.length ? 1 : 0;
        }

        return total;
    }

}
