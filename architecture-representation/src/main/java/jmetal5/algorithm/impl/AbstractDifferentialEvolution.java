package jmetal5.algorithm.impl;

import jmetal5.operator.impl.crossover.DifferentialEvolutionCrossover;
import jmetal5.operator.impl.selection.DifferentialEvolutionSelection;
import jmetal5.solution.DoubleSolution;

/**
 * Abstract class representing differential evolution (DE) algorithms
 *
 * @author Antonio J. Nebro
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractDifferentialEvolution<Result> extends AbstractEvolutionaryAlgorithm<DoubleSolution, Result> {
    protected DifferentialEvolutionCrossover crossoverOperator;
    protected DifferentialEvolutionSelection selectionOperator;
}
