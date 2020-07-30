package aoc.misc;

import java.io.IOException;

public interface Day extends FileOperator {
    public Object part1() throws IOException;
    public Object part2() throws IOException;

    public default void printParts() throws IOException {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }
}

