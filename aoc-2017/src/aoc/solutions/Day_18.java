package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.HashMap;

import java.util.*;

public class Day_18 implements Day {

    public Object part1() throws IOException {
        String input = readDay(18);
        Day_18 d1 = new Day_18();
        return d1.getLatestSound(input);
    }

    public Object part2() throws IOException {
        String input = readDay(18);
        Day_18 d2 = new Day_18();
        Day_18 d1 = new Day_18();
        d1.registers.put("p", 1L);

        simulateUntilBlock(d1, input);
        simulateUntilBlock(d2, input);
        while (d1.queue.size() > 0 || d2.queue.size() > 0) {
            d1.simulateUntilBlock(d1, input);
            d2.simulateUntilBlock(d2, input);
        }

        return d2.sendCounter / 2;
    }

    long latestSound = 0L;
    long pos = 0L;
    long sendCounter = 0L;
    Map<String, Long> registers = new HashMap<>();
    Queue<Long> queue = new LinkedList<>();

    void simulateUntilBlock(Day_18 other, String input) {
        String[] commands = input.split("\n");

        while (true) {
            String[] currCmd = commands[(int) pos].split(" ");

            if (currCmd[0].equals("jgz")) {
                if (isNumber(currCmd[1])) {
                    pos = jump(Long.parseLong(currCmd[1]), Long.parseLong(currCmd[2]), pos);
                } else if (isNumber(currCmd[2])) {
                    pos = jump(currCmd[1], Long.parseLong(currCmd[2]), pos);
                } else {
                    pos = jump(currCmd[1], currCmd[2], pos);
                }
            } else {
                switch (currCmd[0]) {
                    case "set":
                        if (isNumber(currCmd[2])) {
                            set(currCmd[1], Long.parseLong(currCmd[2]));
                        } else {
                            set(currCmd[1], currCmd[2]);
                        }
                        break;
                    case "mul":
                        if (isNumber(currCmd[2])) {
                            mul(currCmd[1], Long.parseLong(currCmd[2]));
                        } else {
                            mul(currCmd[1], currCmd[2]);
                        }
                        break;
                    case "add":
                        if (isNumber(currCmd[2])) {
                            add(currCmd[1], Long.parseLong(currCmd[2]));
                        } else {
                            add(currCmd[1], currCmd[2]);
                        }
                        break;
                    case "mod":
                        if (isNumber(currCmd[2])) {
                            mod(currCmd[1], Long.parseLong(currCmd[2]));
                        } else {
                            mod(currCmd[1], currCmd[2]);
                        }
                        break;
                    case "snd":
                        this.sendCounter += 1;
                        if (isNumber(currCmd[1]))
                            other.queue.add(Long.parseLong(currCmd[1]));
                        else
                            other.queue.add(this.registers.getOrDefault(currCmd[1], 0L));
                        break;
                    case "rcv":
                        if (this.queue.size() == 0)
                            return;
                        this.registers.put(currCmd[1], this.queue.poll());
                        break;
                }

                pos += 1;
            }
        }
    }

    long getLatestSound(String input) {
        long pos = 0L;
        String[] commands = input.split("\n");

        while (true) {
            String[] currCmd = commands[(int) pos].split(" ");

            if (currCmd[0].equals("jgz")) {
                if (isNumber(currCmd[1])) {
                    pos = jump(Long.parseLong(currCmd[1]), Long.parseLong(currCmd[2]), pos);
                } else if (isNumber(currCmd[2])) {
                    pos = jump(currCmd[1], Long.parseLong(currCmd[2]), pos);
                } else {
                    pos = jump(currCmd[1], currCmd[2], pos);
                }
            } else {
                switch (currCmd[0]) {
                    case "set":
                        if (isNumber(currCmd[2])) {
                            set(currCmd[1], Long.parseLong(currCmd[2]));
                        } else {
                            set(currCmd[1], currCmd[2]);
                        }
                        break;
                    case "mul":
                        if (isNumber(currCmd[2])) {
                            mul(currCmd[1], Long.parseLong(currCmd[2]));
                        } else {
                            mul(currCmd[1], currCmd[2]);
                        }
                        break;
                    case "add":
                        if (isNumber(currCmd[2])) {
                            add(currCmd[1], Long.parseLong(currCmd[2]));
                        } else {
                            add(currCmd[1], currCmd[2]);
                        }
                        break;
                    case "mod":
                        if (isNumber(currCmd[2])) {
                            mod(currCmd[1], Long.parseLong(currCmd[2]));
                        } else {
                            mod(currCmd[1], currCmd[2]);
                        }
                        break;
                    case "snd":
                        sound(currCmd[1]);
                        break;
                    case "rcv":
                        if (recover(currCmd[1]))
                            return this.latestSound;
                        break;
                }

                pos += 1;
            }
        }
    }

    boolean isNumber(String x) {
        try {
            Long.parseLong(x);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    void sound(String x) {
        this.latestSound = registers.getOrDefault(x, 0L);
    }

    void set(String x, long y) {
        this.registers.put(x, y);
    }

    void set(String x, String y) {
        this.registers.put(x, this.registers.getOrDefault(y, 0L));
    }

    void add(String x, String y) {
        this.registers.put(x, this.registers.getOrDefault(x, 0L) + this.registers.getOrDefault(y, 0L));
    }

    void add(String x, long y) {
        this.registers.put(x, this.registers.getOrDefault(x, 0L) + y);
    }

    void mul(String x, String y) {
        this.registers.put(x, this.registers.getOrDefault(x, 0L) * this.registers.getOrDefault(y, 0L));
    }

    void mul(String x, long y) {
        this.registers.put(x, this.registers.getOrDefault(x, 0L) * y);
    }

    void mod(String x, String y) {
        this.registers.put(x, Math.floorMod(this.registers.getOrDefault(x, 0L), this.registers.getOrDefault(y, 0L)));
    }

    void mod(String x, long y) {
        this.registers.put(x, Math.floorMod(this.registers.getOrDefault(x, 0L), y));
    }

    boolean recover(String x) {
        return this.registers.getOrDefault(x, 0L) != 0L;
    }

    long jump(String x, long move, long i) {
        if (this.registers.getOrDefault(x, 0L) > 0L) {
            return i + move;
        }
        return i + 1;
    }

    long jump(String x, String y, long i) {
        if (this.registers.getOrDefault(x, 0L) > 0L && this.registers.getOrDefault(y, 0L) != 0L) {
            return this.registers.getOrDefault(y, 0L) + i;
        }
        return i + 1;
    }

    long jump(long x, long y, long i) {
        if (x > 0L && y != 0L) {
            return y + i;
        }
        return i + 1;
    }

}
