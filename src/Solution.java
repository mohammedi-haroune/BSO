import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

public abstract class Solution<P extends Problem, T extends Solution<P, T>> {
    public Solution(P problem) {
        this.problem = problem;
    }

    P problem;
    abstract Set<T> getNeighbors(int flip);
    abstract double quality();

    double diversity(Set<T> set) {
        return set.stream().mapToDouble(this::distance).min().orElse(0);
    }

    abstract double distance(T other);

    public T search(int flip, int numberOfLocalSearchIterations) {
        return this
                .getNeighbors(flip)
                .stream()
                .sorted(Comparator.comparingDouble(sol -> -sol.quality()))
                .limit(numberOfLocalSearchIterations)
                .max(Comparator.comparingDouble(Solution::quality))
                .orElse(null);
    }
}
