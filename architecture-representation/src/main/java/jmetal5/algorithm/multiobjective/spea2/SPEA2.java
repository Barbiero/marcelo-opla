package jmetal5.algorithm.multiobjective.spea2;

import jmetal5.algorithm.impl.AbstractGeneticAlgorithm;
import jmetal5.algorithm.multiobjective.spea2.util.EnvironmentalSelection;
import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.evaluator.SolutionListEvaluator;
import jmetal5.util.solutionattribute.impl.StrengthRawFitness;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan J. Durillo
 **/
@SuppressWarnings("serial")
public class SPEA2<S extends Solution<?>> extends AbstractGeneticAlgorithm<S, List<S>> {
    protected final int maxIterations;
    protected final SolutionListEvaluator<S> evaluator;
    protected final StrengthRawFitness<S> strenghtRawFitness = new StrengthRawFitness<S>();
    protected final EnvironmentalSelection<S> environmentalSelection;
    protected int iterations;
    protected List<S> archive;

    public SPEA2(Problem<S> problem, int maxIterations, int populationSize,
                 CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
                 SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
        super(problem);
        this.maxIterations = maxIterations;
        this.setMaxPopulationSize(populationSize);

        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.selectionOperator = selectionOperator;
        this.environmentalSelection = new EnvironmentalSelection<S>(populationSize);

        this.archive = new ArrayList<>(populationSize);

        this.evaluator = evaluator;
    }

    @Override
    protected void initProgress() {
        iterations = 1;
    }

    @Override
    protected void updateProgress() {
        iterations++;
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return iterations >= maxIterations;
    }

    @Override
    protected List<S> evaluatePopulation(List<S> population) {
        population = evaluator.evaluate(population, getProblem());
        return population;
    }

    @Override
    protected List<S> selection(List<S> population) {
        List<S> union = new ArrayList<>(2 * getMaxPopulationSize());
        union.addAll(archive);
        union.addAll(population);
        strenghtRawFitness.computeDensityEstimator(union);
        archive = environmentalSelection.execute(union);
        return archive;
    }

    @Override
    protected List<S> reproduction(List<S> population) {
        List<S> offSpringPopulation = new ArrayList<>(getMaxPopulationSize());

        while (offSpringPopulation.size() < getMaxPopulationSize()) {
            List<S> parents = new ArrayList<>(2);
            S candidateFirstParent = selectionOperator.execute(population);
            parents.add(candidateFirstParent);
            S candidateSecondParent;
            candidateSecondParent = selectionOperator.execute(population);
            parents.add(candidateSecondParent);

            List<S> offspring = crossoverOperator.execute(parents);
            mutationOperator.execute(offspring.get(0));
            offSpringPopulation.add(offspring.get(0));
        }
        return offSpringPopulation;
    }

    @Override
    protected List<S> replacement(List<S> population,
                                  List<S> offspringPopulation) {
        return offspringPopulation;
    }

    @Override
    public List<S> getResult() {
        return archive;
    }

    @Override
    public String getName() {
        return "SPEA2";
    }

    @Override
    public String getDescription() {
        return "Strength Pareto. Evolutionary Algorithm";
    }
}
