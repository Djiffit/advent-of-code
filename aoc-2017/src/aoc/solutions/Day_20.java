package aoc.solutions;

import aoc.misc.Day;
import javafx.geometry.Point3D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_20 implements Day {

    public Object part1() throws IOException {
        String input = readDay(20);
        return simulateParticles(input, false);
    }

    public Object part2() throws IOException {
        String input = readDay(20);
        return simulateParticles(input, true);
    }


    class Particle implements Comparable<Particle> {
        Point3D pos;
        Point3D acc;
        Point3D vel;

        int name;

        public Particle(Point3D pos, Point3D acc, Point3D vel, int name) {
            this.pos = pos;
            this.vel = vel;
            this.acc = acc;
            this.name = name;
        }

        void timeStep() {
            this.vel = this.vel.add(this.acc);
            this.pos = this.pos.add(this.vel);
        }

        double getDist() {
            return Math.abs(this.pos.getX()) + Math.abs(this.pos.getY()) + Math.abs(this.pos.getZ());
        }

        @Override
        public int compareTo(Particle other) {
            return (int) (getDist() - other.getDist());
        }
    }

    String simulateParticles(String input, boolean destroyColliders) {
        List<Particle> particles = new ArrayList<>();
        readParticles(input, particles);
        int current = -1;
        int prevchange = 0;
        int i = 0;

        while (i - prevchange < 500) {
            for (Particle p : particles)
                p.timeStep();

            if (destroyColliders) {
                HashMap<Point3D, List<Particle>> mapping = new HashMap<>();
                for (Particle p : particles) {
                    List<Particle> parts = mapping.getOrDefault(p.pos, new ArrayList<>());
                    parts.add(p);
                    mapping.put(p.pos, parts);
                }

                particles = new ArrayList<>();
                for (List<Particle> l : mapping.values()) {
                    if (l.size() == 1)
                        particles.add(l.get(0));
                }
            }

            Collections.sort(particles);
            if (particles.get(0).name != current) {
                current = particles.get(0).name;
                prevchange = i;
            }

            i += 1;
        }

        if (destroyColliders)
            return particles.size() + "";

        return current + "";
    }

    void readParticles(String input, List<Particle> target) {
        int name = 0;

        for (String line : input.split("\n")) {
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(line);
            Point3D pos = null;
            Point3D acc = null;
            Point3D vel = null;

            for (int i = 0; i < 3; i++) {
                m.find();
                double x = Double.parseDouble(m.group());
                m.find();
                double y = Double.parseDouble(m.group());
                m.find();
                double z = Double.parseDouble(m.group());
                if (i == 0)
                    pos = new Point3D(x, y, z);
                if (i == 1)
                    vel = new Point3D(x, y, z);
                if (i == 2)
                    acc = new Point3D(x, y, z);
            }

            target.add(new Particle(pos, acc, vel, name));
            name += 1;
        }
    }

}
