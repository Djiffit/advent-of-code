package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.*;

public class Day_22 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(22);
        return solvePartOne(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(22);
        return solvePartTwo(input);
    }

    class Node {
        int size;
        int used;
        int avail;
        Pair<Integer, Integer> pos;

        public Node(int size, int used, int avail, Pair<Integer, Integer> pos) {
            this.size = size;
            this.used = used;
            this.avail = avail;
            this.pos = pos;
            assert(used <= size && avail >= 0);
            if (!(used <= size && avail >= 0)) {
                System.out.println("FAILED");
                //throw new Error("FAILED tocreate proper thing");
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "size=" + size +
                    ", used=" + used +
                    ", avail=" + avail +
                    ", pos=" + pos +
                    '}';
        }

        public Node setData(int newData) {
            return new Node(this.size, newData, this.size - newData, this.pos);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return size == node.size &&
                    used == node.used &&
                    avail == node.avail &&
                    pos.equals(node.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(size, used, avail, pos);
        }
    }

    String solvePartOne(String input) {
        List<Node> nodes = getNodes(input);
        int validPairs = 0;

        for (var n1 : nodes) {
            for (var n2 : nodes) {
                if (n1 == n2)
                    continue;
                if (n2.avail >= n1.used && n1.used > 0)
                    validPairs += 1;
            }
        }
        return "" + validPairs;
    }

    private List<Node> getNodes(String input) {
        List<Node> nodes = new ArrayList<>();
        for (String line : input.split("\n")) {
            String[] params = line.split("\\s+");
            String[] name = params[0].split("-");
            Pair<Integer, Integer> pos = new Pair(Integer.parseInt(name[1].substring(1)), Integer.parseInt(name[2].substring(1)));
            int size = Integer.parseInt(params[1].substring(0, params[1].length() - 1));
            int used = Integer.parseInt(params[2].substring(0, params[2].length() - 1));
            int avail = Integer.parseInt(params[3].substring(0, params[3].length() - 1));
            nodes.add(new Node(size, used, avail, pos));
        }
        return nodes;
    }

    boolean canMove(Node a, Node b) {
       return (b.avail >= a.used + b.used && a.used > 0);
    }

    List<Pair<Integer, Integer>> validSurroundingChanges(Map<Pair<Integer, Integer>, Node> grid, Node curr) {
        var pos = curr.pos;
        Pair<Integer, Integer>[] pairs = new Pair[]{
                new Pair(pos.first, pos.second - 1),
                new Pair(pos.first, pos.second + 1),
                new Pair(pos.first - 1, pos.second),
                new Pair(pos.first + 1, pos.second)
        };
        List<Pair<Integer, Integer>> candidates = new ArrayList<>();
        for (var cand : pairs) {
            if (grid.containsKey(cand) && canMove(grid.get(pos), grid.get(cand)))
                candidates.add(grid.get(cand).pos);
        }
        return candidates;
    }

    Map<Pair<Integer, Integer>, Node> copyGrid(Map<Pair<Integer, Integer>, Node> grid) {
        Map<Pair<Integer, Integer>, Node> newGrid = new HashMap<>();

        for (var key : grid.keySet()) {
            var node = grid.get(key);
            newGrid.put(key, node.setData(node.used));
        }

        return newGrid;
    }


    Map<Pair<Integer, Integer>, Node> swapData(Map<Pair<Integer, Integer>, Node> grid, Node a, Node b) {
        Map<Pair<Integer, Integer>, Node> newGrid = new HashMap<>();
        int origsum = 0;
        for (var key : grid.keySet()) {
            var node = grid.get(key);
            newGrid.put(key, node.setData(node.used));
            origsum += node.used;
        }
        var newNodeA = a.setData(0);
        var newNodeB = b.setData(b.used + a.used);
        newGrid.put(a.pos, newNodeA);
        newGrid.put(b.pos, newNodeB);

        int newsum = 0;
        for (var key : newGrid.keySet()) {
            var node = newGrid.get(key);
            newsum += Math.abs(node.used);
        }
        if (newsum != origsum ) {
            System.out.println("FAILED SWAP " +origsum + " " + newsum);
            System.out.println(a + " " + b);
            System.out.println(newNodeA + " " + newNodeB);
            printGrid(grid, a.pos, 0);
            printGrid(newGrid, b.pos, 0);
        }
        assert(newsum == origsum);
        return newGrid;
    }

    int XMAX = 34;
    int YMAX = 30;

    String solvePartTwo(String input) {
        Set<Pair<Map<Pair<Integer, Integer>, Node>, Pair<Integer, Integer>>> history = new HashSet<>();
        Map<Pair<Integer, Integer>, Node> grid = new HashMap<>();
        var nodes = getNodes(input);
        for (var node : nodes)
            grid.put(node.pos, node);

        Pair<Integer, Integer> target = new Pair(0, 0);
        Queue<Pair<Map<Pair<Integer, Integer>, Node>, Pair<Integer, Pair<Integer, Integer>>>> que = new LinkedList<>();
        que.add(new Pair<>(grid, new Pair<>(0, new Pair<>(XMAX - 1, 0))));

        while (que.size() > 0) {
            var curr = que.poll();
            var state = curr.first;
            var posPair = curr.second;
            var position = posPair.second;
            var dist = posPair.first;
            var currNode = state.get(position);
            if (position.equals(target) || dist > 11)
                return "" + dist;
            if (history.contains(new Pair<>(state, position))) {
                continue;
            }
            history.add(new Pair(state, position));
            for (int x = 0; x < XMAX; x++) {
                for (int y = 0; y < YMAX; y++) {
                    var node = grid.get(new Pair(x, y));
                    var currCands = validSurroundingChanges(state, node);
                    for (var candPos : currCands) {
                        var cgrid = copyGrid(state);
                        var candNode = state.get(candPos);
                        var newGrid = swapData(cgrid, cgrid.get(node.pos), cgrid.get(candNode.pos));
                        //System.out.println(node.pos == position ? "WHAT THE HELLP" : "");
                        que.add(new Pair(newGrid, new Pair<>(dist + 1, node.pos == currNode.pos ? candNode.pos : currNode.pos)));
                        //System.out.println("MOVED " + node.pos + " " + candNode.pos + " " + (node.pos == currNode.pos ? candNode.pos : position));
                        //printGrid(newGrid, node.pos == currNode.pos ? candNode.pos : position, dist + 1);
                    }
                }
            }
        }
        return "success";
    }

    void printGrid(Map<Pair<Integer, Integer>, Node> grid, Pair<Integer, Integer> curr, int dist) {
        System.out.println("\n--- DIST " + dist);
        for (int y = 0; y < YMAX; y++) {
            for (int x = 0; x < XMAX; x++) {
                var node = grid.get(new Pair(x, y));
                if (curr.equals(node.pos)) {
                    System.out.print("(" + node.used + " / " + node.size + ") -- ");
                } else
                    System.out.print(node.used + " / " + node.size + " -- ");
            }
            System.out.println();
        }
        System.out.println("--\n\n");
    }
}
