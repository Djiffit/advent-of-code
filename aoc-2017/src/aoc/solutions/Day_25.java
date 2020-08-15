package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day_25 implements Day {

    public Object part1() throws IOException {
        return getChecksum(12172063);
    }

    public Object part2() throws IOException {
        return "Nice job!";
    }

    long getChecksum(int stepCount) {
        int currState = 0;
        List<State> states = new ArrayList<>();
        Machine m = new Machine();

        states.add(new StateA());
        states.add(new StateB());
        states.add(new StateC());
        states.add(new StateD());
        states.add(new StateE());
        states.add(new StateF());

        for (int i = 0; i < stepCount; i++)
            currState = states.get(currState).act(m);

        long totalOnes = 0;

        for (int val : m.tape.values())
            if (val == 1)
                totalOnes += 1;

        return totalOnes;
    }

    class Machine {
        int position = 0;
        Map<Integer, Integer> tape = new HashMap<>();
    }

    interface State {
        int act(Machine mach);
    }

    class StateA implements State {

        @Override
        public int act(Machine mach) {
            int current = mach.tape.getOrDefault(mach.position, 0);
            if (current == 0) {
                mach.tape.put(mach.position, 1);
                mach.position += 1;
                return 1;
            }
            mach.tape.put(mach.position, 0);
            mach.position -= 1;
            return 2;
        }
    }

    class StateB implements State {

        @Override
        public int act(Machine mach) {
            int current = mach.tape.getOrDefault(mach.position, 0);
            if (current == 0) {
                mach.tape.put(mach.position, 1);
                mach.position -= 1;
                return 0;
            }
            mach.tape.put(mach.position, 1);
            mach.position -= 1;
            return 3;
        }
    }

    class StateC implements State {

        @Override
        public int act(Machine mach) {
            int current = mach.tape.getOrDefault(mach.position, 0);
            if (current == 0) {
                mach.tape.put(mach.position, 1);
                mach.position += 1;
                return 3;
            }
            mach.tape.put(mach.position, 0);
            mach.position += 1;
            return 2;
        }
    }

    class StateD implements State {

        @Override
        public int act(Machine mach) {
            int current = mach.tape.getOrDefault(mach.position, 0);
            if (current == 0) {
                mach.tape.put(mach.position, 0);
                mach.position -= 1;
                return 1;
            }
            mach.tape.put(mach.position, 0);
            mach.position += 1;
            return 4;
        }
    }

    class StateE implements State {

        @Override
        public int act(Machine mach) {
            int current = mach.tape.getOrDefault(mach.position, 0);
            if (current == 0) {
                mach.tape.put(mach.position, 1);
                mach.position += 1;
                return 2;
            }
            mach.tape.put(mach.position, 1);
            mach.position -= 1;
            return 5;
        }
    }

    class StateF implements State {

        @Override
        public int act(Machine mach) {
            int current = mach.tape.getOrDefault(mach.position, 0);
            if (current == 0) {
                mach.tape.put(mach.position, 1);
                mach.position -= 1;
                return 4;
            }
            mach.tape.put(mach.position, 1);
            mach.position += 1;
            return 0;
        }
    }

}
