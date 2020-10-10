package aoc.days;

import aoc.misc.Day;

import java.io.IOException;
import java.util.*;

public class Day_21 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(21);
        return findPassword(input, "bfheacgd");
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(21);
        return revertPasswordFinding(input, "fbgdceah");
    }

    StringBuilder word = new StringBuilder();

    void swapPos(int a, int b) {
        char temp = word.charAt(a);
        word.replace(a, a + 1, "" + word.charAt(b));
        word.replace(b, b+ 1, "" + temp);
    }

    void swap(char a, char b) {
        List<Integer> a_pos = new ArrayList<>();
        List<Integer> b_pos = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == a)
                word.replace(i, i + 1, "" + b);
            else if (c == b)
                word.replace(i, i + 1, "" + a);
        }
    }

    void rotate(boolean right, int num) {
       int pos = right ? Math.floorMod(-num, word.length()) : Math.floorMod(num, word.length());
       StringBuilder newWord = new StringBuilder();

       for (int i = 0; i < word.length(); i ++)
          newWord.append(word.charAt(Math.floorMod(pos + i, word.length())));

       word = newWord;
    }

    void rotateByIndex(char c) {
        int ind = word.indexOf("" + c);
        ind = 1 + ind  + (ind >= 4 ? 1 : 0);
        rotate(true, ind);
    }

    void reverse(int a, int b) {
        while (a < b) {
            swapPos(a, b);
            a += 1; b -= 1;
        }
    }

    void move(int x, int y) {
        char temp = word.charAt(x);
        word.replace(x, x+ 1, "");
        word.insert(y, temp);
    }

    String findPassword(String input, String start) {
        word = new StringBuilder(start);
        String[] cmds = input.split("\n");

        for (String line : cmds) {
            String[] params = line.split(" ");

            switch (params[0]) {
                case "move":
                    move(Integer.parseInt(params[2]), Integer.parseInt(params[5]));
                    break;
                case "swap":
                    if (params[1].equals("position"))
                        swapPos(Integer.parseInt(params[2]), Integer.parseInt(params[5]));
                    else
                        swap(params[2].charAt(0), params[5].charAt(0));

                    break;
                case "reverse":
                    reverse(Integer.parseInt(params[2]), Integer.parseInt(params[4]));
                    break;
                case "rotate":
                    if (params[1].equals("based"))
                        rotateByIndex(params[6].charAt(0));
                    else
                        rotate(params[1].equals("right"), Integer.parseInt(params[2]));
                    break;
            }
        }

        return word.toString();
    }

    List<String> permutations = new ArrayList<>();

    void permute(StringBuilder curr, Set<Character> rem) {
        if (rem.size() == 0) {
            permutations.add(curr.toString());
            return;
        }

        Set<Character> possibles = new HashSet<>(rem);
        for (char cand : possibles) {
            rem.remove(cand);
            curr.append(cand);
            permute(curr, rem);
            rem.add(cand);
            curr.deleteCharAt(curr.length() - 1);
        }
    }

    String revertPasswordFinding(String input, String start) {
        Set<Character> set = new HashSet<>();
        for (char c : start.toCharArray())
            set.add(c);

        permute(new StringBuilder(), set);

        for (String perm : permutations) {
            if (findPassword(input, perm).equals(start))
                return perm;
        }

        return "fail";
    }
}
