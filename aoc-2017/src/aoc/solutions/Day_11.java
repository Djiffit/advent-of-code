package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.*;

public class Day_11 implements Day {

    Coords coords;

    public Object part1() throws IOException {
        String input = readDay(11);
        simulateSteps(input);
        return this.getDistance(true);
    }

    public Object part2() throws IOException {
        return this.recordDist;
    }

    class Coords {
        public int x;
        public int y;
        public int dist = 0;

        public Coords() {
            this.x = 0;
            this.y = 0;
        }

        public Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coords(Coords coords) {
            this.x = coords.x;
            this.y = coords.y;
            this.dist = coords.dist + 1;
        }

        Coords move(String cmd) {
            switch (cmd) {
                case "ne":
                    x += 2;
                    y += 1;
                    break;
                case "nw":
                    x -= 2;
                    y += 1;
                    break;
                case "n":
                    y += 2;
                    break;
                case "sw":
                    x -= 2;
                    y -= 1;
                    break;
                case "s":
                    y -= 2;
                    break;
                case "se":
                    y -= 1;
                    x += 2;
                    break;
            }
            return this;
        }
    }

    int recordDist = 0;

    int getDistance(boolean findTarget) {
        Queue<Coords> que = new LinkedList<>();
        que.add(new Coords());
        Map<Integer, Map<Integer, Integer>> hist = new HashMap<>();

        while (que.size() > 0) {
            Coords curr = que.poll();
            Map<Integer, Integer> first = hist.getOrDefault(curr.y, new HashMap<>());
            first.put(curr.x, curr.dist);
            for (String cmd : new String[] {"ne", "nw", "n", "sw", "s", "se"}) {
                Coords newCoords = new Coords(curr).move(cmd);
                if (findTarget && newCoords.x == this.coords.x && newCoords.y == this.coords.y)
                    return newCoords.dist;

                if (!hist.containsKey(newCoords.y) || !hist.get(newCoords.y).containsKey(newCoords.x)) {
                    que.add(newCoords);
                    first = hist.getOrDefault(newCoords.y, new HashMap<>());
                    first.put(newCoords.x, newCoords.dist);
                    hist.put(newCoords.y, first);
                }
            }
        }
        return -1;
    }

    int countDist(Coords coords) {
        int x = Math.abs(coords.x);
        int y = Math.abs(coords.y);
        if (y >= x) {
            y -= x / 2;
            return x / 2 + y / 2;
        }
        x -= y * 2;
        return y + x / 2;
    }

    void simulateSteps(String input) {

        this.coords = new Coords();
        for (String cmd : input.split(",")) {
            coords.move(cmd);

            int newDist = countDist(coords);
            this.recordDist = Math.max(this.recordDist, newDist);
        }

    }
}
