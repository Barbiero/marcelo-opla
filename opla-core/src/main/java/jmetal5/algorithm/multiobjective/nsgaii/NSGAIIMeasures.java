package jmetal5.algorithm.multiobjective.nsgaii;

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
import jmetal5.qualityindicator.impl.hypervolume.PISAHypervolume;
import jmetal5.solution.Solution;
import jmetal5.util.evaluator.SolutionListEvaluator;
import jmetal5.util.front.Front;
import jmetal5.util.front.imp.ArrayFront;
import jmetal5.util.solutionattribute.Ranking;
import jmetal5.util.solutionattribute.impl.DominanceRanking;

import java.util.List;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class NSGAIIMeasures<S extends Solution<?>> extends NSGAII<S> implements Measurable {
    protected CountingMeasure evaluations;
    protected DurationMeasure durationMeasure;
    protected SimpleMeasureManager measureManager;

    protected BasicMeasure<List<S>> solutionListMeasure;
    protected BasicMeasure<Integer> numberOfNonDominatedSolutionsInPopulation;
    protected BasicMeasure<Double> hypervolumeValue;

    protected Front referenceFront;

    /**
     * Constructor
     */
    public NSGAIIMeasures(Problem<S> problem, int maxIterations, int populationSize,
                          CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
                          SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
        super(problem, maxIterations, populationSize, crossoverOperator, mutationOperator,
                selectionOperator, evaluator);

        referenceFront = new ArrayFront();

        initMeasures();
    }

    @Override
    protected void initProgress() {
        evaluations.reset(getMaxPopulationSize());
    }

    @Override
    protected void updateProgress() {
        evaluations.increment(getMaxPopulationSize());

        solutionListMeasure.push(getPopulation());

        //if (referenceFront.getNumberOfPoints() > 0) {
        hypervolumeValue.push(
                new PISAHypervolume<S>(referenceFront).evaluate(
                        getNonDominatedSolutions(getPopulation())));
        //}
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return evaluations.get() >= maxEvaluations;
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
        evaluations = new CountingMeasure(0);
        numberOfNonDominatedSolutionsInPopulation = new BasicMeasure<>();
        solutionListMeasure = new BasicMeasure<>();
        hypervolumeValue = new BasicMeasure<>();

        measureManager = new SimpleMeasureManager();
        measureManager.setPullMeasure("currentExecutionTime", durationMeasure);
        measureManager.setPullMeasure("currentEvaluation", evaluations);
        measureManager.setPullMeasure("numberOfNonDominatedSolutionsInPopulation",
                numberOfNonDominatedSolutionsInPopulation);

        measureManager.setPushMeasure("currentPopulation", solutionListMeasure);
        measureManager.setPushMeasure("currentEvaluation", evaluations);
        measureManager.setPushMeasure("hypervolume", hypervolumeValue);
    }

    @Override
    public MeasureManager getMeasureManager() {
        return measureManager;
    }

    @Override
    protected List<S> replacement(List<S> population,
                                  List<S> offspringPopulation) {
        List<S> pop = super.replacement(population, offspringPopulation);

        Ranking<S> ranking = new DominanceRanking<S>();
        ranking.computeRanking(population);

        numberOfNonDominatedSolutionsInPopulation.set(ranking.getSubfront(0).size());

        return pop;
    }

    public CountingMeasure getEvaluations() {
        return evaluations;
    }

    @Override
    public String getName() {
        return "NSGAIIM";
    }

    @Override
    public String getDescription() {
        return "Nondominated Sorting Genetic Algorithm version II. Version using measures";
    }

    public void setReferenceFront(Front referenceFront) {
        this.referenceFront = referenceFront;
    }
}
