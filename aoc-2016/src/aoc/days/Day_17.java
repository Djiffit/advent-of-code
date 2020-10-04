package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.*;

public class Day_17 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(17);
        return findPaths(input);
    }

    @Override
    public Object part2() throws IOException {
        part2 = true;
        String input = readDay(17);
        return findPaths(input);
    }

    boolean part2 = false;

    private String createMD5(String hashTarget) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            var out = md5.digest(hashTarget.getBytes());
            StringBuilder hex = new StringBuilder();

            for (byte b : out)
                hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (Exception e) {
            System.out.print("FAILED TO GET ALGO");
            return "";
        }
    }

    String findPaths(String input) {
        Queue<Pair<Pair<String, Integer>, Pair<Integer, Integer>>> queue = new LinkedList<>();
        queue.add(new Pair<>(new Pair<>("vkjiggvb", 0), new Pair<>(0, 0)));
        List<Character> accessible = new ArrayList<>();
        accessible.add('b');
        accessible.add('c');
        accessible.add('d');
        accessible.add('e');
        accessible.add('f');
        Set<String> hist = new HashSet<>();
        int maxLen = 0;

        while (queue.size() > 0) {
            var curr = queue.poll();
            var pos = curr.second;
            var nameDist = curr.first;

            if (pos.first < 0 || pos.first > 3 || pos.second < 0 || pos.second > 3 || hist.contains(nameDist.first))
                continue;

            hist.add(nameDist.first);
            if (pos.first == 3 && pos.second == 3) {
                maxLen = Math.max(maxLen, nameDist.second);
                if (part2)
                    continue;
                return nameDist.first.substring(8);
            }
           var hash = createMD5(nameDist.first);

            if (accessible.contains(hash.charAt(0)))
                queue.add(new Pair<>(new Pair<>(nameDist.first + "U", nameDist.second + 1), new Pair<>(pos.first - 1, pos.second)));
            if (accessible.contains(hash.charAt(1)))
                queue.add(new Pair<>(new Pair<>(nameDist.first + "D", nameDist.second + 1), new Pair<>(pos.first + 1, pos.second)));
            if (accessible.contains(hash.charAt(3)))
                queue.add(new Pair<>(new Pair<>(nameDist.first + "R", nameDist.second + 1), new Pair<>(pos.first, pos.second + 1)));
            if (accessible.contains(hash.charAt(2)))
                queue.add(new Pair<>(new Pair<>(nameDist.first + "L", nameDist.second + 1), new Pair<>(pos.first, pos.second - 1)));
        }
        return "" + maxLen;
    }
}
