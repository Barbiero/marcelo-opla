package jmetal5.util.extremevalues.impl;

import jmetal5.solution.Solution;
import jmetal5.util.extremevalues.ExtremeValuesFinder;
import jmetal5.util.front.imp.ArrayFront;

import java.util.List;

/**
 * Class for finding the extreme values of a list of objects
 *
 * @author Antonio J. Nebro
 */
public class SolutionListExtremeValues implements ExtremeValuesFinder<List<Solution<?>>, List<Double>> {

    @Override
    public List<Double> findLowestValues(List<Solution<?>> solutionList) {
        return new FrontExtremeValues().findLowestValues(new ArrayFront(solutionList));
    }

    @Override
    public List<Double> findHighestValues(List<Solution<?>> solutionList) {
        return new FrontExtremeValues().findHighestValues(new ArrayFront(solutionList));
    }
}
