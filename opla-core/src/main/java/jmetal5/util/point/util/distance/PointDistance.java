package jmetal5.util.point.util.distance;

import jmetal5.util.point.Point;

/**
 * Interface representing classes for computing a distance between two points
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface PointDistance {
    double compute(Point pointA, Point pointB);
}
