package aoc2015.days;

import aoc2015.misc.Day;

import java.io.IOException;

public class Day_11 implements Day {

    @Override
    public Object part1() throws IOException {
        String input = readDay(11);
        return getNextPassword(input);
    }

    @Override
    public Object part2() throws IOException {
        String input = readDay(11);
        return getNextPasswordX2(input);
    }

    char[] password;

    boolean nextPassword() {
        int i = password.length - 1;

        while (password[i] == 'z') {
            password[i] = 'a';
            i -= 1;
        }

        password[i] = (char) (password[i] + 1);
        return validPassword();
    }

    private boolean validPassword() {
        return noInvalids() && hasThreeRising() && hasTwoPairs();
    }

    private boolean hasTwoPairs() {
        int pairs = 0;

        for (int i = 0; i < password.length - 1; i ++) {
            if (password[i] == password[i + 1]) {
                pairs += 1;
                i += 1;
            }
        }

        return pairs >= 2;
    }

    private boolean noInvalids() {
        String invalid = "iol";

        for (char c : password)
            if (invalid.contains(c + ""))
                return false;

        return true;
    }

    private boolean hasThreeRising() {
        for (int i = 0; i < password.length - 3; i++)
            if (password[i] <= 'x' && password[i + 1] - password[i] == 1 && password[i+2] - password[i + 1] == 1)
                return true;

        return false;
    }

    String getNextPassword(String input) {
        this.password = input.toCharArray();
        while (!nextPassword()) {}
        StringBuilder sb = new StringBuilder();

        for (char c : this.password)
            sb.append(c);

        return sb.toString();
    }

    String getNextPasswordX2(String input) {
        return getNextPassword(getNextPassword(input));
    }
}
