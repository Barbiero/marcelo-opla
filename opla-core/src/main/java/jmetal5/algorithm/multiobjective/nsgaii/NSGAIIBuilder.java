package jmetal5.algorithm.multiobjective.nsgaii;

import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.operator.impl.selection.BinaryTournamentSelection;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.AlgorithmBuilder;
import jmetal5.util.JMetalException;
import jmetal5.util.comparator.RankingAndCrowdingDistanceComparator;
import jmetal5.util.evaluator.SolutionListEvaluator;
import jmetal5.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.util.List;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class NSGAIIBuilder<S extends Solution<?>> implements AlgorithmBuilder<NSGAII<S>> {
    /**
     * NSGAIIBuilder class
     */
    private final Problem<S> problem;
    private int maxEvaluations;
    private int populationSize;
    private CrossoverOperator<S> crossoverOperator;
    private MutationOperator<S> mutationOperator;
    private SelectionOperator<List<S>, S> selectionOperator;
    private SolutionListEvaluator<S> evaluator;
    private NSGAIIVariant variant;

    /**
     * NSGAIIBuilder constructor
     */
    public NSGAIIBuilder(Problem<S> problem, CrossoverOperator<S> crossoverOperator,
                         MutationOperator<S> mutationOperator) {
        this.problem = problem;
        maxEvaluations = 25000;
        populationSize = 100;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        selectionOperator = new BinaryTournamentSelection<S>(new RankingAndCrowdingDistanceComparator<S>());
        evaluator = new SequentialSolutionListEvaluator<S>();

        this.variant = NSGAIIVariant.NSGAII;
    }

    public NSGAIIBuilder<S> setMaxEvaluations(int maxEvaluations) {
        if (maxEvaluations < 0) {
            throw new JMetalException("maxEvaluations is negative: " + maxEvaluations);
        }
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public NSGAIIBuilder<S> setVariant(NSGAIIVariant variant) {
        this.variant = variant;

        return this;
    }

    public NSGAII<S> build() {
        NSGAII<S> algorithm = null;
        if (variant.equals(NSGAIIVariant.NSGAII)) {
            algorithm = new NSGAII<S>(problem, maxEvaluations, populationSize, crossoverOperator,
                    mutationOperator, selectionOperator, evaluator);
        } else if (variant.equals(NSGAIIVariant.SteadyStateNSGAII)) {
            algorithm = new SteadyStateNSGAII<S>(problem, maxEvaluations, populationSize, crossoverOperator,
                    mutationOperator, selectionOperator, evaluator);
        } else if (variant.equals(NSGAIIVariant.Measures)) {
            algorithm = new NSGAIIMeasures<S>(problem, maxEvaluations, populationSize, crossoverOperator,
                    mutationOperator, selectionOperator, evaluator);
        }

        return algorithm;
    }

    /* Getters */
    public Problem<S> getProblem() {
        return problem;
    }

    public int getMaxIterations() {
        return maxEvaluations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public NSGAIIBuilder<S> setPopulationSize(int populationSize) {
        if (populationSize < 0) {
            throw new JMetalException("Population size is negative: " + populationSize);
        }

        this.populationSize = populationSize;

        return this;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return crossoverOperator;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public SelectionOperator<List<S>, S> getSelectionOperator() {
        return selectionOperator;
    }

    public NSGAIIBuilder<S> setSelectionOperator(SelectionOperator<List<S>, S> selectionOperator) {
        if (selectionOperator == null) {
            throw new JMetalException("selectionOperator is null");
        }
        this.selectionOperator = selectionOperator;

        return this;
    }

    public SolutionListEvaluator<S> getSolutionListEvaluator() {
        return evaluator;
    }

    public NSGAIIBuilder<S> setSolutionListEvaluator(SolutionListEvaluator<S> evaluator) {
        if (evaluator == null) {
            throw new JMetalException("evaluator is null");
        }
        this.evaluator = evaluator;

        return this;
    }

    public enum NSGAIIVariant {NSGAII, SteadyStateNSGAII, Measures, NSGAII45}
}
