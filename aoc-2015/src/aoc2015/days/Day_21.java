package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day_21 implements Day {

    @Override
    public Object part1() throws IOException {
        return tryAllCombos(false);
    }

    @Override
    public Object part2() throws IOException {
        return tryAllCombos(true);
    }

    class Item {
        int cost;
        int damage;
        int armor;

        public Item(int cost, int damage, int armor) {
            this.cost = cost;
            this.damage = damage;
            this.armor = armor;
        }
    }

    List<Item> weapons;
    List<Item> armor;
    List<Item> rings;

    void createItems() {
        weapons = new ArrayList<>(Arrays.asList(
                new Item(8,4,0),
                new Item(10,5,0),
                new Item(25, 6, 0),
                new Item(40, 7, 0),
                new Item(74, 8, 0)
        ));
        armor = new ArrayList<>(Arrays.asList(
                new Item(0, 0, 0),
                new Item(13, 0, 1),
                new Item(31, 0, 2),
                new Item(53, 0, 3),
                new Item(75, 0, 4),
                new Item(102, 0, 5)
        ));
        rings = new ArrayList<>(Arrays.asList(
                new Item(0, 0, 0),
                new Item(0, 0, 0),
                new Item(25, 1, 0),
                new Item(50, 2,0),
                new Item(100, 3, 0),
                new Item(20, 0, 1),
                new Item(40, 0 ,2),
                new Item(80, 0, 3)
        ));
    }

    class Entity {
        int health;
        int attack;
        int armor;
        int cost = 0;

        public Entity(int health, int attack, int armor) {
            this.health = health;
            this.attack = attack;
            this.armor = armor;
        }

        public Entity(int health, List<Item> items) {
            this.health = health;
            for (Item i : items) {
                this.armor += i.armor;
                this.attack += i.damage;
                this.cost += i.cost;
            }
        }

        boolean takeDamage(Entity ent) {
            this.health -= Math.max(ent.attack - armor, 1);
            return this.health <= 0;
        }

    }

    boolean fightToDeath(Entity player, Entity boss) {
        while (true) {
            if (boss.takeDamage(player))
                return true;
            if (player.takeDamage(boss))
                return false;
        }
    }

    int tryAllCombos(boolean part2) {
        createItems();
        int record = part2 ? -Integer.MAX_VALUE : Integer.MAX_VALUE;
        for (Item weapon: weapons) {
            for (Item armor : armor) {
                for (Item ringOne : rings) {
                    for (Item ringTwo: rings) {
                        if (ringOne.cost != 0 && (ringOne.cost == ringTwo.cost))
                            continue;

                        Entity boss = new Entity(103, 9, 2);
                        Entity player = new Entity(100, Arrays.asList(weapon, armor, ringOne, ringTwo));

                        if (!part2 && fightToDeath(player, boss)) {
                            record = Math.min(player.cost, record);
                        } else if (part2 && !fightToDeath(player, boss)) {
                            if (player.cost < 254)
                                record = Math.max(player.cost, record);
                        }
                    }
                }
            }
        }
        return record;
    }

}
