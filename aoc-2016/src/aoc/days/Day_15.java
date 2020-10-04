package aoc.days;

import aoc.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_15 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(15);
        return findDirectPath(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(15);
        part2 = true;
        return findDirectPath(input);
    }

    boolean part2 = false;

    String findDirectPath(String input) {
        var discs = createDiscs();

        for (int i = 0; i < 1000000000; i++) {
            int target_pos = Math.floorMod(discs.get(0).curr + i + 1, discs.get(0).positions);
            boolean result = true;
            for (int d = 0; d < discs.size(); d++) {
               if (Math.floorMod(discs.get(d).curr + i + 1 + d, discs.get(d).positions) != target_pos) {
                   result = false;
                   break;
               }
            }
            if (result)
                return "" + i;
        }

        return "";
    }

    class Disc {
        int curr;
        int positions;

        public Disc(int start, int positions) {
            this.curr =start;
            this.positions = positions;
        }

        public void move() {
            this.curr = Math.floorMod(this.curr + 1, positions);
        }
    }

    List<Disc> createDiscs() {
        List<Disc> discs = new ArrayList<>();
        discs.add(new Disc(2, 5));
        discs.add(new Disc(7, 13));
        discs.add(new Disc(10,17));
        discs.add(new Disc(2, 3));
        discs.add(new Disc(9, 19));
        discs.add(new Disc(0, 7));
        if (part2)
            discs.add(new Disc(0, 11));
        return discs;
    }
}
