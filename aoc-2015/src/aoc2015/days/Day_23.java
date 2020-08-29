package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day_23 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(23);
        return solvePartOne(input, false);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(23);
        return solvePartOne(input, true);
    }

    class Computer {
        Map<String, Long> registers = new HashMap<>();
        String[] cmds;
        int pos = 0;

        public Computer(String input, boolean part2) {
            registers.put("a", part2 ? 1L : 0L);
            registers.put("b", 0L);
            cmds = input.split("\n");
        }

        void compute() {
            while (pos >= 0 && pos < cmds.length) {
                String cmd = cmds[pos];
                String command = cmd.split(" ")[0];

                if (command.equals("hlf"))
                    half(cmd.split(" ")[1]);
                if (command.equals("tpl"))
                    tpl(cmd.split(" ")[1]);
                if (command.equals("inc"))
                    inc(cmd.split(" ")[1]);
                if (command.equals("jmp"))
                    jmp(Long.parseLong(cmd.split(" ")[1]));
                if (command.equals("jie"))
                    jie(cmd.charAt(4) + "", Long.parseLong(cmd.split(", ")[1]));
                if (command.equals("jio"))
                    jio(cmd.charAt(4) + "", Long.parseLong(cmd.split(", ")[1]));
            }
        }

        void half(String reg) {
            registers.put(reg, registers.get(reg) / 2);
            pos += 1;
        }

        void tpl(String reg) {
            registers.put(reg, registers.get(reg) * 3);
            pos += 1;
        }

        void inc(String reg) {
            registers.put(reg, registers.get(reg) + 1);
            pos += 1;
        }

        void jmp(long count) {
            pos += count;
        }

        void jie(String reg, long jump) {
            if (registers.get(reg) % 2 == 0)
                jmp(jump);
            else
                pos += 1;
        }

        void jio(String reg, long jump) {
            if (registers.get(reg) == 1)
                jmp(jump);
            else
                pos += 1;
        }
    }

    String solvePartOne(String input, boolean part2) {
        Computer computer = new Computer(input, part2);
        computer.compute();
        return "" + computer.registers.get("b");
    }

    String solvePartTwo(String input) {

        return "success";
    }
}