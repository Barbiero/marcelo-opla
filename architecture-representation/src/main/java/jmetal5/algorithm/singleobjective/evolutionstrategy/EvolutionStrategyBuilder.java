package jmetal5.algorithm.singleobjective.evolutionstrategy;

import jmetal5.algorithm.Algorithm;
import jmetal5.operator.MutationOperator;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.AlgorithmBuilder;
import jmetal5.util.JMetalException;

/**
 * Class implementing a (mu , lambda) Evolution Strategy (lambda must be divisible by mu)
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class EvolutionStrategyBuilder<S extends Solution<?>> implements AlgorithmBuilder<Algorithm<S>> {
    private Problem<S> problem;
    private int mu;
    private int lambda;
    private int maxEvaluations;
    private MutationOperator<S> mutation;
    private EvolutionStrategyVariant variant;

    public EvolutionStrategyBuilder(Problem<S> problem, MutationOperator<S> mutationOperator,
                                    EvolutionStrategyVariant variant) {
        this.problem = problem;
        this.mu = 1;
        this.lambda = 10;
        this.maxEvaluations = 250000;
        this.mutation = mutationOperator;
        this.variant = variant;
    }

    @Override
    public Algorithm<S> build() {
        if (variant == EvolutionStrategyVariant.ELITIST) {
            return new ElitistEvolutionStrategy<S>(problem, mu, lambda, maxEvaluations, mutation);
        } else if (variant == EvolutionStrategyVariant.NON_ELITIST) {
            return new NonElitistEvolutionStrategy<S>(problem, mu, lambda, maxEvaluations, mutation);
        } else {
            throw new JMetalException("Unknown variant: " + variant);
        }
    }

    /* Getters */
    public int getMu() {
        return mu;
    }

    public EvolutionStrategyBuilder<S> setMu(int mu) {
        this.mu = mu;

        return this;
    }

    public int getLambda() {
        return lambda;
    }

    public EvolutionStrategyBuilder<S> setLambda(int lambda) {
        this.lambda = lambda;

        return this;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public EvolutionStrategyBuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public MutationOperator<S> getMutation() {
        return mutation;
    }

    public enum EvolutionStrategyVariant {ELITIST, NON_ELITIST}
}
