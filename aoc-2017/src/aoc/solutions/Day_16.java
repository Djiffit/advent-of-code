package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day_16 implements Day {

    public Object part1() throws IOException {
        String input = readDay(16);
        dancingPrograms(input, 1);
        return printPrograms();
    }

    public Object part2() throws IOException {
        String input = readDay(16);
        dancingPrograms(input, 1000000000 % 60);
        return printPrograms();
    }

    char[] programs = "abcdefghijklmnop".toCharArray();
    Map<Character, Integer> positions = new HashMap<>();
    int position = 0;

    String printPrograms() {
        StringBuilder newString = new StringBuilder();
        for (int j = 0; j < this.programs.length; j++) {
            newString.append(this.programs[(j + this.position) % this.programs.length]);
        }
        return newString.toString();
    }

    void spin(int amnt) {
        this.position = Math.floorMod((this.position - amnt), programs.length);
    }

    void exchange(int a, int b) {
        a = (this.position + a) % this.programs.length;
        b = (this.position + b) % this.programs.length;
        char temp = this.programs[a];
        this.programs[a] = this.programs[b];
        this.programs[b] = temp;
        this.positions.put(programs[a], a);
        this.positions.put(programs[b], b);
    }

    void exchangeByName(char a, char b) {
        int posA = this.positions.get(a);
        int posB = this.positions.get(b);
        this.programs[posA] = b;
        this.programs[posB] = a;
        this.positions.put(b, posA);
        this.positions.put(a, posB);
    }

    void dancingPrograms(String input, int howMany) {
        for (int i = 0; i < programs.length; i++) {
            positions.put(programs[i], i);
        }

        for (int i = 0; i < howMany; i++) {
            String[] cmds = input.split(",");

            for (String cmd : cmds) {
                char action = cmd.charAt(0);

                switch (action) {
                    case 's':
                        spin(Integer.parseInt(cmd.substring(1)));
                        break;
                    case 'x':
                        String[] params = cmd.substring(1).split("/");
                        exchange(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                        break;
                    case 'p':
                        exchangeByName(cmd.charAt(1), cmd.charAt(3));
                        break;
                }
            }
        }
    }


}
