package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.*;

public class Day_07 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(7);
        return solvePartOne(input, false);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(7);
        return solvePartOne(input, true);
    }

    boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    long solvePartOne(String input, boolean part2) {
        Map<String, Long> values = new HashMap<>();
        Map<String, String> rules = new HashMap<>();
        Map<String, List<String>> prereqs = new HashMap<>();

        for (String line : input.split("\n")) {
            String[] ops = line.split(" -> ");
            List<String> requirements = new ArrayList<>();

            String target = ops[1];
            String rule = ops[0];

            rules.put(target, rule);
            processRequirements(values, requirements, target, rule);
            prereqs.put(target, requirements);
        }

        if (part2)
            values.put("b", 46065L);

        return resolveValue("a", values, rules, prereqs);
    }

    private void processRequirements(Map<String, Long> values, List<String> requirements, String target, String rule) {
        if (rule.contains("RSHIFT") || rule.contains("LSHIFT"))
            requirements.add(rule.split(" ")[0]);
        else if (rule.contains("OR") || rule.contains("AND")) {
            String p1 = rule.split(" ")[0];
            String p2 = rule.split(" ")[2];

            if (!isNumeric(p1))
                requirements.add(p1);
            if (!isNumeric(p2))
                requirements.add(p2);

        } else if (rule.contains("NOT"))
            requirements.add(rule.split(" ")[1]);
        else {
            if (isNumeric(rule))
                values.put(target, Long.parseLong(rule));
            else
                requirements.add(rule);
        }
    }

    private long resolveValue(String curr, Map<String, Long> values, Map<String, String> rules, Map<String, List<String>> prereqs) {
        if (values.containsKey(curr))
            return values.get(curr);

        for (String req : prereqs.getOrDefault(curr, new ArrayList<>()))
            resolveValue(req, values, rules, prereqs);

        long value = resolveRule(rules.get(curr), values);
        values.put(curr, value);

        return value;
    }

    long resolveValue(String param, Map<String, Long> values) {
        if (isNumeric(param))
            return Long.parseLong(param);
        return values.get(param);
    }

    private long resolveRule(String rule, Map<String, Long> values) {
        String[] splitRule = rule.split(" ");

        if (rule.contains("RSHIFT"))
            return resolveValue(splitRule[0], values) >> Integer.parseInt(splitRule[2]);
        if (rule.contains("LSHIFT"))
            return resolveValue(splitRule[0], values) << Integer.parseInt(splitRule[2]);
        if (rule.contains("NOT"))
            return ~resolveValue(splitRule[1], values);
        if (rule.contains("OR"))
            return resolveValue(splitRule[0], values) | resolveValue(splitRule[2], values);
        if (rule.contains("AND"))
            return resolveValue(splitRule[0], values) & resolveValue(splitRule[2], values);

        return resolveValue(rule, values);
    }

}
