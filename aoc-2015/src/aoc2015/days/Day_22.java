package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day_22 implements Day {

    @Override
    public Object part1() throws IOException {
        return solvePartOne();
    }

    @Override
    public Object part2() throws IOException {
        return solvePartOne();
    }

    List<String> spells = Arrays.asList("mm", "d", "s", "p", "r");

    class Player {
        int health = 50;
        int mana = 500;
        int shieldDur = 0;
        int rechargeDur = 0;

        public Player() {}

        public Player(Player player) {
            this.health = player.health;
            this.mana = player.mana;
            this.shieldDur = player.shieldDur;
            this.rechargeDur = player.rechargeDur;
        }
    }

    class Boss {
        int health = 51;
        int poisonDur = 0;
        int damage = 9;

        public Boss () {}
        public Boss (Boss boss) {
            this.health = boss.health;
            this.poisonDur = boss.poisonDur;
            this.damage = boss.damage;
        }
    }

    int record = Integer.MAX_VALUE;

    void traverseGameTree(Player player, Boss boss, int usedMana, boolean pTurn, List<String> history) {
        player = new Player(player);
        boss = new Boss(boss);
        if (pTurn) {
            player.health -= 1;
            if (player.health == 0)
                return;
        }
        if (player.health <= 0 || player.mana <= 0)
            return;
        if (boss.health <= 0) {
            System.out.println(history);
            record = Math.min(usedMana, record);
            return;
        }

        if (player.rechargeDur > 0) {
            player.mana += 101;
            player.rechargeDur -= 1;
        }
        if (player.shieldDur > 0) {
            player.shieldDur -= 1;
        }
        if (boss.poisonDur > 0) {
            boss.poisonDur -= 1;
            boss.health -= 3;
        }

        if (pTurn) {
                if (player.mana >= 73) {
                    Boss newBoss = new Boss(boss);
                    Player newPlayer = new Player(player);
                    newBoss.health -= 2;
                    newPlayer.health += 2;
                    newPlayer.mana -= 73;
                    history.add("d");
                    traverseGameTree(newPlayer, newBoss, usedMana + 73, false, history);
                    history.remove(history.size() - 1);
                }
                if (player.mana >= 53) {
                    Boss newBoss = new Boss(boss);
                    newBoss.health -= 4;
                    Player newPlayer = new Player(player);
                    newPlayer.mana -= 53;
                    history.add("mm");
                    traverseGameTree(newPlayer, newBoss, usedMana + 53, false, history);
                    history.remove(history.size() - 1);
                }
                if (player.mana >= 113 && player.shieldDur == 0) {
                    Boss newBoss = new Boss(boss);
                    Player newPlayer = new Player(player);
                    newPlayer.shieldDur = 6;
                    newPlayer.mana -= 113;
                    history.add("s");
                    traverseGameTree(newPlayer, newBoss, usedMana + 113, false, history);
                    history.remove(history.size() - 1);
                }
                if (player.mana >= 173 && boss.poisonDur == 0) {
                    Boss newBoss = new Boss(boss);
                    Player newPlayer = new Player(player);
                    newBoss.poisonDur = 6;
                    newPlayer.mana -= 173;
                    history.add("p");
                    traverseGameTree(newPlayer, newBoss, usedMana + 173, false, history);
                    history.remove(history.size() - 1);
                }
                if (player.mana >= 229) {
                    Boss newBoss = new Boss(boss);
                    Player newPlayer = new Player(player);
                    newPlayer.rechargeDur = 5;
                    newPlayer.mana -= 229;
                    history.add("r");
                    traverseGameTree(newPlayer, newBoss, usedMana + 229, false, history);
                    history.remove(history.size() - 1);
                }
        } else {
            Boss newBoss = new Boss(boss);
            Player newPlayer = new Player(player);
            if (player.shieldDur > 0)
                newPlayer.health -= Math.max(1, boss.damage - 7);
            else
                newPlayer.health -= boss.damage;
            traverseGameTree(newPlayer, newBoss, usedMana, true, history);
        }
    }

    int solvePartOne() {
        traverseGameTree(new Player(), new Boss(), 0, true, new ArrayList<>());

        return record;
    }
}
