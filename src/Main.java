import com.github.dakusui.combinatoradix.Combinator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        BSO<InstanceSAT, SATSolution> bso = new BSO<>(
                2500,
                2,
                10,
                10,
                100
        );
        String path = "/home/mohammedi/IdeaProjects/BSO/benchmarks/UF75.325.100/uf75-03.cnf";
        InstanceSAT problem = InstanceSAT.fromCNF(path);
        SATSolution beeInit = new SATSolution(problem);

        //System.out.println("beeInit = " + beeInit.getNeighbors(4).size());

        SATSolution solution = bso.run(beeInit);
        System.out.println("solution = " + solution);
        System.out.println("solution.problem.numSatisfy(solution) = " + solution.problem.numSatisfy(solution));
    }
}
