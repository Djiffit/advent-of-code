package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.*;

public class Day_04 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(4);
        return crackingSquad(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(4);
        part2 = true;
        return crackingSquad(input);
    }

    boolean part2 = false;

    static class PairSorter implements Comparator<Pair<Character, Integer>> {

        @Override
        public int compare(Pair<Character, Integer> o1, Pair<Character, Integer> o2) {
            if (!o1.second.equals(o2.second))
                return o2.second.compareTo(o1.second);
            return o1.first.compareTo(o2.first);
        }
    }

    String crackingSquad(String input) {
        int total = 0;
        for (String room : input.split("\n")) {
            String[] parts = room.split("-");
            Map<Character, Integer> charCounts = new HashMap<>();
            StringBuilder message = new StringBuilder();
            int number = Integer.parseInt(parts[parts.length - 1].substring(0,3));

            for (int i = 0; i < parts.length - 1; i++) {
                for (char c : parts[i].toCharArray()) {
                    if (part2)
                        c = (char) (((c - 'a') + number) % 26 + 'a');
                    charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
                    message.append(c);
                }
            }

            if (part2 && message.toString().startsWith("northpoleobjectstorage"))
                return "" + number;
            List<Pair<Character, Integer>> pairs = new ArrayList<>();

            for (char c : charCounts.keySet())
                pairs.add(new Pair(c, charCounts.get(c)));

            Collections.sort(pairs, new PairSorter());
            String checksum = parts[parts.length - 1].substring(4, parts[parts.length - 1].length() - 1);
            boolean valid = true;
            for (int i = 0; i < checksum.length(); i++) {
                char c = checksum.charAt(i);
                if (pairs.size() - 1 < i || pairs.get(i).first != c) {
                    valid = false;
                    break;
                }
            }

            if (valid)
                total += number;

        }
        return "" + total;
    }
}
