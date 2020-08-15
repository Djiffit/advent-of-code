package aoc.solutions;

import aoc.misc.Day;

import java.io.IOException;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import java.util.*;

public class Day_21 implements Day {

    public Object part1() throws IOException {
        String input = readDay(21);
        return activeAfterNSteps(5, input);
    }

    public Object part2() throws IOException {
        String input = readDay(21);
        return activeAfterNSteps(18, input);
    }


    public int activeAfterNSteps(int n, String input) {
        List<Rule> rules = new ArrayList<>();

        for (String row : input.split("\n")) {
            String[] split = row.split(" => ");
            List<String> cond = new ArrayList<>();
            List<String> out = new ArrayList<>();

            Collections.addAll(cond, split[0].split("/"));
            Collections.addAll(out, split[1].split("/"));

            rules.add(new Rule(new Pattern(cond), new Pattern(out)));
        }

        List<String> startRows = new ArrayList<>();
        startRows.add(".#.");
        startRows.add("..#");
        startRows.add("###");

        Pattern patt = new Pattern(startRows);

        for (int i = 0; i < n; i++) {
            patt = patt.step(rules);
        }

        return patt.getActive();
    }

    static class Pattern {
        List<String> raw;

        public Pattern(List<String> raw) {
            this.raw = raw;
        }

        List<String> getRaw() {
            return new ArrayList<>(this.raw);
        }

        public static Pattern joinRows(List<Pattern> patterns) {
            Pattern start = new Pattern(patterns.get(0).getRaw());

            for (int i = 1; i < patterns.size(); i++) {
                start = start.joinRow(patterns.get(i));
            }

            return start;
        }

        public static Pattern joinCols(List<Pattern> patterns) {
            Pattern start = new Pattern(patterns.get(0).getRaw());
            for (int i = 1; i < patterns.size(); i++) {
                start = start.joinCol(patterns.get(i));
            }

            return start;
        }

        public int getActive() {
            int total = 0;

            for (int y = 0; y < raw.size(); y++) {
                for (int x  = 0; x < raw.size(); x++) {
                    if (raw.get(y).charAt(x) == '#')
                        total += 1;
                }
            }

            return total;
        }

        Pattern joinRow(Pattern p) {
            Pattern newP = new Pattern(getRaw());
            for (int i = 0; i < raw.size(); i++) {
                newP.raw.set(i, newP.raw.get(i) + p.raw.get(i));
            }
            return newP;
        }

        Pattern joinCol(Pattern p) {
            Pattern newP = new Pattern(getRaw());
            newP.raw.addAll(p.raw);
            return newP;
        }

        Pattern step(List<Rule> rules) {
            List<Pattern> rows = new ArrayList<>();
            int jump = raw.size() % 2 == 0 ? 2 : 3;
            for (int yStart = 0; yStart < raw.size(); yStart += jump) {
                List<Pattern> patterns = new ArrayList<>();
                for (int xStart = 0; xStart < raw.size(); xStart += jump) {
                    List<String> patternRaw = new ArrayList<>();
                    for (int y = 0; y < jump; y++) {
                        StringBuilder row = new StringBuilder();
                        for (int x = 0; x < jump; x++) {
                            row.append(this.raw.get(y + yStart).charAt(x + xStart));
                        }
                        patternRaw.add(row.toString());
                    }
                    Pattern newPatt = new Pattern(patternRaw).transform(rules);
                    patterns.add(newPatt);
                }
                rows.add(Pattern.joinRows(patterns));
            }

            return Pattern.joinCols(rows);
        }

        Pattern transform(List<Rule> rules) {
            for (Rule r : rules)
                if (matches(r)) {
                    return r.getOut();
                }
            throw new Error("Failed");
        }

        boolean equals(Pattern other) {
            if (raw.size() != other.raw.size())
                return false;

            for (int y = 0; y < raw.size(); y++) {
                for (int x = 0; x < raw.size(); x++) {
                    if (raw.get(y).charAt(x) != other.raw.get(y).charAt(x))
                        return false;
                }
            }

            return true;
        }

        Pattern rotate(int times) {
            Pattern p = this;
            for (int i = 0; i < times; i++)
                p = rotate(p);
            return p;
        }

        Pattern rotate(Pattern p) {
            List<String> newRaw = new ArrayList<>(p.getRaw());
            for (int y = 0; y < raw.size(); y++) {
                StringBuilder newThing = new StringBuilder(newRaw.get(y));
                for (int x = raw.size() - 1; x >= 0; x--) {
                    newThing.replace(x, x + 1, ""+p.raw.get(x).charAt(p.raw.size() - 1 - y));
                }
                newRaw.set(y, newThing.toString());
            }

            return new Pattern(newRaw);
        }

        Pattern flip() {
            List<String> newRaw = new ArrayList<>();
            for (int y = raw.size() - 1; y >= 0; y--) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < raw.size(); x++) {
                    sb.append(raw.get(y).charAt(x));
                }
                newRaw.add(sb.toString());
            }
            return new Pattern(newRaw);
        }

        boolean matches(Rule rule) {
            Pattern cond = rule.cond;

            for (int rot = 0; rot < 4; rot++) {
                if (rotate(rot).equals(cond) || rotate(rot).flip().equals(cond))
                    return true;
            }

            return false;
        }
    }

    class Rule {
        Pattern cond;
        private Pattern out;

        public Rule(Pattern cond, Pattern out) {
            this.cond = cond;
            this.out = out;
        }

        Pattern getOut() {
            return new Pattern(out.raw);
        }
    }

}
