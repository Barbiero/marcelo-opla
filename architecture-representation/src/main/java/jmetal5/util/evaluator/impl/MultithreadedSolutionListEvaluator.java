package jmetal5.util.evaluator.impl;

import jmetal5.problem.ConstrainedProblem;
import jmetal5.problem.Problem;
import jmetal5.util.JMetalLogger;
import jmetal5.util.evaluator.SolutionListEvaluator;

import java.util.List;

/**
 * @author Antonio J. Nebro
 */
@SuppressWarnings("serial")
public class MultithreadedSolutionListEvaluator<S> implements SolutionListEvaluator<S> {
    private int numberOfThreads;

    public MultithreadedSolutionListEvaluator(int numberOfThreads, Problem<S> problem) {
        if (numberOfThreads == 0) {
            this.numberOfThreads = Runtime.getRuntime().availableProcessors();
        } else {
            this.numberOfThreads = numberOfThreads;
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
                    "" + this.numberOfThreads);
        }
        JMetalLogger.logger.info("Number of cores: " + numberOfThreads);
    }

    @Override
    public List<S> evaluate(List<S> solutionList, Problem<S> problem) {
        if (problem instanceof ConstrainedProblem) {
            solutionList.parallelStream().forEach(s -> {
                problem.evaluate(s);
                ((ConstrainedProblem<S>) problem).evaluateConstraints(s);
            });
        } else {
            solutionList.parallelStream().forEach(s -> problem.evaluate(s));
        }

        return solutionList;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    @Override
    public void shutdown() {
    }

}
