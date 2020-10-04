package aoc.days;

import aoc.misc.Day;
import aoc.misc.Pair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class Day_14 implements Day {

    @Override
    public Object part1() throws IOException {
        test();
        String input = readDay(14);
        return doHashing(input);
    }

    @Override
    public Object part2() throws IOException {
        part2 = true;
        String input = readDay(14);
        return doHashing(input);
    }

    void test() {
        assert(hasConsecutive("aaa", 3).equals("a"));
        assert(hasConsecutive("0aaa0", 3).equals("a"));
        assert(hasConsecutive("aaa0", 3).equals("a"));
        assert(hasConsecutive("0aaa", 3).equals("a"));
        assert(hasConsecutive("dsafaafdg", 3).equals(""));
        assert(hasConsecutive("aaaaa", 5).equals("a"));
        assert(hasConsecutive("0aaaaa", 5).equals("a"));
        assert(hasConsecutive("aaaaa0", 5).equals("a"));
        assert(hasConsecutive("0aaaaa0", 5).equals("a"));
        assert(hasConsecutive("aaaa", 5).equals(""));
    }

   String createMD5(String input) {
       try {
           byte[] msg = input.getBytes("UTF-8");
           MessageDigest md = MessageDigest.getInstance("MD5");
           byte[] array = md.digest(msg);

           StringBuffer sb = new StringBuffer();
           for (int i = 0; i < array.length; ++i) {
               sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
           }

           return sb.toString();


       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }
       return "";
   }

   String hasConsecutive(String word, int count) {
        for (int i = 0; i <= word.length() - count; i++) {
           char start = word.charAt(i);
           boolean valid = true;
           for (int j = i; j < i + count; j++) {
               if (word.charAt(j) != start) {
                   valid = false;
               }
           }
           if (valid)
               return start + "";
        }
        return "";
   }

   boolean part2 = false;

    String doHashing(String input) {
        List<Pair<String, Integer>> candidates = new ArrayList<>();
        String key = "cuanljph";
        int index = 0;
        List<Integer> result = new ArrayList<>();
        int end = Integer.MAX_VALUE;

        while (index < end) {
            String currWord = key + index;
            String curr = createMD5(currWord);
            if (part2) {
                for (int i = 0; i < 2016; i++) {
                    curr = createMD5(curr);
                    assert (curr.length() == 32);
                }
            }
            String fRep = hasConsecutive(curr, 5);
            if (fRep.length() > 0) {
                int finalIndex = index;
                candidates = candidates.stream().filter(s -> finalIndex - s.second <= 1000).collect(Collectors.toList());
                List<Pair<String, Integer>> toremove = new ArrayList<>();
                for (var cand : candidates) {
                    if (fRep.equals(cand.first)) {
                        result.add(cand.second);
                        toremove.add(cand);
                    }
                }
                for (var el : toremove) {
                    candidates.remove(el);
                }
                if (result.size() > 64)
                    break;
            }
            String tRep = hasConsecutive(curr, 3);
            if (tRep.length() > 0) {
                candidates.add(new Pair<>(tRep, index));
            }
            index += 1;
       }
        return "" + result.get(63);
    }
}
