package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.*;

public class Day_12 implements Day {

    public Object part1() throws IOException {
        String input = readDay(12);
        return findConnectionSize(input);
    }

    public Object part2() throws IOException {
        String input = readDay(12);
        return findDistinctSets(input);
    }


    Set<String> visited = new HashSet<>();
    Map<String, List<String>> neighbors = new HashMap<>();

    private void instantiateConnections(String input) {
        this.neighbors = new HashMap<>();
        this.visited = new HashSet<>();

        for (String row : input.split("\n")) {
            String[] elems = row.split("<->");
            String left = elems[0].trim();
            for (String right : elems[1].split(",")) {
                right = right.trim();
                List<String> rightNeighbours = this.neighbors.getOrDefault(right, new ArrayList<>());
                List<String> leftNeighbours = this.neighbors.getOrDefault(left, new ArrayList<>());
                rightNeighbours.add(left);
                leftNeighbours.add(right);
                this.neighbors.put(right, rightNeighbours);
                this.neighbors.put(left, leftNeighbours);
            }
        }
    }

    private int findConnectionSize(String input) {
        instantiateConnections(input);
        bfs("0");
        return this.visited.size();
    }

    private int findDistinctSets(String input) {
        instantiateConnections(input);
        int totalDistinct = 0;

        for (String node : this.neighbors.keySet()) {
            if (this.visited.contains(node))
                continue;
            bfs(node);
            totalDistinct++;
            if (this.neighbors.keySet().size() == this.visited.size())
                return totalDistinct;
        }

        return totalDistinct;
    }

    private void bfs(String start) {
        Queue<String> que = new LinkedList<>();
        que.add(start);
        this.visited.add(start);

        while (que.size() > 0) {
            String curr = que.poll();
            for (String neigh : this.neighbors.getOrDefault(curr, new ArrayList<>())) {
                if (!this.visited.contains(neigh)) {
                    this.visited.add(neigh);
                    que.add(neigh);
                }
            }
        }

    }
}
