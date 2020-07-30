package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day_7 implements Day {

    HashMap<String, Integer> weights = new HashMap<>();
    HashMap<String, Integer> totalWeight = new HashMap<>();
    HashMap<String, Set<String>> children = new HashMap<>();
    HashMap<String, Set<String>> parents = new HashMap<>();

    public Object part1() throws IOException {
        String input = readDay(7);
        return getRootNode(input);
    }

    public Object part2() throws IOException {
        String input = readDay(7);
        return findImbalance(getRootNode(input));
    }

    private void insertChild(String key, String value) {
        if (this.children.containsKey(key)) {
            this.children.get(key).add(value);
        } else {
            Set<String> children = new HashSet<>();
            children.add(value);
            this.children.put(key, children);
        }
    }

    private void insertParent(String key, String value) {
        if (this.parents.containsKey(key)) {
            this.parents.get(key).add(value);
        } else {
            Set<String> parents = new HashSet<>();
            parents.add(value);
            this.parents.put(key, parents);
        }
    }

    private void readTree(String inputString) {
        for (String input : inputString.split("\n")) {
            input = input.replace(",", "");
            String[] parts = input.split(" -> ");
            String pattern = "(\\w+)\\s+[(](\\d+)[)]";
            Pattern pat = Pattern.compile(pattern);

            Matcher match = pat.matcher(parts[0]);
            match.matches();

            String name = match.group(1);
            this.weights.put(name, Integer.parseInt(match.group(2)));

            if (parts.length > 1) {
                for (String child : parts[1].split("\\s")) {
                    insertChild(child, name);
                    insertParent(name, child);
                }
            }
        }
    }

    private String getRootNode(String input) {
        readTree(input);
        for (String key : this.weights.keySet()) {
            if (!this.children.containsKey(key)) {
                return key;
            }
        }
        return "";
    }

    private int getTotalWeights(String root) {
        if (totalWeight.containsKey(root)) {
            return totalWeight.get(root);
        }

        int total = this.weights.get(root);
        for (String parent : this.parents.getOrDefault(root, new HashSet<>())) {
            total += getTotalWeights(parent);
        }
        this.totalWeight.put(root, total);
        return total;
    }

    private boolean isValidNode(String root) {
        Set<Integer> weights = new HashSet<>();

        for (String parent : this.parents.getOrDefault(root, new HashSet<>())) {
            weights.add(this.totalWeight.get(parent));
        }

        return weights.size() < 2;
    }

    private int findImbalance(String root) {
        getTotalWeights(root);
        int result = 0;
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        for (String key : this.children.keySet()) {
            if (!this.parents.containsKey(key)) {
                queue.add(key);
            }
        }

        while (queue.size() > 0) {
            String curr = queue.poll();
            if (isValidNode(curr)) {
                for (String child: this.children.getOrDefault(curr, new HashSet<>())) {
                    if (!visited.contains(child)) {
                        queue.add(child);
                        visited.add(child);
                    }
                }
            } else {
                HashMap<Integer, Integer> counts = new HashMap<>();
                HashMap<Integer, String> responsible = new HashMap<>();

                for (String parent : this.parents.getOrDefault(curr, new HashSet<>())) {
                    counts.put(this.totalWeight.get(parent), counts.getOrDefault(this.totalWeight.get(parent), 0) + 1);
                    responsible.put(this.totalWeight.get(parent), parent);
                }

                int wrongVal = 0;
                int correctVal = 0;

                for (int val : counts.keySet()) {
                    if (counts.get(val) == 1) {
                        wrongVal = val;
                    } else {
                        correctVal = val;
                    }
                }

                result = this.weights.get(responsible.get(wrongVal)) - (wrongVal - correctVal);
                break;
            }
        }
        return result;
    }
}
