package aoc.days;

import aoc.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_07 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(7);
        return bridgeBypasser(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(7);
        return superSecretListening(input);
    }

    boolean isHyperNet(String word) {
        for (int i = 0; i < word.length() - 3; i ++) {
            String sub = word.substring(i, i + 4);
            if (sub.charAt(0) == sub.charAt(3) && sub.charAt(1) == sub.charAt(2) && sub.charAt(0) != sub.charAt(1)) {
                return true;
            }
        }
        return false;
    }

    void getABAs(String input, Set<String> abas) {
        for (int i = 0; i < input.length() - 2; i++) {
            String sub = input.substring(i, i + 3);
            if (sub.charAt(0) == sub.charAt(2) && sub.charAt(0) != sub.charAt(1)) {
                abas.add(sub);
            }
        }
    }

    String bridgeBypasser(String input) {
        int total = 0;
        for (String line : input.split("\n")) {
            StringBuilder sb = new StringBuilder();
            boolean foundHypernet = false;
            boolean isValid = true;
            for (char c : line.toCharArray()) {
                if (c == ']') {
                    if (isHyperNet(sb.toString()))
                        isValid = false;
                    sb = new StringBuilder();
                } else if (c == '[') {
                    if (isHyperNet(sb.toString()))
                        foundHypernet = true;
                    sb = new StringBuilder();
                } else {
                    sb.append(c);
                }
            }
            if (isHyperNet(sb.toString()))
                foundHypernet = true;
            if (foundHypernet && isValid)
                total += 1;
        }
        return "" + total;
    }

    String superSecretListening(String input) {
        int total = 0;
        for (String line : input.split("\n")) {
            Set<String> hypernetABA = new HashSet<>();
            Set<String> superNetABA = new HashSet<>();
            StringBuilder sb = new StringBuilder();
            for (char c : line.toCharArray()) {
                if (c == ']') {
                    getABAs(sb.toString(), hypernetABA);
                    sb = new StringBuilder();
                } else if (c == '[') {
                    getABAs(sb.toString(), superNetABA);
                    sb = new StringBuilder();
                } else {
                    sb.append(c);
                }
            }
            getABAs(sb.toString(), superNetABA);

            for (String aba : superNetABA) {
                if (hypernetABA.contains(aba.charAt(1) + "" + aba.charAt(0) + "" + aba.charAt(1))) {
                    total += 1;
                    break;
                }
            }
        }
        return "" + total;
    }
}
