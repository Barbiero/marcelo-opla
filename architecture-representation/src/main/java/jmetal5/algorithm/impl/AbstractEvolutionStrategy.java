package jmetal5.algorithm.impl;

import jmetal5.operator.MutationOperator;
import jmetal5.problem.Problem;

/**
 * Abstract class representing an evolution strategy algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

@SuppressWarnings("serial")
public abstract class AbstractEvolutionStrategy<S, Result> extends AbstractEvolutionaryAlgorithm<S, Result> {
    protected MutationOperator<S> mutationOperator;

    /**
     * Constructor
     *
     * @param problem The problem to solve
     */
    public AbstractEvolutionStrategy(Problem<S> problem) {
        setProblem(problem);
    }

    /* Getter */
    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }
}
