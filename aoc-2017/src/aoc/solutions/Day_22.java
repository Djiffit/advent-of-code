package aoc.solutions;

import aoc.misc.Day;
import javafx.geometry.Point2D;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day_22 implements Day {

    public Object part1() throws IOException {
        String input = readDay(22);
        return countInfections(input, 10000, false);
    }

    public Object part2() throws IOException {
        String input = readDay(22);
        return countInfections(input, 10000000, true);
    }

    enum Status {
        INFECTED, WEAK, FLAGGED, CLEAN
    }

    class Node {
        Status status;
        boolean complex;

        public Node(char curr, boolean complex) {
            status = curr == '.' ? Status.CLEAN : Status.INFECTED;
            this.complex = complex;
        }

        void evolve() {
            if (!complex)
                status = status == Status.CLEAN ? Status.INFECTED : Status.CLEAN;
            else {
                switch (status) {
                    case WEAK:
                        status = Status.INFECTED;
                        break;
                    case INFECTED:
                        status = Status.FLAGGED;
                        break;
                    case FLAGGED:
                        status = Status.CLEAN;
                        break;
                    case CLEAN:
                        status = Status.WEAK;
                        break;
                }
            }
        }
    }

    class Robot {
        Point2D currPos;
        boolean complex;
        Point2D[] deltas = {new Point2D(0, 1), new Point2D(1, 0), new Point2D(0, -1), new Point2D(-1, 0)};
        int currMove = 2;
        long infCount = 0;

        public Robot(int y, int x, boolean complex) {
            currPos = new Point2D(x, y);
            this.complex = complex;
        }

        int getDirection(Status status) {
            switch (status) {
                case WEAK:
                    return currMove;
                case INFECTED:
                    return currMove - 1;
                case FLAGGED:
                    return currMove + 2;
                default:
                    return currMove + 1;
            }
        }

        void burst(Map<Point2D, Node> map) {
            Node currNode = map.getOrDefault(currPos, new Node('.', complex));
            this.currMove = Math.floorMod(getDirection(currNode.status), 4);
            currNode.evolve();
            map.put(this.currPos, currNode);
            if (currNode.status == Status.INFECTED)
                this.infCount += 1;
            this.currPos = this.currPos.add(deltas[currMove]);
        }
    }

    String countInfections(String input, int steps, boolean complex) {
        Map<Point2D, Node> map = new HashMap<>();
        String[] rows = input.split("\n");
        Robot robo = new Robot(rows.length / 2, rows[0].length() / 2, complex);

        for (int y = 0; y < rows.length; y++)
            for (int x = 0; x < rows[0].length(); x++)
                map.put(new Point2D(x, y), new Node(rows[y].charAt(x), complex));

        for (int i = 0; i < steps; i++)
            robo.burst(map);

        return robo.infCount + "";
    }
}
