package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import java.util.*;

public class Day_19 implements Day {

    public Object part1() throws IOException {
        String input = readDay(13);
        return collectLetters(input, false);
    }

    public Object part2() throws IOException {
        String input = readDay(13);
        return collectLetters(input, true);
    }

    char getCharByPoint(Point2D currPoint, String[] rows) {
        return rows[(int) currPoint.getY()].charAt((int) currPoint.getX());
    }

    Point2D createPoint(Point2D a, Point2D b) {
        return new Point2D(a.getX() + b.getX(), a.getY() + b.getY());
    }

    String collectLetters(String input, boolean part2) {
        StringBuilder letters = new StringBuilder();
        String[] rows = input.split("\n");
        int colIndex = rows[0].indexOf("|");

        Point2D currPoint = new Point2D(colIndex, 0);

        Point2D[] deltas = {new Point2D(0, 1), new Point2D(1, 0), new Point2D(0, -1), new Point2D(-1, 0)};
        char[] symbols = {'|', '-', '|', '-'};
        int deltaIndex = 0;
        int totalSteps = 0;

        while (true) {
            char currChar = getCharByPoint(currPoint, rows);

            if (currChar == '+') {
                Point2D turnLeft = createPoint(deltas[Math.floorMod(deltaIndex + 1, 4)], currPoint);
                if (getCharByPoint(turnLeft, rows) == symbols[Math.floorMod(deltaIndex + 1, 4)]
                        || Character.isAlphabetic(getCharByPoint(turnLeft, rows)))
                    deltaIndex = Math.floorMod(deltaIndex + 1, 4);
                else
                    deltaIndex = Math.floorMod(deltaIndex - 1, 4);
            } else if (Character.isAlphabetic(currChar))
                letters.append(currChar);
            else if (currChar == ' ')
                break;

            currPoint = createPoint(currPoint, deltas[deltaIndex]);
            totalSteps += 1;
        }

        if (part2)
            return totalSteps + "";

        return letters.toString();
    }

}
