package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.*;

public class Day_17 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(17);
        return solvePartOne(input, false);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(17);
        return solvePartOne(input, true);
    }

    Set<List<Integer>> hist;
    List<List<Integer>> solutions;
    Map<Integer, Integer> counts;

    int solvePartOne(String input, boolean part2) {
        List<Integer> containers = new ArrayList<>();
        counts = new HashMap<>();

        hist = new HashSet<>();
        solutions = new ArrayList<>();

        for (String number : input.split("\n"))
            containers.add(Integer.parseInt(number));

        Collections.sort(containers);
        for (int number : containers)
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        tryAllContainers(new ArrayList<>(), containers, 150);

        if (part2) {
            int minSize = Integer.MAX_VALUE;

            for (List<Integer> sol : solutions)
                minSize = Math.min(sol.size(), minSize);

            int totalMinCount = 0;

            for (List<Integer> sol : solutions)
                if (sol.size() == minSize)
                    totalMinCount += 1;

            return totalMinCount;
        }

        return solutions.size();
    }

    void tryAllContainers(List<Integer> sofar, List<Integer> usable, int remaining) {
        Collections.sort(sofar);
        Collections.sort(usable);
        if (hist.contains(sofar))
            return;
        hist.add(new ArrayList<>(sofar));
        if (remaining == 0) {
            HashMap<Integer, Integer> counter = new HashMap<>();
            int count = 1;
            for (int number : sofar)
                counter.put(number, counter.getOrDefault(number, 0) + 1);

            for (int key : counter.keySet()) {
                if (counter.get(key) < counts.get(key)) {
                    count *= counts.get(key);
                }
            }
            for (int i = 0; i < count; i ++)
                solutions.add(new ArrayList<>(sofar));
            return;
        }

        for (int i = 0; i < usable.size(); i++) {
            if (usable.get(i) <= remaining) {
                int temp = usable.remove(i);
                sofar.add(temp);
                tryAllContainers(new ArrayList<>(sofar), usable, remaining - temp);
                usable.add(temp);
                sofar.remove(sofar.size() - 1);
                Collections.sort(usable);
            }
        }
    }

    String solvePartTwo(String input) {

        return "success";
    }
}
