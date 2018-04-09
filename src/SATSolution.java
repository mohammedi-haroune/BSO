import com.github.dakusui.combinatoradix.Combinator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SATSolution extends Solution<InstanceSAT, SATSolution> {
    ArrayList<Boolean> literals;

    public SATSolution(InstanceSAT problem) {
        super(problem);
        int numVars = problem.variables.size();
        literals = new ArrayList<>();
        for (int i = 0; i < numVars; i++) {
            literals.add(true);
        }
    }

    public int size() {
        return literals.size();
    }

    @Override
    public String toString() {
        return "Solution{" +
                "literals=" + literals +
                '}';
    }

     @Override
     HashSet<SATSolution> getNeighbors(int flip) {
         HashSet<SATSolution> neighbors = new HashSet<>();
         int n = problem.variables.size();
         for (int f = 1; f <= flip; f++) {
             for (int i = 0; i < n / f; i++) {
                 SATSolution solution = copy();
                 for (int h = 0; h < f; h++)
                     if (f * h + i < n)
                         solution.literals.set(f * h + i, !literals.get(f * h + i));
                 neighbors.add(solution);
             }
         }
         return neighbors;
     }

//     List<Integer> vars = problem.variables.stream().map(x -> x - 1).collect(Collectors.toList());
//    @Override
//    HashSet<SATSolution> getNeighbors(int flip) {
//        HashSet<SATSolution> neighbors = new HashSet<>();
//        for (int f = 1; f <= flip; f++) {
//            for (List<Integer> list : new Combinator<>(vars, f)) {
//                SATSolution solution = copy();
//                for (int i :
//                        list) {
//                    solution.literals.set(i, !literals.get(i));
//                }
//                neighbors.add(solution);
//            }
//        }
//        return neighbors;
//    }


    public SATSolution copy() {
        SATSolution solution = new SATSolution(problem);
        solution.literals = new ArrayList<>(literals);
        return solution;
    }

    @Override
    double quality() {
        return problem.numSatisfy(this);
    }

    @Override
    double distance(SATSolution other) {
        return other.literals.stream().filter(x -> !literals.contains(x)).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SATSolution solution = (SATSolution) o;
        return Objects.equals(literals, solution.literals);
    }

    @Override
    public int hashCode() {

        return Objects.hash(literals);
    }
}
