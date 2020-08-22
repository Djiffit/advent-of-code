package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_14 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(14);
        return solvePartOne(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(14);
        return solvePartTwo(input);
    }

    class Reindeer {
        int speed;
        int burstDuration;
        String name;
        int restTime;
        int points = 0;

        public Reindeer(int speed, int burstDuration, int restTime, String name) {
            this.speed = speed;
            this.burstDuration = burstDuration;
            this.restTime = restTime;
            this.name = name;
        }

        public int getDistanceAt(int seconds) {
            int total = burstDuration + restTime;
            int cycles = Math.floorDiv(seconds, total);
            int extra = 0;
            if (seconds % total < burstDuration)
                extra = (seconds % total) * speed;
            else
                extra = speed * burstDuration;
            return cycles * burstDuration * speed + extra;
        }

        @Override
        public String toString() {
            return (name + " " + speed + " " + burstDuration + "  " + restTime);
        }
    }
    List<Reindeer> reindeer;

    void readReindeer(String input) {
        for (String row : input.split("\n")) {
            String[] rowElems = row.split(" ");
            reindeer.add(new Reindeer(
                    Integer.parseInt(rowElems[3]),
                    Integer.parseInt(rowElems[6]),
                    Integer.parseInt(rowElems[13]),
                    rowElems[0]
            ));
        }
    }

    int solvePartOne(String input) {
        reindeer = new ArrayList<>();
        readReindeer(input);
        int maxDist = 0;
        for (Reindeer deer : reindeer) {
            maxDist = Math.max(maxDist, deer.getDistanceAt(2503));
        }
        return maxDist;
    }

    int solvePartTwo(String input) {
        reindeer = new ArrayList<>();
        readReindeer(input);
        int bestScore = 0;

        for (int i = 1; i < 2504; i++) {
            int bestDist = 0;

            for (Reindeer rd : reindeer)
                bestDist = Math.max(bestDist, rd.getDistanceAt(i));

            for (Reindeer rd : reindeer)
                if (rd.getDistanceAt(i) == bestDist)
                    rd.points += 1;
        }

        for (Reindeer rd : reindeer)
            bestScore = Math.max(bestScore, rd.points);

        return bestScore;
    }
}
