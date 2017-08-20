package jmetal5.algorithm.multiobjective.spea2;

import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.operator.impl.selection.BinaryTournamentSelection;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.AlgorithmBuilder;
import jmetal5.util.JMetalException;
import jmetal5.util.evaluator.SolutionListEvaluator;
import jmetal5.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.util.List;

/**
 * @author Juan J. Durillo
 */
public class SPEA2Builder<S extends Solution<?>> implements AlgorithmBuilder<SPEA2<S>> {
    /**
     * SPEA2Builder class
     */
    protected final Problem<S> problem;
    protected int maxIterations;
    protected int populationSize;
    protected CrossoverOperator<S> crossoverOperator;
    protected MutationOperator<S> mutationOperator;
    protected SelectionOperator<List<S>, S> selectionOperator;
    protected SolutionListEvaluator<S> evaluator;

    /**
     * SPEA2Builder constructor
     */
    public SPEA2Builder(Problem<S> problem, CrossoverOperator<S> crossoverOperator,
                        MutationOperator<S> mutationOperator) {
        this.problem = problem;
        maxIterations = 250;
        populationSize = 100;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        selectionOperator = new BinaryTournamentSelection<S>();
        evaluator = new SequentialSolutionListEvaluator<S>();
    }

    public SPEA2<S> build() {
        SPEA2<S> algorithm = null;
        algorithm = new SPEA2<S>(problem, maxIterations, populationSize, crossoverOperator,
                mutationOperator, selectionOperator, evaluator);

        return algorithm;
    }

    /* Getters */
    public Problem<S> getProblem() {
        return problem;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public SPEA2Builder<S> setMaxIterations(int maxIterations) {
        if (maxIterations < 0) {
            throw new JMetalException("maxIterations is negative: " + maxIterations);
        }
        this.maxIterations = maxIterations;

        return this;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public SPEA2Builder<S> setPopulationSize(int populationSize) {
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

    public SPEA2Builder<S> setSelectionOperator(SelectionOperator<List<S>, S> selectionOperator) {
        if (selectionOperator == null) {
            throw new JMetalException("selectionOperator is null");
        }
        this.selectionOperator = selectionOperator;

        return this;
    }

    public SolutionListEvaluator<S> getSolutionListEvaluator() {
        return evaluator;
    }

    public SPEA2Builder<S> setSolutionListEvaluator(SolutionListEvaluator<S> evaluator) {
        if (evaluator == null) {
            throw new JMetalException("evaluator is null");
        }
        this.evaluator = evaluator;

        return this;
    }
}
