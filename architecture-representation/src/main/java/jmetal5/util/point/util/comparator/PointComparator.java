package jmetal5.util.point.util.comparator;

import jmetal5.util.JMetalException;
import jmetal5.util.point.Point;

import java.util.Comparator;

/**
 * Point comparator. Starts the comparison from front last point dimension to the first one
 *
 * @author Antonio J. Nebro
 */
public class PointComparator implements Comparator<Point> {
    private boolean maximizing;

    public PointComparator() {
        this.maximizing = true;
    }

    public void setMaximizing() {
        maximizing = true;
    }

    public void setMinimizing() {
        maximizing = false;
    }

    /**
     * Compares two Point objects
     *
     * @param pointOne An object that reference a Point
     * @param pointTwo An object that reference a Point
     * @return -1 if o1 < o1, 1 if o1 > o2 or 0 in other case.
     */
    @Override
    public int compare(Point pointOne, Point pointTwo) {
        if (pointOne == null) {
            throw new JMetalException("PointOne is null");
        } else if (pointTwo == null) {
            throw new JMetalException("PointTwo is null");
        } else if (pointOne.getNumberOfDimensions() != pointTwo.getNumberOfDimensions()) {
            throw new JMetalException("Points have different size: "
                    + pointOne.getNumberOfDimensions() + " and "
                    + pointTwo.getNumberOfDimensions());
        }

        for (int i = pointOne.getNumberOfDimensions() - 1; i >= 0; i--) {
            if (isBetter(pointOne.getDimensionValue(i), pointTwo.getDimensionValue(i))) {
                return -1;
            } else if (isBetter(pointTwo.getDimensionValue(i), pointOne.getDimensionValue(i))) {
                return 1;
            }
        }
        return 0;
    }

    private boolean isBetter(double v1, double v2) {
        if (maximizing) {
            return (v1 > v2);
        } else {
            return (v2 > v1);
        }
    }
}
