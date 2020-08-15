package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day_10 implements Day {

    public Object part1() throws IOException {
        String input = readDay(10);
        return twistOnce(input);
    }

    public Object part2() throws IOException {
        String input = readDay(10);
        return twist64(input);
    }

    private void doTwist(List<Integer> nums, int currPos, int length) {
        int left = currPos;
        int right = (currPos + length - 1) % nums.size();

        for (int i = 0; i < length / 2; i++) {
            int tmp = nums.get(left);
            nums.set(left, nums.get(right));
            nums.set(right, tmp);
            left = Math.floorMod((left + 1), nums.size());
            right = Math.floorMod((right - 1), nums.size());
        }
    }

    public int twistOnce(String input) {
        List<Integer> rotations = createRotations(input);

        List<Integer> nums = twistKnot(rotations, 1);
        return nums.get(0) * nums.get(1);
    }

    public List<Integer> createRotations(String input) {
        return Arrays.asList(Stream.of(input.split(",")).map(Integer::valueOf).toArray(Integer[]::new));
    }

    public String createHex(List<Integer> nums) {
        StringBuilder hex = new StringBuilder();

        for (int i = 0; i < 16; i ++) {
            int xor = 0;
            for (int j = 0; j < 16; j ++)
                xor = xor ^ (nums.get(i * 16 + j));
            hex.append(Integer.toHexString(xor).length() == 1 ? "0" + Integer.toHexString(xor) : Integer.toHexString(xor));
        }

        return hex.toString();
    }

    public String twist64(String input) {
        List<Integer> rotations = new ArrayList<>();
        byte[] bytes = input.getBytes();

        for (byte b : bytes) {
            rotations.add(Byte.toUnsignedInt(b));
        }

        rotations.add(17);
        rotations.add(31);
        rotations.add(73);
        rotations.add(47);
        rotations.add(23);

        List<Integer> nums = twistKnot(rotations, 64);
        return createHex(nums);
    }

    public List<Integer> twistKnot(List<Integer> rotations, int twistCount) {
        List<Integer> nums = new ArrayList<>();
        int currPos = 0;
        int skipSize = 0;

        for (int i = 0; i < 256; i++)
            nums.add(i);

        for (int i = 0; i < twistCount; i++) {
            for (int rot : rotations) {
                doTwist(nums, currPos, rot);
                currPos = Math.floorMod((currPos + rot + skipSize), nums.size());
                skipSize += 1;
            }
        }

        return nums;
    }

}
