package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day_6 implements Day {

    public Object part1() throws IOException {
        String input = readDay(6);
        return memoryAllocator(input, false);
    }

    public Object part2() throws IOException {
        String input = readDay(6);
        return memoryAllocator(input, true);
    }

    private void runBalance(List<Integer> numbers) {
        int best_ind = 0;
        int best_val = 0;

        for (int i = 0; i < numbers.size(); i ++) {
            if (numbers.get(i) > best_val) {
                best_ind = i;
                best_val = numbers.get(i);
            }
        }

        numbers.set(best_ind, 0 );
        int ind = best_ind;
        while (best_val > 0) {
            ind = (ind + 1) % numbers.size();
            numbers.set(ind, numbers.get(ind) + 1);
            best_val -= 1;
        }
    }

    private int memoryAllocator(String input, boolean findCycle) {
        int rounds = 0;

        List<Integer> numbers = Arrays.stream(input.split("\\s+")).map(Integer::valueOf).collect(Collectors.toList());
        HashMap<List<Integer>, Integer> hist = new HashMap<>();
        hist.put(numbers, 0);

        while (true) {
            runBalance(numbers);
            rounds += 1;
            if (hist.containsKey(numbers)) {
                if (findCycle)
                    return findCycle(numbers);
                return rounds;
            }
            hist.put(numbers, rounds);
        }
    }

    private int findCycle(List<Integer> numbers) {
        List<Integer> target = numbers.stream().collect(Collectors.toList());
        int rounds = 1;
        runBalance(numbers);
        while (!numbers.equals(target)) {
            runBalance(numbers);
            rounds+= 1;
        }
        return rounds;
    }

}
