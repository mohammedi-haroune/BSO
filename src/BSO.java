import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class BSO<P extends Problem, T extends Solution<P, T>> {
    private int maximumNumberOfIterations;
    private int parameterFlip;
    private int numberOfBees;
    private int maximumNumberOfChanges;
    private int numberOfLocalSearchIterations;

    public BSO(int maximumNumberOfIterations, int parameterFlip, int numberOfBees, int maximumNumberOfChanges, int numberOfLocalSearchIterations) {
        this.maximumNumberOfIterations = maximumNumberOfIterations;
        this.parameterFlip = parameterFlip;
        this.numberOfBees = numberOfBees;
        this.maximumNumberOfChanges = maximumNumberOfChanges;
        this.numberOfLocalSearchIterations = numberOfLocalSearchIterations;
    }

    public T run(T beeInit) {
        HashSet<T> taboo = new HashSet<>();
        int numberOfChanges = maximumNumberOfChanges;
        T sref = beeInit;
        int i = 0;
        while (i < maximumNumberOfIterations && !sref.problem.isOptimal(sref)) {
            System.out.println("sref.quality() = " + sref.quality() + " ,sref.diversity() = " + sref.diversity(taboo) + " ,taboo = " + taboo.size());
            taboo.add(sref);
            List<T> dances =
            sref.getNeighbors(parameterFlip)
                    .stream()
                    .sorted(Comparator.comparingDouble(sol -> -sol.quality()))
                    .limit(numberOfBees)
                    .map(solution -> solution.search(parameterFlip, numberOfLocalSearchIterations))
                    .collect(Collectors.toList());

            T bestQuality = dances.stream().max(Comparator.comparingDouble(Solution::quality)).orElse(null);
            T bestDiversity = dances.stream().max(Comparator.comparingDouble(s -> s.diversity(taboo))).orElse(null);

            System.out.println("bestDiversity.distance(sref) = " + bestDiversity.distance(sref));
            System.out.println("bestQuality.distance(sref) = " + bestQuality.distance(sref));

            if (bestQuality.quality() - sref.quality() > 0) sref = bestQuality;
            else {
                numberOfChanges--;
                if (numberOfChanges > 0) sref = bestQuality;
                else {
                    sref = bestDiversity;
                    numberOfChanges = maximumNumberOfChanges;
                }
            }
            i++;
        }
        return sref;
    }
}
