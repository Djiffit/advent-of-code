package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.*;

public class Day_15 implements Day {

    @Override
    public Object part1() throws IOException {
        return findBestFoodScore(false);
    }

    @Override
    public Object part2() throws IOException {
        return findBestFoodScore(true);
    }

    class Ingredient {
        long capacity;
        long durability;
        long flavor;
        long texture;
        long calories;

        public Ingredient(long capacity, long durability, long flavor, long texture, long calories) {
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }

        void add(Ingredient other) {
            this.capacity += other.capacity;
            this.calories += other.calories;
            this.flavor += other.flavor;
            this.texture += other.texture;
            this.durability += other.durability;
        }

        void zero() {
            this.capacity = Math.max(this.capacity, 0);
            this.calories = Math.max(this.calories, 0);
            this.flavor = Math.max(this.flavor, 0);
            this.texture = Math.max(this.texture, 0);
            this.durability = Math.max(this.durability, 0);
        }
    }

    List<Ingredient> ingredients;
    Set<List<Long>> hist;
    List<List<Long>> waysToSum;
    Set<List<Integer>> intPerms;

    void addIngredients() {
        ingredients.add(new Ingredient(3,0,0,-3,2));
        ingredients.add(new Ingredient(0,0,-2,2,8));
        ingredients.add(new Ingredient(-3,3,0,0,9));
        ingredients.add(new Ingredient(-1,0,4,0,1));
    }

    Long findBestFoodScore(boolean part2) {
        ingredients = new ArrayList<>();
        hist = new HashSet<>();
        addIngredients();
        waysToSum = new ArrayList<>();
        getAllWaysToSum(new ArrayList<Long>(), 100);
        intPerms = new HashSet<>();
        createAllPermutations();

        return getBestScore(part2);
    }

    private Long getBestScore(boolean part2) {
        Long bestScore = 0L;
        for (List<Long> sumWay: waysToSum) {
            for (List<Integer> perm : intPerms) {
                bestScore = Math.max(bestScore, countScore(sumWay, perm, part2));
            }
        }
        return bestScore;
    }

    private long countScore(List<Long> sumWay, List<Integer> perm, boolean part2) {
        Ingredient temp = new Ingredient(0,0,0,0,0);

        for (int i = 0; i < sumWay.size(); i ++) {
            for (long j = 0; j < sumWay.get(i); j++) {
                temp.add(ingredients.get(perm.get(i)));
            }
        }
        temp.zero();
        Long total = temp.durability * temp.capacity * temp.flavor * temp.texture;
        if (part2 && temp.calories != 500)
            return 0;
        return total;
    }

    private void createAllPermutations() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z ++) {
                    for (int c =0; c < 4; c++) {
                        if (new HashSet<Integer>(Arrays.asList(x,y,z,c)).size() == 4) {
                            List<Integer> arr = new ArrayList<>(Arrays.asList(x,y,z,c));
                            intPerms.add(arr);
                        }
                    }
                }
            }
        }
    }

    private void getAllWaysToSum(ArrayList<Long> longegers, long left) {
        Collections.sort(longegers);
        if (hist.contains(longegers))
            return;
        hist.add(new ArrayList<>(longegers));
        if (left == 0 && longegers.size() == 4) {
            this.waysToSum.add(new ArrayList<>(longegers));
            return;
        }
        if (left <= 0 || longegers.size() == 4)
            return;

        for (long i = 0; i <= left; i++) {
            longegers.add(i);
            getAllWaysToSum(new ArrayList<>(longegers), left - i);
            longegers.remove(longegers.size() - 1);
        }

    }

}
