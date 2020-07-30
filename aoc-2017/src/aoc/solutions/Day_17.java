package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_17 implements Day {

    public Object part1() throws IOException {
        return simulateBuffer(359);
    }

    public Object part2() throws IOException {
        return calcFirst(359);
    }

    int simulateBuffer(int stepCount) {
        List<Integer> state;
        int pos = 0;
        state = new ArrayList<>();
        state.add(0);
        state.add(1);

        for (int i = 2; i < 2018; i++) {
            pos = (pos + stepCount) % state.size();
            state.add(pos + 1, i);
            pos += 1;
        }

        return state.get(pos + 1);
    }
    
    int calcFirst(int stepCount) {
        int pos = 1;
        int size = 2;
        int afterZero = 1;

        for (int i = 2; i <= 50000000; i++) {
            pos = (pos + stepCount) % size;
            if (pos == 0)
                afterZero = i;
            size += 1;
            pos += 1;
        }

        return afterZero;
    }


}
