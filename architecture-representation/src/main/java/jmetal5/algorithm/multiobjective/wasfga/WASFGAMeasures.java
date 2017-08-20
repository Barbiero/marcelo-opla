package jmetal5.algorithm.multiobjective.wasfga;

import jmetal5.measure.Measurable;
import jmetal5.measure.MeasureManager;
import jmetal5.measure.impl.BasicMeasure;
import jmetal5.measure.impl.CountingMeasure;
import jmetal5.measure.impl.DurationMeasure;
import jmetal5.measure.impl.SimpleMeasureManager;
import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.evaluator.SolutionListEvaluator;

import java.util.List;

/**
 * Implementation of the preference based algorithm named WASF-GA on jMetal5.0
 *
 * @author Jorge Rodriguez
 */
@SuppressWarnings("serial")
public class WASFGAMeasures<S extends Solution<?>> extends WASFGA<S> implements Measurable {

    protected CountingMeasure iterations;
    protected DurationMeasure durationMeasure;
    protected SimpleMeasureManager measureManager;
    protected BasicMeasure<List<S>> solutionListMeasure;

    /**
     * Constructor
     *
     * @param problem Problem to solve
     */
    public WASFGAMeasures(Problem<S> problem,
                          int populationSize,
                          int maxIterations,
                          CrossoverOperator<S> crossoverOperator,
                          MutationOperator<S> mutationOperator,
                          SelectionOperator<List<S>, S> selectionOperator,
                          SolutionListEvaluator<S> evaluator,
                          List<Double> referencePoint) {

        super(problem,
                populationSize,
                maxIterations,
                crossoverOperator,
                mutationOperator,
                selectionOperator,
                evaluator,
                referencePoint);
        this.initMeasures();
    }

    @Override
    protected void initProgress() {
        this.iterations.reset();
    }

    @Override
    protected void updateProgress() {
        this.iterations.increment();
        solutionListMeasure.push(getResult());
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return this.iterations.get() >= maxIterations;
    }

    @Override
    public void run() {
        durationMeasure.reset();
        durationMeasure.start();
        super.run();
        durationMeasure.stop();
    }

    /* Measures code */
    private void initMeasures() {
        durationMeasure = new DurationMeasure();
        iterations = new CountingMeasure(0);
        solutionListMeasure = new BasicMeasure<>();

        measureManager = new SimpleMeasureManager();
        measureManager.setPullMeasure("currentExecutionTime", durationMeasure);
        measureManager.setPullMeasure("currentEvaluation", iterations);

        measureManager.setPushMeasure("currentPopulation", solutionListMeasure);
        measureManager.setPushMeasure("currentEvaluation", iterations);
    }

    @Override
    public String getName() {
        return "WASFGA";
    }

    @Override
    public String getDescription() {
        return "Weighting Achievement Scalarizing Function Genetic Algorithm. Version using Measures";
    }

    @Override
    public MeasureManager getMeasureManager() {
        return this.measureManager;
    }
}
