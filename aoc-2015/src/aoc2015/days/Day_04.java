package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;

public class Day_04 implements Day {

    @Override
    public Object part1() {
        String input = "yzbqklnj";
        return getSuffixForTarget(input, "00000");
    }

    @Override
    public Object part2() {
        String input = "yzbqklnj";
        return getSuffixForTarget(input, "000000");
    }

    int getSuffixForTarget(String inp, String target) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            for (int i = 0; i < 9962629; i ++) {
                String input = inp + i;
                byte[] digest = md5.digest(input.getBytes());
                StringBuilder sb = new StringBuilder(digest.length * 2);

                for (byte b : digest)
                    sb.append(String.format("%02x", b));

                if (sb.substring(0, target.length()).equals(target))
                    return i;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }
}
