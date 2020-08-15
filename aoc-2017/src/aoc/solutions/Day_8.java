package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_8 implements Day {

    HashMap<String, Integer> registers = new HashMap<>();
    int largestValue = 0;

    public Object part1() throws IOException {
        String input = readDay(8);
        return getLargestValue(input);
    }

    public Object part2() throws IOException {
        String input = readDay(8);
        return this.largestValue;
    }

    private int getLargestValue(String input) {
        for (String cmd : input.split("\n")) {
            processCommand(cmd);
        }

        return Collections.max(this.registers.values());
    }

    private boolean validCondition(String cond, String target, int param) {
        int targetVal = this.registers.getOrDefault(target, 0);

        switch (cond) {
            case "==":
                return targetVal == param;
            case "!=":
                return targetVal != param;
            case "<":
                return targetVal < param;
            case "<=":
                return targetVal <= param;
            case ">":
                return targetVal > param;
            case ">=":
                return targetVal >= param;
            default:
                return false;
        }
    }

    private void processCommand(String input) {
        String pattern = "(\\w+)\\s+(\\w+)\\s+([-]?\\d+) if (\\w+)\\s([=>!<]+)\\s([-]?\\d+)";
        Pattern pat = Pattern.compile(pattern);

        Matcher match = pat.matcher(input);
        match.matches();

        String operand = match.group(1);
        String operation = match.group(2);
        int change = Integer.parseInt(match.group(3));
        String condTarget = match.group(4);
        String cond = match.group(5);
        int condParameter = Integer.parseInt(match.group(6));

        if (validCondition(cond, condTarget, condParameter)) {
            this.registers.put(operand, this.registers.getOrDefault(operand, 0) + (operation.equals("inc") ? change : -change));
        }

        this.largestValue = Math.max(this.largestValue, this.registers.getOrDefault(operand, 0));
    }

}
