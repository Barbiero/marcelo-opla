package jmetal5.util.point.util.distance;

import jmetal5.util.JMetalException;
import jmetal5.util.point.Point;

/**
 * Computes the Euclidean distance between two points
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class EuclideanDistance implements PointDistance {

    @Override
    public double compute(Point a, Point b) {
        if (a == null) {
            throw new JMetalException("The first point is null");
        } else if (b == null) {
            throw new JMetalException("The second point is null");
        } else if (a.getNumberOfDimensions() != b.getNumberOfDimensions()) {
            throw new JMetalException("The dimensions of the points are different: "
                    + a.getNumberOfDimensions() + ", " + b.getNumberOfDimensions());
        }

        double distance = 0.0;

        for (int i = 0; i < a.getNumberOfDimensions(); i++) {
            distance += Math.pow(a.getDimensionValue(i) - b.getDimensionValue(i), 2.0);
        }
        return Math.sqrt(distance);
    }
}
