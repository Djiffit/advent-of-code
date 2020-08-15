package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day_14 implements Day {

    public Object part1() throws IOException {
        String input = readDay(14);
        return countActiveBits("hxtvlmkl");
    }

    public Object part2() throws IOException {
        return findNumberOfRegions();
    }

    List<char[]> binaries = new ArrayList<>();

    void dfs(int y, int x) {
        binaries.get(y)[x] = '2';
        if (y > 0 && binaries.get(y - 1)[x] == '1')
            dfs(y - 1, x);
        if (y < 127 && binaries.get(y + 1)[x] == '1')
            dfs(y + 1, x);
        if (x > 0 && binaries.get(y)[x - 1] == '1')
            dfs(y, x - 1);
        if (x < 127 && binaries.get(y)[x + 1] == '1')
            dfs(y, x + 1);
    }

    int findNumberOfRegions() {
        int currNum = 0;

        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 128; x++) {
                if (binaries.get(y)[x] == '1') {
                    dfs(y, x);
                    currNum += 1;
                }
            }
        }

        return currNum;
    }

    int countActiveBits(String input) {
        int total = 0;
        Day_10 knotter = new Day_10();

        for (int i = 0; i < 128; i ++) {
            String curr = input + '-' + i;
            String knot = knotter.twist64(curr);
            StringBuilder binary = new StringBuilder();

            for (char c : knot.toCharArray()) {
                String append = new BigInteger("" + c, 16).toString(2);
                while (append.length() < 4)
                    append = " " + append;
                binary.append(append);
            }

            for (char c : binary.toString().toCharArray())
                if (c == '1')
                    total += 1;

            binaries.add(binary.toString().toCharArray());
        }

        return total;
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
