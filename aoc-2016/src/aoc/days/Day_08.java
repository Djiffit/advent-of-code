package aoc.days;

import aoc.misc.Day;

import java.io.IOException;

public class Day_08 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(8);
        return twoFactorAuth(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(8);
        part2 = true;
        return twoFactorAuth(input);
    }

    char[][] grid = new char[6][50];
    boolean part2 = false;

    void init() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                grid[y][x] = '.';
            }
        }
    }

    void rect(int a, int b) {
        for (int y = 0; y < b; y++) {
            for (int x = 0; x < a; x++) {
                grid[y][x] = '#';
            }
        }
    }

    void rotateRow(int row, int times) {
        for (int i = 0; i < times; i++) {
            char temp = grid[row][grid[0].length - 1];
            System.arraycopy(grid[row], 0, grid[row], 1, grid[0].length - 1);
            grid[row][0] = temp;
        }
    }

    void rotateCol(int col, int times) {
        for (int i = 0; i < times; i++) {
            char temp = grid[grid.length - 1][col];
            for (int y = grid.length - 1; y > 0; y--) {
                grid[y][col] = grid[y - 1][col];
            }
            grid[0][col] = temp;
        }
    }

    int countLit() {
        int total = 0;
        for (char[] chars : grid) {
            for (int x = 0; x < grid[0].length; x++) {
                if (chars[x] == '#')
                    total += 1;
            }
        }
        return total;
    }

    String twoFactorAuth(String input) {
        init();

        for (String row : input.split("\n")) {
            if (row.startsWith("rect")) {
                var parts = row.split(" ")[1].split("x");
                rect(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } else if (row.startsWith("rotate row")) {
                var parts = row.split("row ")[1].split(" by ");
                rotateRow(Integer.parseInt(parts[0].substring(2)), Integer.parseInt(parts[1]));
            } else {
                var parts = row.split("column ")[1].split(" by ");
                rotateCol(Integer.parseInt(parts[0].substring(2)), Integer.parseInt(parts[1]));
            }
        }

        if (part2)
            return printGrid();
        return "" + countLit();
    }

    private String printGrid() {
        StringBuilder sb = new StringBuilder("\n");

        for (char[] chars : grid) {
            for (int x = 0; x < grid[0].length; x++)
                sb.append(chars[x]);
            sb.append("\n");
        }

        return sb.toString();
    }

}
