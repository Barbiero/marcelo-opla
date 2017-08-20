package jmetal5.algorithm.multiobjective.gde3;

import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.operator.impl.crossover.DifferentialEvolutionCrossover;
import jmetal5.operator.impl.selection.DifferentialEvolutionSelection;
import jmetal5.problem.DoubleProblem;
import jmetal5.solution.DoubleSolution;
import jmetal5.util.AlgorithmBuilder;
import jmetal5.util.evaluator.SolutionListEvaluator;
import jmetal5.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.util.List;

/**
 * This class implements the GDE3 algorithm
 */
public class GDE3Builder implements AlgorithmBuilder<GDE3> {
    protected int populationSize;
    protected int maxEvaluations;
    protected DifferentialEvolutionCrossover crossoverOperator;
    protected DifferentialEvolutionSelection selectionOperator;
    protected SolutionListEvaluator<DoubleSolution> evaluator;
    private DoubleProblem problem;

    /**
     * Constructor
     */
    public GDE3Builder(DoubleProblem problem) {
        this.problem = problem;
        maxEvaluations = 25000;
        populationSize = 100;
        selectionOperator = new DifferentialEvolutionSelection();
        crossoverOperator = new DifferentialEvolutionCrossover();
        evaluator = new SequentialSolutionListEvaluator<DoubleSolution>();
    }

    public GDE3Builder setCrossover(DifferentialEvolutionCrossover crossover) {
        crossoverOperator = crossover;

        return this;
    }

    public GDE3Builder setSelection(DifferentialEvolutionSelection selection) {
        selectionOperator = selection;

        return this;
    }

    public GDE3Builder setSolutionSetEvaluator(SolutionListEvaluator<DoubleSolution> evaluator) {
        this.evaluator = evaluator;

        return this;
    }

    public GDE3 build() {
        return new GDE3(problem, populationSize, maxEvaluations, selectionOperator, crossoverOperator, evaluator);
    }

    /* Getters */
    public CrossoverOperator<DoubleSolution> getCrossoverOperator() {
        return crossoverOperator;
    }

    public SelectionOperator<List<DoubleSolution>, List<DoubleSolution>> getSelectionOperator() {
        return selectionOperator;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    /* Setters */
    public GDE3Builder setPopulationSize(int populationSize) {
        this.populationSize = populationSize;

        return this;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public GDE3Builder setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;

        return this;
    }

}

