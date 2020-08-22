package aoc2015.days;

import aoc2015.misc.Day;
import aoc2015.misc.Pair;

import java.io.IOException;
import java.util.*;

public class Day_16 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(16);
        return auntDetective(input, false);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(16);
        return auntDetective(input, true);
    }

    Map<Integer, Map<String, Integer>> auntData;

    int auntDetective(String input, boolean part2) {
        auntData = new HashMap<>();
        readAunts(input);

        for (int aunt : auntData.keySet()) {
            if (checkAunt(aunt, part2))
                return aunt;
        }

        return -1;
    }

    boolean checkAunt(int aunt, boolean part2) {
        List<Pair<String, Integer>> conditions = Arrays.asList(
                new Pair("children", 3),
                new Pair("cats", 7),
                new Pair("samoyeds", 2),
                new Pair("akitas", 0),
                new Pair("pomeranians", 3),
                new Pair("vizslas", 0),
                new Pair("goldfish", 5),
                new Pair("trees", 3),
                new Pair("cars", 2),
                new Pair("perfumes", 1)
        );

        Map<String, Integer> data = auntData.get(aunt);

        if (part2) {
            for (Pair<String, Integer> cond : conditions) {
                if (cond.first.equals("trees") || cond.first.equals("cats")) {
                    if (data.containsKey(cond.first) && !(data.get(cond.first) > cond.second))
                        return false;
                }
                else if (cond.first.equals("pomeranians") || cond.first.equals("goldfish")) {
                    if (data.containsKey(cond.first) && !(data.get(cond.first) < cond.second))
                        return false;
                }
                else {
                    if (data.containsKey(cond.first) && !data.get(cond.first).equals(cond.second)) {
                        return false;
                    }
                }
            }
        } else {
            for (Pair<String, Integer> cond : conditions) {
                if (data.containsKey(cond.first) && !data.get(cond.first).equals(cond.second))
                    return false;
            }
        }

        return true;
    }

    private void readAunts(String input) {
        for (String line : input.split("\n")) {
            String[] parts = line.split(", ");
            String[] first = parts[0].split(": ");
            int aunt = Integer.parseInt(first[0].split(" ")[1]);
            Map<String, Integer> data = new HashMap<>();
            data.put(first[1], Integer.parseInt(first[2]));
            for (int i = 1; i < parts.length; i ++) {
                String[] info = parts[i].split(": ");
                data.put(info[0], Integer.parseInt(info[1]));
            }
            auntData.put(aunt, data);
        }
    }

}
