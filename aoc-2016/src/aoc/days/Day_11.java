package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day_11 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(11);
        return solvePartOne(10);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(11);
        part2 = true;
        return solvePartOne(14);
    }

    boolean part2 = false;

    class Floor {
        List<Pair<Character, Character>> items;

        public Floor() {
            this.items = new ArrayList<>();
        }

        public Floor(List<Pair<Character, Character>> items) {
            this.items = new ArrayList<>();
            for (var i : items)
                this.items.add(i);
        }

        int gens() {
            int total = 0;
            for (var p : items) {
                if (p.second == 'G')
                    total += 1;
            }
            return total;
        }

        boolean hasGenerator(char c) {
            for (var p : items) {
                if (p.first == c && p.second == 'G')
                    return true;
            }
            return false;
        }

        boolean validateFloor() {
            int gens = gens();
            for (var p : items) {
                if (p.second == 'M' && (!hasGenerator(p.first) && gens > 0)) {
                    return false;
                }
            }
            return true;
        }

        void removeItems(Collection<Pair<Character, Character>> itemList) {
            for (var i : itemList) {
                this.items.remove(i);
            }
        }

        boolean addItem(Collection<Pair<Character, Character>> itemList) {
            items.addAll(itemList);

           if (!validateFloor()) {
               for (var i : itemList) {
                   items.remove(i);
               }
               return false;
           }

           return true;
        }

        public Floor clone() {
            return new Floor(this.items);
        }

    }

    List<Floor> createFloors() {
        List<Floor> res = new ArrayList<>();
        Floor floor1 = new Floor();
        Floor floor2 = new Floor();
        Floor floor3 = new Floor();
        Floor floor4 = new Floor();

        floor1.addItem(new ArrayList<Pair<Character, Character>>() {{
            add(new Pair<>('T', 'G'));
            add(new Pair<>('T', 'M'));
            add(new Pair<>('P', 'G'));
            add(new Pair<>('S', 'G'));
        }});

        floor2.addItem(new ArrayList<Pair<Character, Character>>() {{
            add(new Pair<>('P', 'M'));
            add(new Pair<>('S', 'M'));
        }});

        floor3.addItem(new ArrayList<Pair<Character, Character>>() {{
            add(new Pair<>('P', 'G'));
            add(new Pair<>('P', 'M'));
            add(new Pair<>('R', 'G'));
            add(new Pair<>('R', 'M'));
        }});
        if (part2) {
            floor1.addItem(new ArrayList<Pair<Character, Character>>() {{
                add(new Pair<>('E', 'G'));
                add(new Pair<>('E', 'M'));
                add(new Pair<>('D', 'G'));
                add(new Pair<>('D', 'M'));
            }});
        }
        res.add(floor1);
        res.add(floor2);
        res.add(floor3);
        res.add(floor4);

        return res;
    }

    List<Floor> copyList(List<Floor> floors) {
        List<Floor> newList = new ArrayList<>();

        for (var f : floors) {
            newList.add(f.clone());
        }

        return newList;
    }

    class PairComparator implements Comparator<Pair<Character, Character>> {

        @Override
        public int compare(Pair<Character, Character> o1, Pair<Character, Character> o2) {
            if (o1.first == o2.first)
                return o1.second.compareTo(o2.second);
            return o1.first.compareTo(o2.first);
        }
    }

    class StateCompartor implements Comparator<Pair<Pair<Integer, Integer>, List<Floor>>> {

        @Override
        public int compare(Pair<Pair<Integer, Integer>, List<Floor>> o1, Pair<Pair<Integer, Integer>, List<Floor>> o2) {
            int t1 = o1.second.get(3).items.size();
            int t2 = o2.second.get(3).items.size();
            return (o1.first.second + t1 * 10) - (o2.first.second + t2 * 10);

        }
    }

    String getState(List<Floor> floors, int floor) {
        StringBuilder sb = new StringBuilder();

        sb.append(floor);
        sb.append("\n");
        for (var f : floors) {
            sb.append("[");
            List<Pair<Character, Character>> pairs = new ArrayList<>(f.items);
            Collections.sort(pairs, new PairComparator());
            for (var i : pairs)
                sb.append(i);
            sb.append("]\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    int solvePartOne(int target) {
        PriorityQueue<Pair<Pair<Integer, Integer>, List<Floor>>> states = new PriorityQueue<>(new StateCompartor());
        states.add(new Pair<>(new Pair<>(0, 0), createFloors()));
        Set<String> history = new HashSet<>();
        int distance = 0;

        while (states.size() > 0) {
            var current = states.poll();
            int floor = current.first.first;
            int dist = current.first.second;

            if (dist > distance) {
                distance = dist;
                System.out.println("Now at bfs level " + distance);
            }
            var currFloor = current.second.get(floor);
            var curr = getState(current.second, floor);
            if (history.contains(curr))
                continue;
            history.add(curr);

            if (current.second.get(3).items.size() >= target)
                return dist;
            for (int it1 = 0; it1 < currFloor.items.size(); it1++) {
                for (int it2 = it1; it2 < currFloor.items.size(); it2++) {
                    var item = currFloor.items.get(it1);
                    var item2 = currFloor.items.get(it2);
                    for (int d = -1; d <= 1; d+= 2) {
                        if (floor + d < 0 || floor + d > 3 || (d == -1 &&current.second.get(floor + d).items.size() == 0))
                            continue;

                        var newState = copyList(current.second);
                        var newItems = Stream.of(item, item2).collect(Collectors.toCollection(HashSet::new));
                        newState.get(floor).removeItems(newItems);
                        var valid = newState.get(floor + d).addItem(newItems);
                        if (valid)
                            states.add(new Pair<>(new Pair<>(floor + d, dist + 1), newState));
                    }
                }
            }
        }
        return -1;
    }
}
