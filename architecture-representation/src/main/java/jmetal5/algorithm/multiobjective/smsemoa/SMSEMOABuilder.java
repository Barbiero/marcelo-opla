package jmetal5.algorithm.multiobjective.smsemoa;

import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.operator.impl.selection.RandomSelection;
import jmetal5.problem.Problem;
import jmetal5.qualityindicator.impl.Hypervolume;
import jmetal5.qualityindicator.impl.hypervolume.PISAHypervolume;
import jmetal5.solution.Solution;
import jmetal5.util.AlgorithmBuilder;

import java.util.List;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class SMSEMOABuilder<S extends Solution<?>> implements AlgorithmBuilder<SMSEMOA<S>> {
    private static final double DEFAULT_OFFSET = 100.0;

    protected Problem<S> problem;

    protected int populationSize;
    protected int maxEvaluations;

    protected CrossoverOperator<S> crossoverOperator;
    protected MutationOperator<S> mutationOperator;
    protected SelectionOperator<List<S>, S> selectionOperator;

    protected double offset;

    protected Hypervolume<S> hypervolumeImplementation;

    public SMSEMOABuilder(Problem<S> problem, CrossoverOperator<S> crossoverOperator,
                          MutationOperator<S> mutationOperator) {
        this.problem = problem;
        this.offset = DEFAULT_OFFSET;
        populationSize = 100;
        maxEvaluations = 25000;
        this.hypervolumeImplementation = new PISAHypervolume<>();
        hypervolumeImplementation.setOffset(offset);

        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.selectionOperator = new RandomSelection<S>();
    }

    public SMSEMOABuilder<S> setHypervolumeImplementation(Hypervolume<S> hypervolumeImplementation) {
        this.hypervolumeImplementation = hypervolumeImplementation;

        return this;
    }

    @Override
    public SMSEMOA<S> build() {
        return new SMSEMOA<S>(problem, maxEvaluations, populationSize, offset,
                crossoverOperator, mutationOperator, selectionOperator, hypervolumeImplementation);
    }

    public Problem<S> getProblem() {
        return problem;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public SMSEMOABuilder<S> setPopulationSize(int populationSize) {
        this.populationSize = populationSize;

        return this;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public SMSEMOABuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return crossoverOperator;
    }

  /*
   * Getters
   */

    public SMSEMOABuilder<S> setCrossoverOperator(CrossoverOperator<S> crossover) {
        crossoverOperator = crossover;

        return this;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public SMSEMOABuilder<S> setMutationOperator(MutationOperator<S> mutation) {
        mutationOperator = mutation;

        return this;
    }

    public SelectionOperator<List<S>, S> getSelectionOperator() {
        return selectionOperator;
    }

    public SMSEMOABuilder<S> setSelectionOperator(SelectionOperator<List<S>, S> selection) {
        selectionOperator = selection;

        return this;
    }

    public double getOffset() {
        return offset;
    }

    public SMSEMOABuilder<S> setOffset(double offset) {
        this.offset = offset;

        return this;
    }
}
