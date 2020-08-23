package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.Arrays;

public class Day_18 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(18);
        part2 = false;
        return gifLights(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(18);
        part2 = true;
        return gifLights(input);
    }

    char[][] lightGrid = new char[100][100];
    boolean part2 = false;

    void initializeGrid(String input) {
        String[] lines = input.split("\n");
        for (int y = 0; y < lightGrid.length; y++) {
            for (int x = 0; x < lightGrid.length; x++) {
                lightGrid[y][x] = lines[y].charAt(x);
            }
        }
        if (part2) {
            lightGrid[0][lightGrid.length - 1] = '#';
            lightGrid[lightGrid.length - 1][lightGrid.length - 1] = '#';
            lightGrid[0][0] = '#';
            lightGrid[lightGrid.length - 1][0] = '#';
        }

    }

    void gridStep() {
        char[][] newGrid = Arrays.stream(lightGrid).map(char[]::clone).toArray(char[][]::new);

        for (int y = 0; y < lightGrid.length; y++) {
            for (int x = 0; x < lightGrid.length; x++) {
                if (part2) {
                    if ((y == 0 && x == 0) || (y == 0 && x == lightGrid.length - 1) || (y == lightGrid.length - 1 && x == lightGrid.length - 1) || (y == lightGrid.length - 1 && x == 0))
                        continue;
                }
                int neighCount = countNeighbors(y, x);
                if (lightGrid[y][x] == '#') {
                    if (neighCount < 2 || neighCount > 3)
                        newGrid[y][x] = '.';
                } else {
                    if (neighCount == 3)
                        newGrid[y][x] = '#';
                }
            }
        }
        lightGrid = newGrid;
    }

    private char getPoint(int y, int x) {
        if ((y < 0) || (x < 0) || (y > (lightGrid.length - 1)) || (x > (lightGrid.length - 1)))
            return '.';
        return lightGrid[y][x];
    }

    private int countNeighbors(int y, int x) {
        int total = 0;
        for (int ny = y - 1; ny <= y+1; ny++) {
            for (int nx = x - 1; nx <= x+1; nx++) {
                if ((nx == x) && (ny == y)) {
                    continue;
                }
                total += getPoint(ny, nx) == '#' ? 1 : 0;
            }
        }
        return total;
    }

    void printGrid() {
        for (int y = 0; y < lightGrid.length; y ++) {
            for (int x = 0; x < lightGrid.length; x++) {
                System.out.print(lightGrid[y][x]);
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    int gifLights(String input) {
        initializeGrid(input);
        for (int i = 0; i < 100; i++) {
            gridStep();
        }

        return countActive();
    }

    private int countActive() {
        int total = 0;

        for (int y = 0; y < lightGrid.length; y ++) {
            for (int x = 0; x < lightGrid.length; x++) {
                if (lightGrid[y][x] == '#')
                    total += 1;
            }
        }

        return total;
    }
    
}
