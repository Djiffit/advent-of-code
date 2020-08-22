package aoc2015.days;

import aoc2015.misc.Day;
import javafx.geometry.Point2D;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day_03 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(3);
        return getSantaPositions(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(3);
        return getRoboSantaPositions(input);
    }

    int getSantaPositions(String input) {
        Set<Point2D> visits = new HashSet<>();
        Point2D pos = new Point2D(0, 0);
        visits.add(pos);

        for (char c : input.toCharArray()) {
            if (c == '<')
                pos = pos.add(-1, 0);
            if (c == '>')
                pos = pos.add(1, 0);
            if (c == '^')
                pos = pos.add(0, -1);
            if (c == 'v')
                pos = pos.add(0, 1);
            visits.add(pos);
        }

        return visits.size();
    }

    int getRoboSantaPositions(String input) {
        Set<Point2D> visits = new HashSet<>();
        Point2D santaPos = new Point2D(0, 0);
        Point2D roboPos = new Point2D(0, 0);

        visits.add(santaPos);
        boolean santa = true;

        for (char c : input.toCharArray()) {
            Point2D pos = santa ? santaPos : roboPos;

            if (c == '<')
                pos = pos.add(-1, 0);
            if (c == '>')
                pos = pos.add(1, 0);
            if (c == '^')
                pos = pos.add(0, -1);
            if (c == 'v')
                pos = pos.add(0, 1);
            visits.add(pos);

            if (santa)
                santaPos = pos;
            else
                roboPos = pos;

            santa = !santa;
        }
        return visits.size();
    }
}
