package jmetal5.algorithm.multiobjective.randomsearch;

import jmetal5.algorithm.Algorithm;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.archive.impl.NonDominatedSolutionListArchive;

import java.util.List;

/**
 * This class implements a simple random search algorithm.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class RandomSearch<S extends Solution<?>> implements Algorithm<List<S>> {
    NonDominatedSolutionListArchive<S> nonDominatedArchive;
    private Problem<S> problem;
    private int maxEvaluations;

    /**
     * Constructor
     */
    public RandomSearch(Problem<S> problem, int maxEvaluations) {
        this.problem = problem;
        this.maxEvaluations = maxEvaluations;
        nonDominatedArchive = new NonDominatedSolutionListArchive<S>();
    }

    /* Getter */
    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    @Override
    public void run() {
        S newSolution;
        for (int i = 0; i < maxEvaluations; i++) {
            newSolution = problem.createSolution();
            problem.evaluate(newSolution);
            nonDominatedArchive.add(newSolution);
        }
    }

    @Override
    public List<S> getResult() {
        return nonDominatedArchive.getSolutionList();
    }

    @Override
    public String getName() {
        return "RS";
    }

    @Override
    public String getDescription() {
        return "Multi-objective random search algorithm";
    }
} 
