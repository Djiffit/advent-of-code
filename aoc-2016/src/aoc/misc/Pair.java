package aoc.misc;

import java.util.Objects;

public class Pair<T1, T2> implements Comparable {
    public T1 first;
    public T2 second;
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return first.equals(pair.first) &&
                second.equals(pair.second);
    }

    @Override
    public String toString() {
        return "[ " + this.first + " " + this.second + " ]";
    }
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public int compareTo(Object o) {
        Pair<T1, T2> other = (Pair<T1, T2> ) o;
        return (int) first < ((int) other.first ) ? -1 : 1;
    }
}
