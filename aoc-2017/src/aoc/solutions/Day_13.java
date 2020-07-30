package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.HashMap;

public class Day_13 implements Day {

    public Object part1() throws IOException {
        String input = readDay(13);
        return countViolationScore(input, 0);
    }

    public Object part2() throws IOException {
        String input = readDay(13);
        return smallestDelay(input);
    }


    public class Scanner {
        public int severity;
        public int direction = 1;
        public int position = 0;

        Scanner(int severity) {
            this.severity = severity;
        }

        public void step() {
            this.position += direction;
            if (this.position == this.severity - 1 || this.position == 0)
                this.direction = this.direction == 1 ? -1 : 1;
        }
    }

    private HashMap<Integer, Scanner> scanners = new HashMap<>();

    private void readScanners(String input) {
        this.scanners = new HashMap<>();
        for (String row : input.split("\n")) {
            String[] data = row.split(": ");
            this.scanners.put(Integer.parseInt(data[0]), new Scanner(Integer.parseInt(data[1])));
        }
    }

    private int countViolationScore(String input, int wait) {
        readScanners(input);
        int position = -1;
        int totalSeverity = 0;

        for (int i = 0; i < wait; i ++) {
            for (Scanner s : this.scanners.values())
                s.step();
        }

        while (this.scanners.size() > 0) {
            position++;
            if (this.scanners.containsKey(position)) {
                if (this.scanners.get(position).position == 0)
                    totalSeverity += this.scanners.get(position).severity * position;
                this.scanners.remove(position);
            }
            for (Scanner s : this.scanners.values())
                s.step();
        }

        return totalSeverity;
    }

    private int smallestDelay(String input) {
        readScanners(input);
        for (int i = 0; i < 100000001; i++) {
            boolean valid = true;
            for (int key : this.scanners.keySet()) {
                int cycle = (this.scanners.get(key).severity - 1) * 2;
                if ((i + key) % cycle == 0) {
                    valid = false;
                    break;
                }
            }
            if (valid)
                return i;

        }
        return -1;
    }

}
