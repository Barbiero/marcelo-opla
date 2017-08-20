package jmetal5.util.referencePoint.impl;

import jmetal5.solution.Solution;
import jmetal5.util.JMetalException;
import jmetal5.util.referencePoint.ReferencePoint;

/**
 * Class representing an ideal point (minimization is assumed)
 *
 * @author Antonio J.Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class IdealPoint extends ReferencePoint {
    private static final double DEFAULT_INITIAL_VALUE = Double.POSITIVE_INFINITY;

    public IdealPoint(int numberOfPoints) {
        super(numberOfPoints);

        for (int i = 0; i < numberOfPoints; i++) {
            this.setObjective(i, DEFAULT_INITIAL_VALUE);
        }
    }

    @Override
    public void update(Solution<?> solution) {
        if (solution == null) {
            throw new JMetalException("The solution is null");
        } else if (solution.getNumberOfObjectives() != this.getNumberOfObjectives()) {
            throw new JMetalException("The number of objectives of the solution ("
                    + solution.getNumberOfObjectives()
                    + ") "
                    + "is different to the size of the reference point("
                    + this.getNumberOfObjectives()
                    + ")"
            );
        }

        for (int i = 0; i < this.getNumberOfObjectives(); i++) {
            if (this.getObjective(i) > solution.getObjective(i)) {
                this.setObjective(i, solution.getObjective(i));
            }
        }
    }
}
