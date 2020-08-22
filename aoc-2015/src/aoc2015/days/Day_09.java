package aoc2015.days;

import aoc2015.misc.Day;
import aoc2015.misc.Pair;

import java.io.IOException;
import java.util.*;

public class Day_09 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(9);
        return shortestDistance(input, false);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(9);
        return shortestDistance(input, true);
    }

    Map<String, List<String>> connections = new HashMap<>();
    Map<Pair<String, String>, Integer> dist = new HashMap<>();
    Set<String> locations = new HashSet<>();

    void readConnections(String input, boolean part2) {
        for (String line : input.split("\n")) {
            String[] parts = line.split(" = ");
            String[] locations = parts[0].split(" to ");
            int distance = Integer.parseInt(parts[1]);
            List<String> connOne = connections.getOrDefault(locations[0], new ArrayList<>());
            List<String> connTwo = connections.getOrDefault(locations[1], new ArrayList<>());
            connOne.add(locations[1]);
            connTwo.add(locations[0]);
            dist.put(new Pair(locations[0], locations[1]), !part2 ? distance : -distance);
            dist.put(new Pair(locations[1], locations[0]), !part2 ? distance : -distance);
            connections.put(locations[0], connOne);
            connections.put(locations[1], connTwo);
            this.locations.add(locations[0]);
            this.locations.add(locations[1]);
        }
    }

    int findShortestPath(String curr, int dist, Set<String> visited) {
        int bestDist = Integer.MAX_VALUE;
        if (visited.size() == this.locations.size() - 1)
            return dist;
        visited.add(curr);

        for (String neigh : connections.get(curr))
            if (!visited.contains(neigh))
                bestDist = Math.min(bestDist, findShortestPath(neigh, dist + this.dist.get(new Pair(curr, neigh)), visited));

        visited.remove(curr);
        return bestDist;
    }

    int shortestDistance(String input, boolean part2) {
        readConnections(input, part2);
        int record = Integer.MAX_VALUE;

        for (String loc : this.locations)
            record = Math.min(record, findShortestPath(loc, 0, new HashSet<>()));

        return part2 ? -record : record;
    }

}
