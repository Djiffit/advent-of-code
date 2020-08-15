package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;

public class Day_15 implements Day {

    public Object part1() throws IOException {
        String input = readDay(15);
        return countMatches(input, 40000000, 1, 1);
    }

    public Object part2() throws IOException {
        String input = readDay(15);
        return countMatches(input, 5000000, 4, 8);
    }


    final long mask = 65535;
    Generator genA;
    Generator genB;

    public class Generator {
        long value;
        long multiplier;
        final long mod = 2147483647;
        int picky = 1;

        Generator(int start, int multiplier, int picky) {
            this.value = start;
            this.multiplier = multiplier;
            this.picky = picky;
        }

        long createNext() {
            do {
                long next = (value * multiplier) % mod;
                this.value = next;
            } while (this.value % this.picky != 0);

            return this.value;
        }
    }

    private void readGenerators(String input, int Amod, int Bmod) {
        String[] rows = input.split("\n");
        this.genA = new Generator(Integer.parseInt(rows[0].split("with ")[1]), 16807, Amod);
        this.genB = new Generator(Integer.parseInt(rows[1].split("with ")[1]), 48271, Bmod);
    }

    private int countMatches(String input, int rounds, int Amod, int Bmod) {
        readGenerators(input, Amod, Bmod);
        int totalMatches = 0;

        for (int i = 0; i < rounds; i++) {
            long Aval = this.genA.createNext();
            long Bval = this.genB.createNext();
            if ((Aval & this.mask) == (Bval & this.mask))
                totalMatches++;
        }

        return totalMatches;
    }

}
