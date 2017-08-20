package jmetal5.algorithm.multiobjective.randomsearch;

import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.AlgorithmBuilder;

/**
 * This class implements a simple random search algorithm.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class RandomSearchBuilder<S extends Solution<?>> implements AlgorithmBuilder<RandomSearch<S>> {
    private Problem<S> problem;
    private int maxEvaluations;

    public RandomSearchBuilder(Problem<S> problem) {
        this.problem = problem;
        maxEvaluations = 25000;
    }

    /* Getter */
    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public RandomSearchBuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public RandomSearch<S> build() {
        return new RandomSearch<S>(problem, maxEvaluations);
    }
} 
