package jmetal5.algorithm.multiobjective.nsgaii;

import jmetal5.algorithm.impl.AbstractGeneticAlgorithm;
import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.operator.impl.selection.RankingAndCrowdingSelection;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.SolutionListUtils;
import jmetal5.util.evaluator.SolutionListEvaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class NSGAII<S extends Solution<?>> extends AbstractGeneticAlgorithm<S, List<S>> {
    protected final int maxEvaluations;

    protected final SolutionListEvaluator<S> evaluator;

    protected int evaluations;

    /**
     * Constructor
     */
    public NSGAII(Problem<S> problem, int maxEvaluations, int populationSize,
                  CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
                  SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
        super(problem);
        this.maxEvaluations = maxEvaluations;
        setMaxPopulationSize(populationSize);

        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.selectionOperator = selectionOperator;

        this.evaluator = evaluator;
    }

    @Override
    protected void initProgress() {
        evaluations = getMaxPopulationSize();
    }

    @Override
    protected void updateProgress() {
        evaluations += getMaxPopulationSize();
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return evaluations >= maxEvaluations;
    }

    @Override
    protected List<S> evaluatePopulation(List<S> population) {
        population = evaluator.evaluate(population, getProblem());

        return population;
    }

    @Override
    protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
        List<S> jointPopulation = new ArrayList<>();
        jointPopulation.addAll(population);
        jointPopulation.addAll(offspringPopulation);

        RankingAndCrowdingSelection<S> rankingAndCrowdingSelection;
        rankingAndCrowdingSelection = new RankingAndCrowdingSelection<S>(getMaxPopulationSize());

        return rankingAndCrowdingSelection.execute(jointPopulation);
    }

    @Override
    public List<S> getResult() {
        return getNonDominatedSolutions(getPopulation());
    }

    protected List<S> getNonDominatedSolutions(List<S> solutionList) {
        return SolutionListUtils.getNondominatedSolutions(solutionList);
    }

    @Override
    public String getName() {
        return "NSGAII";
    }

    @Override
    public String getDescription() {
        return "Nondominated Sorting Genetic Algorithm version II";
    }
}
