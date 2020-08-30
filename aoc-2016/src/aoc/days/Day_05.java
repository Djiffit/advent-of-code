package aoc.days;

import aoc.misc.Day;

import java.io.IOException;
import java.security.MessageDigest;

public class Day_05 implements Day {

    @Override
    public Object part1() throws IOException {
        return solvePartOne("ffykfhsq");
    }

    @Override
    public Object part2() throws IOException {
        part2 = true;
        return solvePartOne("ffykfhsq");
    }
    boolean part2 = false;

    String solvePartOne(String input) {
        char[] chars = "--------".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000000000; i ++) {
            String hashTarget = input + "" + i;
            String hash = createMD5(hashTarget);
            if (hash.startsWith("00000")) {
                if (hash.charAt(5) >= '0' && hash.charAt(5) <= '7') {
                    if (chars[Integer.parseInt(hash.charAt(5) + "")] == '-')
                        chars[Integer.parseInt(hash.charAt(5) + "")] = hash.charAt(6);
                }
                sb.append(hash.charAt(5));
            }

            if (part2) {
                boolean done = true;
                for (char aChar : chars)
                    if (aChar == '-')
                        done = false;
                if (done) {
                    StringBuilder word = new StringBuilder();
                    for (char aChar : chars)
                        word.append(aChar);

                    return word.toString();
                }
            } else {
                if (sb.length() == 8)
                    return sb.toString();
            }
        }

        return createMD5("abc3231929");
    }

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
}
