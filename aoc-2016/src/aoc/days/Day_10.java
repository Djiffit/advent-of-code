package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_10 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(20);
        return botTraders(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(20);
        bots = new HashMap<>();
        targets = new HashMap<>();
        outputs = new HashMap<>();
        part2 = true;
        return botTraders(input);
    }

    Map<Integer, Pair<Integer, Integer>> bots = new HashMap<>();
    Map<Integer, Pair<Pair<String, Integer>, Pair<String, Integer>>> targets = new HashMap<>();
    Map<Integer, Integer> outputs = new HashMap<>();
    boolean part2 = false;

     boolean giveTo(int bot, int val) {
        if (bots.containsKey(bot)) {
            var pair = bots.get(bot);
            int temp = pair.first;
            pair.first = Math.min(temp, val);
            pair.second = Math.max(temp, val);
            bots.put(bot, pair);
            return true;
        } else {
            bots.put(bot, new Pair(val, -1));
            return false;
        }
    }

    String botTraders(String input) {
        for (String line : input.split("\n")) {
            if (line.startsWith("value")) {
               String[] parts = line.split(" goes to bot ");
               int num1 = Integer.parseInt(parts[0].substring(6));
               int num2 = Integer.parseInt(parts[1]);
               giveTo(num2, num1);
            } else {
                String[] parts = line.split("gives low to ");
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(line);
                m.find();
                int bot = Integer.parseInt(m.group());
                m.find();
                int low = Integer.parseInt(m.group());
                m.find();
                int high = Integer.parseInt(m.group());
                Pair<String, Integer> pair1;
                Pair<String, Integer> pair2;

                if (parts[1].startsWith("output")) {
                    pair1 = new Pair("out", low);
                } else {
                    pair1 = new Pair("bot", low);
                }
                if (parts[1].split("and high to ")[1].startsWith("output")) {
                    pair2 = new Pair("out", high);
                } else {
                    pair2 = new Pair("bot", high);
                }
                targets.put(bot, new Pair(pair1, pair2));
            }
        }

        Queue<Integer> que = new LinkedList<>();
        for (int key : bots.keySet()) {
            Pair<Integer, Integer> pair = bots.getOrDefault(key, new Pair(-1, -1));
            if (pair.first != -1 && pair.second != -1) {
                que.add(key);
            }
        }

        while (que.size() > 0) {
            int curr = que.poll();
            var pairs = targets.get(curr);
            var pair = bots.get(curr);
            if ((pair.equals(new Pair(17, 61)) || pair.equals(new Pair(61, 17))) && !part2) {
               return "" + curr;
            }
            if (pairs.first.first.equals("out")) {
                outputs.put(pairs.first.second, pair.first);
            } else {
                if (giveTo(pairs.first.second, pair.first)) {
                    que.add(pairs.first.second);
                }
            }
            if (pairs.second.first.equals("out")) {
                outputs.put(pairs.second.second, pair.second);
            } else {
                if (giveTo(pairs.second.second, pair.second)) {
                    que.add(pairs.second.second);
                }
            }
        }

        return "" + outputs.get(0) * outputs.get(1) * outputs.get(2);
    }

}
