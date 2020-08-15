package aoc.solutions;

import aoc.misc.Day;
import javafx.geometry.Point2D;

import java.io.IOException;
import java.util.*;

public class Day_24 implements Day {

    public Object part1() throws IOException {
        String input = readDay(24);
        return (findStrongestBridge(input, true));
    }

    public Object part2() throws IOException {
        String input = readDay(24);
        return findStrongestBridge(input, false);
    }

    int bestStrength = 0;
    int maxLength = 0;
    HashMap<Integer, Integer> weightByLength = new HashMap<>();

    int findStrongestBridge(String input, boolean part1) {
        HashMap<Double, List<Point2D>> points = new HashMap<>();
        Set<Point2D> used = new HashSet<>();

        for (String lock : input.split("\n")) {
            String[] parts = lock.split("/");
            double a = Double.parseDouble(parts[0]);
            double b = Double.parseDouble(parts[1]);
            Point2D current = new Point2D(a, b);
            List<Point2D> aList = points.getOrDefault(a, new ArrayList<>());
            List<Point2D> bList = points.getOrDefault(b, new ArrayList<>());
            aList.add(current);
            bList.add(current);
            points.put(a, aList);
            points.put(b, bList);
        }

        findBestStrength(points, used, 0, new Point2D(0, 0));

        if (part1)
            return bestStrength;

        return weightByLength.get(maxLength);
    }

    int countStrength(Set<Point2D> used) {
        int total = 0;


        for (Point2D p : used)
            total += p.getX() + p.getY();

        weightByLength.put(used.size() - 1, Math.max(weightByLength.getOrDefault(used.size() - 1, 0), total));
        maxLength = Math.max(maxLength, used.size() - 1);
        return total;
    }

    private void findBestStrength(HashMap<Double, List<Point2D>> points, Set<Point2D> used, double port, Point2D curr) {
        used.add(curr);
        for (Point2D cand : points.get(port)) {
            if (used.contains(cand))
                continue;
            findBestStrength(points, used, cand.getX() == port ? cand.getY() : cand.getX(), cand);
        }

        bestStrength = Math.max(bestStrength, countStrength(used));
        used.remove(curr);
    }

}
