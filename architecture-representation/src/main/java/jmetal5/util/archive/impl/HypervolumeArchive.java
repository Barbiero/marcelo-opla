package jmetal5.util.archive.impl;

import jmetal5.qualityindicator.impl.Hypervolume;
import jmetal5.solution.Solution;
import jmetal5.util.SolutionListUtils;
import jmetal5.util.comparator.HypervolumeContributionComparator;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Antonio J. Nebro on 24/09/14.
 */
@SuppressWarnings("serial")
public class HypervolumeArchive<S extends Solution<?>> extends AbstractBoundedArchive<S> {
    Hypervolume<S> hypervolume;
    private Comparator<S> comparator;

    public HypervolumeArchive(int maxSize, Hypervolume<S> hypervolume) {
        super(maxSize);
        comparator = new HypervolumeContributionComparator<S>();
        this.hypervolume = hypervolume;
    }

    @Override
    public void prune() {
        if (getSolutionList().size() > getMaxSize()) {
            computeDensityEstimator();
            S worst = new SolutionListUtils().findWorstSolution(getSolutionList(), comparator);
            getSolutionList().remove(worst);
        }
    }

    @Override
    public Comparator<S> getComparator() {
        return comparator;
    }

    @Override
    public void computeDensityEstimator() {
        hypervolume.computeHypervolumeContribution(archive.getSolutionList(), archive.getSolutionList());
    }

    @Override
    public void sortByDensityEstimator() {
        Collections.sort(getSolutionList(), new HypervolumeContributionComparator<S>());
    }
}
