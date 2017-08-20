package jmetal5.util.experiment.util;

import jmetal5.problem.Problem;
import jmetal5.solution.Solution;

/**
 * Class used to add a tag field to a problem.
 *
 * @author Antonio J. Nebro <ajnebro@uma.es>
 */
public class ExperimentProblem<S extends Solution<?>> {
    private Problem<S> problem;
    private String tag;

    public ExperimentProblem(Problem<S> problem, String tag) {
        this.problem = problem;
        this.tag = tag;
    }

    public ExperimentProblem(Problem<S> problem) {
        this.problem = problem;
        this.tag = problem.getName();
    }

    public Problem<S> getProblem() {
        return problem;
    }

    public String getTag() {
        return tag;
    }
}
