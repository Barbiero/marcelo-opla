package jmetal5.algorithm.multiobjective.mochc;

/**
 * Created by ajnebro on 21/11/14.
 */

import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.problem.BinaryProblem;
import jmetal5.solution.BinarySolution;
import jmetal5.util.AlgorithmBuilder;
import jmetal5.util.evaluator.SolutionListEvaluator;
import jmetal5.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.util.List;

/**
 * Builder class
 */
public class MOCHCBuilder implements AlgorithmBuilder<MOCHC> {
    BinaryProblem problem;
    SolutionListEvaluator<BinarySolution> evaluator;
    int populationSize;
    int maxEvaluations;
    int convergenceValue;
    double preservedPopulation;
    double initialConvergenceCount;
    CrossoverOperator<BinarySolution> crossoverOperator;
    MutationOperator<BinarySolution> cataclysmicMutation;
    SelectionOperator<List<BinarySolution>, BinarySolution> parentSelection;
    SelectionOperator<List<BinarySolution>, List<BinarySolution>> newGenerationSelection;

    public MOCHCBuilder(BinaryProblem problem) {
        this.problem = problem;
        evaluator = new SequentialSolutionListEvaluator<BinarySolution>();
        populationSize = 100;
        maxEvaluations = 25000;
        convergenceValue = 3;
        preservedPopulation = 0.05;
        initialConvergenceCount = 0.25;
    }

    /* Getters */
    public BinaryProblem getProblem() {
        return problem;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    /* Setters */
    public MOCHCBuilder setPopulationSize(int populationSize) {
        this.populationSize = populationSize;

        return this;
    }

    public int getMaxEvaluation() {
        return maxEvaluations;
    }

    public double getInitialConvergenceCount() {
        return initialConvergenceCount;
    }

    public MOCHCBuilder setInitialConvergenceCount(double initialConvergenceCount) {
        this.initialConvergenceCount = initialConvergenceCount;

        return this;
    }

    public int getConvergenceValue() {
        return convergenceValue;
    }

    public MOCHCBuilder setConvergenceValue(int convergenceValue) {
        this.convergenceValue = convergenceValue;

        return this;
    }

    public CrossoverOperator<BinarySolution> getCrossover() {
        return crossoverOperator;
    }

    public MOCHCBuilder setCrossover(CrossoverOperator<BinarySolution> crossover) {
        this.crossoverOperator = crossover;

        return this;
    }

    public MutationOperator<BinarySolution> getCataclysmicMutation() {
        return cataclysmicMutation;
    }

    public MOCHCBuilder setCataclysmicMutation(MutationOperator<BinarySolution> cataclysmicMutation) {
        this.cataclysmicMutation = cataclysmicMutation;

        return this;
    }

    public SelectionOperator<List<BinarySolution>, BinarySolution> getParentSelection() {
        return parentSelection;
    }

    public MOCHCBuilder setParentSelection(SelectionOperator<List<BinarySolution>, BinarySolution> parentSelection) {
        this.parentSelection = parentSelection;

        return this;
    }

    public SelectionOperator<List<BinarySolution>, List<BinarySolution>> getNewGenerationSelection() {
        return newGenerationSelection;
    }

    public MOCHCBuilder setNewGenerationSelection(SelectionOperator<List<BinarySolution>, List<BinarySolution>> newGenerationSelection) {
        this.newGenerationSelection = newGenerationSelection;

        return this;
    }

    public double getPreservedPopulation() {
        return preservedPopulation;
    }

    public MOCHCBuilder setPreservedPopulation(double preservedPopulation) {
        this.preservedPopulation = preservedPopulation;

        return this;
    }

    public MOCHCBuilder setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public MOCHCBuilder setEvaluator(SolutionListEvaluator<BinarySolution> evaluator) {
        this.evaluator = evaluator;

        return this;
    }

    public MOCHC build() {
        MOCHC algorithm =
                new MOCHC(problem, populationSize, maxEvaluations, convergenceValue, preservedPopulation,
                        initialConvergenceCount, crossoverOperator, cataclysmicMutation, newGenerationSelection,
                        parentSelection, evaluator);

        return algorithm;
    }
}
