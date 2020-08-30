package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day_01 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(1);
        return pointDistance(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(1);
        part2 = true;
        return pointDistance(input);
    }

    boolean part2 = false;

    int pointDistance(String input) {
        int x = 0;
        int y = 0;
        int heading = 0;
        Pair<Character, Integer>[] modifiers = new Pair[]{
                new Pair('y', 1),
                new Pair('x', 1),
                new Pair('y', -1),
                new Pair('x', -1)
        };
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        for (String cmd : input.split(", ")) {
            visited.add(new Pair(x, y));
            var dir = cmd.charAt(0) + "";
            int amount = Integer.parseInt(cmd.substring(1));
            heading = Math.floorMod(heading + (dir.equals("L") ? -1 : 1), 4);
            for (int i = 0; i < amount; i++) {
                if (modifiers[heading].first == 'y')
                    y += modifiers[heading].second;
                else x  += modifiers[heading].second;

                var newPos = new Pair(x, y);
                if (part2 && visited.contains(newPos))
                    return Math.abs(x) + Math.abs(y);
                else visited.add(newPos);

            }
        }

        return Math.abs(x) + Math.abs(y);
    }
}
