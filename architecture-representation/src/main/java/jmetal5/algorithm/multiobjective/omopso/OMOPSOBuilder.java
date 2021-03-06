package jmetal5.algorithm.multiobjective.omopso;

import jmetal5.operator.MutationOperator;
import jmetal5.operator.impl.mutation.NonUniformMutation;
import jmetal5.operator.impl.mutation.UniformMutation;
import jmetal5.problem.DoubleProblem;
import jmetal5.solution.DoubleSolution;
import jmetal5.util.AlgorithmBuilder;
import jmetal5.util.evaluator.SolutionListEvaluator;

/**
 * Class implementing the OMOPSO algorithm
 */
public class OMOPSOBuilder implements AlgorithmBuilder<OMOPSO> {
    protected DoubleProblem problem;
    protected SolutionListEvaluator<DoubleSolution> evaluator;

    private int swarmSize = 100;
    private int archiveSize = 100;
    private int maxIterations = 25000;

    private UniformMutation uniformMutation;
    private NonUniformMutation nonUniformMutation;

    public OMOPSOBuilder(DoubleProblem problem, SolutionListEvaluator<DoubleSolution> evaluator) {
        this.evaluator = evaluator;
        this.problem = problem;
    }

    /* Getters */
    public int getArchiveSize() {
        return archiveSize;
    }

    public OMOPSOBuilder setArchiveSize(int archiveSize) {
        this.archiveSize = archiveSize;

        return this;
    }

    public int getSwarmSize() {
        return swarmSize;
    }

    public OMOPSOBuilder setSwarmSize(int swarmSize) {
        this.swarmSize = swarmSize;

        return this;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public OMOPSOBuilder setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;

        return this;
    }

    public UniformMutation getUniformMutation() {
        return uniformMutation;
    }

    public OMOPSOBuilder setUniformMutation(MutationOperator<DoubleSolution> uniformMutation) {
        this.uniformMutation = (UniformMutation) uniformMutation;

        return this;
    }

    public NonUniformMutation getNonUniformMutation() {
        return nonUniformMutation;
    }

    public OMOPSOBuilder setNonUniformMutation(MutationOperator<DoubleSolution> nonUniformMutation) {
        this.nonUniformMutation = (NonUniformMutation) nonUniformMutation;

        return this;
    }

    public OMOPSO build() {
        return new OMOPSO(problem, evaluator, swarmSize, maxIterations, archiveSize, uniformMutation,
                nonUniformMutation);
    }
}
