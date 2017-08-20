package jmetal5.algorithm.multiobjective.nsgaiii;

import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.AlgorithmBuilder;
import jmetal5.util.evaluator.SolutionListEvaluator;
import jmetal5.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.util.List;


/**
 * Builder class
 */
public class NSGAIIIBuilder<S extends Solution<?>> implements AlgorithmBuilder<NSGAIII<S>> {
    // no access modifier means access from classes within the same package
    private Problem<S> problem;
    private int maxIterations;
    private int populationSize;
    private CrossoverOperator<S> crossoverOperator;
    private MutationOperator<S> mutationOperator;
    private SelectionOperator<List<S>, S> selectionOperator;

    private SolutionListEvaluator<S> evaluator;

    /**
     * Builder constructor
     */
    public NSGAIIIBuilder(Problem<S> problem) {
        this.problem = problem;
        maxIterations = 250;
        populationSize = 100;
        evaluator = new SequentialSolutionListEvaluator<S>();
    }

    public NSGAIIIBuilder<S> setSolutionListEvaluator(SolutionListEvaluator<S> evaluator) {
        this.evaluator = evaluator;

        return this;
    }

    public SolutionListEvaluator<S> getEvaluator() {
        return evaluator;
    }

    public Problem<S> getProblem() {
        return problem;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public NSGAIIIBuilder<S> setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;

        return this;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public NSGAIIIBuilder<S> setPopulationSize(int populationSize) {
        this.populationSize = populationSize;

        return this;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return crossoverOperator;
    }

    public NSGAIIIBuilder<S> setCrossoverOperator(CrossoverOperator<S> crossoverOperator) {
        this.crossoverOperator = crossoverOperator;

        return this;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public NSGAIIIBuilder<S> setMutationOperator(MutationOperator<S> mutationOperator) {
        this.mutationOperator = mutationOperator;

        return this;
    }

    public SelectionOperator<List<S>, S> getSelectionOperator() {
        return selectionOperator;
    }

    public NSGAIIIBuilder<S> setSelectionOperator(SelectionOperator<List<S>, S> selectionOperator) {
        this.selectionOperator = selectionOperator;

        return this;
    }

    public NSGAIII<S> build() {
        return new NSGAIII<>(this);
    }
}
