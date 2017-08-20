package jmetal5.algorithm.multiobjective.gwasfga;

import jmetal5.algorithm.multiobjective.gwasfga.util.GWASFGARanking;
import jmetal5.algorithm.multiobjective.mombi.util.ASFWASFGA;
import jmetal5.algorithm.multiobjective.mombi.util.AbstractUtilityFunctionsSet;
import jmetal5.algorithm.multiobjective.wasfga.WASFGA;
import jmetal5.algorithm.multiobjective.wasfga.util.WeightVector;
import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.SelectionOperator;
import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.evaluator.SolutionListEvaluator;
import jmetal5.util.solutionattribute.Ranking;

import java.util.List;

/**
 * This class executes the GWASFGA algorithm described in:
 * Saborido, R., Ruiz, A. B. and Luque, M. (2015). Global WASF-GA: An Evolutionary Algorithm in
 * Multiobjective Optimization to Approximate the whole Pareto Optimal Front.
 * Evolutionary Computation Accepted for publication.
 *
 * @author Juanjo Durillo
 */
public class GWASFGA<S extends Solution<?>> extends WASFGA<S> {
    private static final long serialVersionUID = 1L;
    final AbstractUtilityFunctionsSet<S> achievementScalarizingUtopia;
    final AbstractUtilityFunctionsSet<S> achievementScalarizingNadir;

    public GWASFGA(Problem<S> problem, int populationSize, int maxIterations, CrossoverOperator<S> crossoverOperator,
                   MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator,
                   SolutionListEvaluator<S> evaluator) {
        super(problem, populationSize, maxIterations, crossoverOperator, mutationOperator, selectionOperator, evaluator,
                null);
        setMaxPopulationSize(populationSize);

        double[][] weights = WeightVector.initUniformWeights2D(0.005, getMaxPopulationSize());

        int halfVectorSize = weights.length / 2;
        int evenVectorsSize = (weights.length % 2 == 0) ? halfVectorSize : (halfVectorSize + 1);
        int oddVectorsSize = halfVectorSize;

        double[][] evenVectors = new double[evenVectorsSize][getProblem().getNumberOfObjectives()];
        double[][] oddVectors = new double[oddVectorsSize][getProblem().getNumberOfObjectives()];

        int index = 0;
        for (int i = 0; i < weights.length; i = i + 2)
            evenVectors[index++] = weights[i];

        index = 0;
        for (int i = 1; i < weights.length; i = i + 2)
            oddVectors[index++] = weights[i];

        this.achievementScalarizingNadir = createUtilityFunction(this.getNadirPoint(), evenVectors);
        this.achievementScalarizingUtopia = createUtilityFunction(this.getReferencePoint(), oddVectors);

    }

    public AbstractUtilityFunctionsSet<S> createUtilityFunction(List<Double> referencePoint, double[][] weights) {
        weights = WeightVector.invertWeights(weights, true);
        ASFWASFGA<S> aux = new ASFWASFGA<>(weights, referencePoint);

        return aux;
    }

    protected Ranking<S> computeRanking(List<S> solutionList) {
        Ranking<S> ranking = new GWASFGARanking<>(this.achievementScalarizingUtopia, this.achievementScalarizingNadir);
        ranking.computeRanking(solutionList);
        return ranking;
    }

    @Override
    public String getName() {
        return "GWASFGA";
    }

    @Override
    public String getDescription() {
        return "Global Weighting Achievement Scalarizing Function Genetic Algorithm";
    }
}
