package jmetal5.algorithm.multiobjective.paes;

import jmetal5.operator.MutationOperator;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.AlgorithmBuilder;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class PAESBuilder<S extends Solution<?>> implements AlgorithmBuilder<PAES<S>> {
    private Problem<S> problem;

    private int archiveSize;
    private int maxEvaluations;
    private int biSections;

    private MutationOperator<S> mutationOperator;

    public PAESBuilder(Problem<S> problem) {
        this.problem = problem;
    }

    public PAES<S> build() {
        return new PAES<S>(problem, archiveSize, maxEvaluations, biSections, mutationOperator);
    }

    /*
     * Getters
     */
    public Problem<S> getProblem() {
        return problem;
    }

    public int getArchiveSize() {
        return archiveSize;
    }

    public PAESBuilder<S> setArchiveSize(int archiveSize) {
        this.archiveSize = archiveSize;

        return this;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public PAESBuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public int getBiSections() {
        return biSections;
    }

    public PAESBuilder<S> setBiSections(int biSections) {
        this.biSections = biSections;

        return this;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public PAESBuilder<S> setMutationOperator(MutationOperator<S> mutation) {
        mutationOperator = mutation;

        return this;
    }
}
