package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_19 implements Day {

    String molecule = "CRnCaSiRnBSiRnFArTiBPTiTiBFArPBCaSiThSiRnTiBPBPMgArCaSiRnTiMgArCaSiThCaSiRnFArRnSiRnFArTiTiBFArCaCaSiRnSiThCaCaSiRnMgArFYSiRnFYCaFArSiThCaSiThPBPTiMgArCaPRnSiAlArPBCaCaSiRnFYSiThCaRnFArArCaCaSiRnPBSiRnFArMgYCaCaCaCaSiThCaCaSiAlArCaCaSiRnPBSiAlArBCaCaCaCaSiThCaPBSiThPBPBCaSiRnFYFArSiThCaSiRnFArBCaCaSiRnFYFArSiThCaPBSiThCaSiRnPMgArRnFArPTiBCaPRnFArCaCaCaCaSiRnCaCaSiRnFYFArFArBCaSiThFArThSiThSiRnTiRnPMgArFArCaSiThCaPBCaSiRnBFArCaCaPRnCaCaPMgArSiRnFYFArCaSiThRnPBPMgAr";

    @Override
    public Object part1() throws IOException {
        String input = readDay(19);
        return solvePartOne(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(19);
        return solvePartTwo(input);
    }

    Map<String, List<String>> rules;
    Map<String, List<String>> rev_rules;
    Set<String> results;

    int solvePartOne(String input) {
        readRules(input);
        results = new HashSet<>();
        parseAllRules(molecule, results, rules);
        return results.size();
    }

    private void parseAllRules(String input, Set<String> results, Map<String, List<String>> rules) {
        for (String key : rules.keySet()) {
            Pattern pat = Pattern.compile(key);
            Matcher mat = pat.matcher(input);

            while (mat.find()) {
                for (String replacement : rules.get(key)) {
                    StringBuilder newString = new StringBuilder(input);
                    int startInd = mat.start();
                    newString.replace(startInd, startInd + key.length(), replacement);
                    results.add(newString.toString());
                }
            }
        }
    }

    private void readRules(String input) {
        rules = new HashMap<>();
        rev_rules = new HashMap<>();
        for (String line : input.split("\n")) {
            String[] comps = line.split(" => ");
            List<String> outputs = rules.getOrDefault(comps[0], new ArrayList<>());
            List<String> outputsRev = rules.getOrDefault(comps[1], new ArrayList<>());
            outputs.add(comps[1]);
            outputsRev.add(comps[0]);
            rules.put(comps[0], outputs);
            rev_rules.put(comps[1], outputsRev);
        }
    }

    int solvePartTwo(String input) {
        readRules(input);
        String current = molecule;
        int rounds = 0;

        while (!current.equals("e")) {
            rounds += 1;
            Set<String> results = new HashSet<>();
            parseAllRules(current, results, rev_rules);
            current = (String) results.toArray()[0];
        }

        return rounds;
    }
}
