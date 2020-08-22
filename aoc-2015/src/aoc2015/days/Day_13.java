package aoc2015.days;

import aoc2015.misc.Day;
import aoc2015.misc.Pair;

import java.io.IOException;
import java.util.*;

public class Day_13 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(13);
        return testSeatingPositions(input, false);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(13);
        return testSeatingPositions(input, true);
    }

    Map<Pair<String, String>, Integer> connections;
    Set<String> people;
    int record;

    int testSeatingPositions(String input, boolean part2) {
        connections = new HashMap<>();
        people = new HashSet<>();
        record = -Integer.MAX_VALUE;

        loadConnections(input);
        if (part2) {
            for (String person : people) {
                connections.put(new Pair("Me", person), 0);
                connections.put(new Pair(person, "Me"), 0);
            }

            people.add("Me");
        }
        testAllFormations(new ArrayList<>());

        return record;
    }

    private void testAllFormations(ArrayList<String> seats) {
        if (people.size() == seats.size())
            record = Math.max(record, evaluateSeatingPositions(seats));

        for (String person : people) {
            if (!seats.contains(person)) {
                seats.add(person);
                testAllFormations(seats);
                seats.remove(seats.size() - 1);
            }
        }
    }

    private int evaluateSeatingPositions(ArrayList<String> seats) {
        int total = connections.get(new Pair(seats.get(0), seats.get(seats.size() - 1)))
                + connections.get(new Pair(seats.get(0), seats.get(1)))
                + connections.get(new Pair(seats.get(seats.size() - 1), seats.get(0)))
                + connections.get(new Pair(seats.get(seats.size() - 1), seats.get(seats.size() - 2)));

        for (int i = 1; i < seats.size() - 1; i ++)
            total += connections.get(new Pair(seats.get(i), seats.get(i - 1)))
                    + connections.get(new Pair(seats.get(i), seats.get(i + 1)));

        return total;
    }

    private void loadConnections(String input) {
        for (String line : input.split("\n")) {
            String[] split = line.split(" ");
            String person = split[0];
            people.add(person);
            String target = split[split.length - 1];
            target = target.substring(0, target.length() - 1);
            int effect = split[2].equals("gain") ? 1 : -1;
            int amount = Integer.parseInt(split[3]);
            connections.put(new Pair(person, target), effect * amount);
        }
    }

}
