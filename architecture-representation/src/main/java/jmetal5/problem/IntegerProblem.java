package jmetal5.problem;

import jmetal5.solution.IntegerSolution;

/**
 * Interface representing integer problems
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface IntegerProblem extends Problem<IntegerSolution> {
    Integer getLowerBound(int index);

    Integer getUpperBound(int index);
}
