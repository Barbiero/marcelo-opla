package jmetal5.problem.impl;

import jmetal5.problem.PermutationProblem;
import jmetal5.solution.PermutationSolution;
import jmetal5.solution.impl.DefaultIntegerPermutationSolution;

@SuppressWarnings("serial")
public abstract class AbstractIntegerPermutationProblem
        extends AbstractGenericProblem<PermutationSolution<Integer>> implements
        PermutationProblem<PermutationSolution<Integer>> {

  /* Getters */

  /* Setters */

    @Override
    public PermutationSolution<Integer> createSolution() {
        return new DefaultIntegerPermutationSolution(this);
    }
}
