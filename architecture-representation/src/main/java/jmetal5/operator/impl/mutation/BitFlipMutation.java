package jmetal5.operator.impl.mutation;

import jmetal5.operator.MutationOperator;
import jmetal5.solution.BinarySolution;
import jmetal5.util.JMetalException;
import jmetal5.util.pseudorandom.JMetalRandom;
import jmetal5.util.pseudorandom.RandomGenerator;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @version 1.0
 * <p>
 * This class implements a bit flip mutation operator.
 */
@SuppressWarnings("serial")
public class BitFlipMutation implements MutationOperator<BinarySolution> {
    private double mutationProbability;
    private RandomGenerator<Double> randomGenerator;

    /**
     * Constructor
     */
    public BitFlipMutation(double mutationProbability) {
        this(mutationProbability, () -> JMetalRandom.getInstance().nextDouble());
    }

    /**
     * Constructor
     */
    public BitFlipMutation(double mutationProbability, RandomGenerator<Double> randomGenerator) {
        if (mutationProbability < 0) {
            throw new JMetalException("Mutation probability is negative: " + mutationProbability);
        }
        this.mutationProbability = mutationProbability;
        this.randomGenerator = randomGenerator;
    }

    /* Getter */
    public double getMutationProbability() {
        return mutationProbability;
    }

    /* Setters */
    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    /**
     * Execute() method
     */
    @Override
    public BinarySolution execute(BinarySolution solution) {
        if (null == solution) {
            throw new JMetalException("Null parameter");
        }

        doMutation(mutationProbability, solution);
        return solution;
    }

    /**
     * Perform the mutation operation
     *
     * @param probability Mutation setProbability
     * @param solution    The solution to mutate
     */
    public void doMutation(double probability, BinarySolution solution) {
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            for (int j = 0; j < solution.getVariableValue(i).getBinarySetLength(); j++) {
                if (randomGenerator.getRandomValue() <= probability) {
                    solution.getVariableValue(i).flip(j);
                }
            }
        }
    }
}
