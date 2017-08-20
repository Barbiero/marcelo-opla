package jmetal5.util.referencePoint;

import jmetal5.solution.Solution;
import jmetal5.util.point.util.PointSolution;

/**
 * Interface representing a reference point
 *
 * @author <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public abstract class ReferencePoint extends PointSolution {
    public ReferencePoint(int numberOfObjectives) {
        super(numberOfObjectives);
    }

    public abstract void update(Solution<?> solution);
}
