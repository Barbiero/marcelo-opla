package jmetal5.operator.impl.crossover;

import jmetal5.operator.CrossoverOperator;
import jmetal5.solution.Solution;

import java.util.List;

/**
 * Created by FlapKap on 27-05-2017.
 */
public class TwoPointCrossover<T> implements CrossoverOperator<Solution<T>> {
    NPointCrossover<T> operator;

    public TwoPointCrossover(double probability) {
        this.operator = new NPointCrossover<>(probability, 2);
    }

    @Override
    public List<Solution<T>> execute(List<Solution<T>> solutions) {
        return operator.execute(solutions);
    }

    @Override
    public int getNumberOfParents() {
        return operator.getNumberOfParents();
    }

    public double getCrossoverProbability() {
        return operator.getCrossoverProbability();
    }
}
