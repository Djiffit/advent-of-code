package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day_02 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(2);
        return keypadCombo(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(2);
        return keypadComboWeirdKeypad(input);
    }
    int x, y;
    Map<Pair<Integer, Integer>, Character> validPoints;

    void move(char cmd) {
        if (cmd == 'U')
            y -= 1;
        if (cmd == 'D')
            y += 1;
        if (cmd == 'L')
            x -= 1;
        if (cmd == 'R')
            x += 1;
        x = Math.max(0, x);
        x = Math.min(2, x);
        y = Math.max(0, y);
        y = Math.min(2, y);
    }

    void move2(char cmd) {
        int old_x = x;
        int old_y = y;
        if (cmd == 'U')
            y += 1;
        if (cmd == 'D')
            y -= 1;
        if (cmd == 'L')
            x -= 1;
        if (cmd == 'R')
            x += 1;
        if (!validPoints.containsKey(new Pair(y, x))) {
            x = old_x;
            y = old_y;
        }
    }

    String keypadCombo(String input) {
        x = 1;
        y = 1;
        StringBuilder sb = new StringBuilder();
        for (String line : input.split("\n")) {
            for (char c : line.toCharArray()) {
                move(c);
            }
            sb.append(y * 3 + x + 1);
        }
        return sb.toString();
    }

    String keypadComboWeirdKeypad(String input) {
        initValues();
        StringBuilder sb = new StringBuilder();
        for (String line : input.split("\n")) {
            for (char c : line.toCharArray()) {
                move2(c);
            }
            sb.append(validPoints.get(new Pair(y, x)));
        }
        return sb.toString();
    }

    private void initValues() {
        x = -2;
        y = 0;
        validPoints = new HashMap<>();
        validPoints.put(new Pair(0, 0), '7');
        validPoints.put(new Pair(1, 0), '3');
        validPoints.put(new Pair(2, 0), '1');
        validPoints.put(new Pair(-1, 0), 'B');
        validPoints.put(new Pair(-2, 0), 'D');
        validPoints.put(new Pair(0, 1), '8');
        validPoints.put(new Pair(0, 2), '9');
        validPoints.put(new Pair(0, -1), '6');
        validPoints.put(new Pair(0, -2), '5');
        validPoints.put(new Pair(1, 1), '4');
        validPoints.put(new Pair(-1, -1), 'A');
        validPoints.put(new Pair(-1, 1), 'C');
        validPoints.put(new Pair(1, -1), '2');
    }
}
