package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.HashMap;

public class Day_23 implements Day {

    public Object part1() throws IOException {
        String input = readDay(23);
        return countMultiplies(input);
    }

    public Object part2() throws IOException {
        String input = readDay(23);
        return findPrimes();
    }

    boolean isPrime(int number) {
        for (int i = 2; i <= number / 2; i++)
            if (number % i == 0)
                return false;
        return true;
    }

    int findPrimes() {
        int b = 57 * 100 + 100000;
        int c = b + 17000;
        int total = 0;
        for (int i = b; i <= c; i += 17) {
            if (!isPrime(i))
                total += 1;
        }
        return total;
    }

    HashMap<String, Long> regs;
    Long mulCounts = 0L;

    long getValue(String param) {
        try {
            return Long.parseLong(param);
        } catch (Exception e) {
            return this.regs.getOrDefault(param, 0L);
        }
    }

    void setReg(String x, long y) {
        this.regs.put(x, y);
    }

    void subReg(String x, long y) {
        this.regs.put(x, this.regs.getOrDefault(x, 0L) - y);
    }

    void mulReg(String x, long y) {
        this.regs.put(x, this.regs.getOrDefault(x, 0L) * y);
        mulCounts += 1;
    }

    long countMultiplies(String input) {
        this.regs = new HashMap<>();
        long pos = 0L;
        String[] commands = input.split("\n");

        while ((int) pos < commands.length) {
            String[] currCmd = commands[(int) pos].split(" ");
            switch (currCmd[0]) {
                case "jnz":
                    if (getValue(currCmd[1]) != 0) {
                        pos += (getValue(currCmd[2]));
                        continue;
                    } else
                        break;
                case "set":
                    setReg(currCmd[1], getValue(currCmd[2]));
                    break;
                case "mul":
                    mulReg(currCmd[1], getValue(currCmd[2]));
                    break;
                case "sub":
                    subReg(currCmd[1], getValue(currCmd[2]));
                    break;
            }

            pos += 1;
        }

        return mulCounts;
    }
}
