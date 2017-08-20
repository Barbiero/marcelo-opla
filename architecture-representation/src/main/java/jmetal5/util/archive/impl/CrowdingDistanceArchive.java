package jmetal5.util.archive.impl;

import jmetal5.solution.Solution;
import jmetal5.util.SolutionListUtils;
import jmetal5.util.comparator.CrowdingDistanceComparator;
import jmetal5.util.solutionattribute.DensityEstimator;
import jmetal5.util.solutionattribute.impl.CrowdingDistance;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Antonio J. Nebro on 24/09/14.
 * Modified by Juanjo on 07/04/2015
 */
@SuppressWarnings("serial")
public class CrowdingDistanceArchive<S extends Solution<?>> extends AbstractBoundedArchive<S> {
    private Comparator<S> crowdingDistanceComparator;
    private DensityEstimator<S> crowdingDistance;

    public CrowdingDistanceArchive(int maxSize) {
        super(maxSize);
        crowdingDistanceComparator = new CrowdingDistanceComparator<S>();
        crowdingDistance = new CrowdingDistance<S>();
    }

    @Override
    public void prune() {
        if (getSolutionList().size() > getMaxSize()) {
            computeDensityEstimator();
            S worst = new SolutionListUtils().findWorstSolution(getSolutionList(), crowdingDistanceComparator);
            getSolutionList().remove(worst);
        }
    }

    @Override
    public Comparator<S> getComparator() {
        return crowdingDistanceComparator;
    }

    @Override
    public void computeDensityEstimator() {
        crowdingDistance.computeDensityEstimator(getSolutionList());
    }

    @Override
    public void sortByDensityEstimator() {
        Collections.sort(getSolutionList(), new CrowdingDistanceComparator<S>());
    }
}
