package jmetal5.algorithm.multiobjective.mocell;

import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.operator.impl.selection.BinaryTournamentSelection;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.AlgorithmBuilder;
import jmetal5.util.JMetalException;
import jmetal5.util.archive.BoundedArchive;
import jmetal5.util.archive.impl.CrowdingDistanceArchive;
import jmetal5.util.comparator.RankingAndCrowdingDistanceComparator;
import jmetal5.util.evaluator.SolutionListEvaluator;
import jmetal5.util.evaluator.impl.SequentialSolutionListEvaluator;
import jmetal5.util.neighborhood.Neighborhood;
import jmetal5.util.neighborhood.impl.C9;

import java.util.List;

/**
 * Created by juanjo
 */
public class MOCellBuilder<S extends Solution<?>> implements AlgorithmBuilder<MOCell<S>> {
    /**
     * MOCellBuilder class
     */
    protected final Problem<S> problem;
    protected int maxEvaluations;
    protected int populationSize;
    protected CrossoverOperator<S> crossoverOperator;
    protected MutationOperator<S> mutationOperator;
    protected SelectionOperator<List<S>, S> selectionOperator;
    protected SolutionListEvaluator<S> evaluator;
    protected Neighborhood<S> neighborhood;
    protected BoundedArchive<S> archive;

    /**
     * MOCellBuilder constructor
     */
    public MOCellBuilder(Problem<S> problem, CrossoverOperator<S> crossoverOperator,
                         MutationOperator<S> mutationOperator) {
        this.problem = problem;
        maxEvaluations = 25000;
        populationSize = 100;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        selectionOperator = new BinaryTournamentSelection<S>(new RankingAndCrowdingDistanceComparator<S>());
        neighborhood = new C9<S>((int) Math.sqrt(populationSize), (int) Math.sqrt(populationSize));
        evaluator = new SequentialSolutionListEvaluator<S>();
        archive = new CrowdingDistanceArchive<>(populationSize);
    }

    public MOCellBuilder<S> setNeighborhood(Neighborhood<S> neighborhood) {
        this.neighborhood = neighborhood;

        return this;
    }

    public MOCell<S> build() {
        MOCell<S> algorithm = new MOCell<S>(problem, maxEvaluations, populationSize, archive,
                neighborhood, crossoverOperator, mutationOperator, selectionOperator, evaluator);

        return algorithm;
    }

    /* Getters */
    public Problem<S> getProblem() {
        return problem;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public MOCellBuilder<S> setMaxEvaluations(int maxEvaluations) {
        if (maxEvaluations < 0) {
            throw new JMetalException("maxEvaluations is negative: " + maxEvaluations);
        }
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public MOCellBuilder<S> setPopulationSize(int populationSize) {
        if (populationSize < 0) {
            throw new JMetalException("Population size is negative: " + populationSize);
        }

        this.populationSize = populationSize;

        return this;
    }

    public BoundedArchive<S> getArchive() {
        return archive;
    }

    public MOCellBuilder<S> setArchive(BoundedArchive<S> archive) {
        this.archive = archive;

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

    public MOCellBuilder<S> setSelectionOperator(SelectionOperator<List<S>, S> selectionOperator) {
        if (selectionOperator == null) {
            throw new JMetalException("selectionOperator is null");
        }
        this.selectionOperator = selectionOperator;

        return this;
    }

    public SolutionListEvaluator<S> getSolutionListEvaluator() {
        return evaluator;
    }

    public MOCellBuilder<S> setSolutionListEvaluator(SolutionListEvaluator<S> evaluator) {
        if (evaluator == null) {
            throw new JMetalException("evaluator is null");
        }
        this.evaluator = evaluator;

        return this;
    }

    public enum MOCellVariant {MOCell, SteadyStateMOCell, Measures}
}
