package aoc.days;

import aoc.misc.Day;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day_12 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(12);
        return registerSimulator(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(12);
        return registerSimulatorTwo(input);
    }

    Map<String, Integer> registers = new HashMap<>();

    int getValue(String param) {
        try {
            return Integer.parseInt(param);
        } catch (Exception e) {
            return registers.getOrDefault(param, 0);
        }
    }

    int registerSimulator(String input) {
        String[] cmds = input.split("\n");
        int position = 0;
        while (position < cmds.length) {
            var line = cmds[position];
            String[] split = line.split(" ");
            if (line.startsWith("cpy")) {
                registers.put(line.split(" ")[2], getValue(line.split(" ")[1]));
            } else if (line.startsWith("inc")) {
               registers.put(split[1], registers.getOrDefault(split[1], 0) + 1);
            } else if (line.startsWith("dec")) {
                registers.put(split[1], registers.getOrDefault(split[1], 0) - 1);
            } else if (line.startsWith("jnz")) {
                if (getValue(split[1]) != 0) {
                    position += (Integer.parseInt(split[2]) - 1);
                }
            }
            position += 1;
        }
        return registers.get("a");
    }

    int registerSimulatorTwo(String input) {
       registers = new HashMap<>();
       registers.put("c", 1);

       return registerSimulator(input);
    }
}
