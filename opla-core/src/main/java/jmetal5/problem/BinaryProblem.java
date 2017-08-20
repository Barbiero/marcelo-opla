package jmetal5.problem;

import jmetal5.solution.BinarySolution;

/**
 * Interface representing binary problems
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface BinaryProblem extends Problem<BinarySolution> {
    int getNumberOfBits(int index);

    int getTotalNumberOfBits();
}
