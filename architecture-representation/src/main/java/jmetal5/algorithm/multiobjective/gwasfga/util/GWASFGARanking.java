package jmetal5.algorithm.multiobjective.gwasfga.util;

import jmetal5.algorithm.multiobjective.mombi.util.AbstractUtilityFunctionsSet;
import jmetal5.solution.Solution;
import jmetal5.util.solutionattribute.Ranking;
import jmetal5.util.solutionattribute.impl.GenericSolutionAttribute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class GWASFGARanking<S extends Solution<?>> extends GenericSolutionAttribute<S, Integer>
        implements Ranking<S> {

    private AbstractUtilityFunctionsSet<S> utilityFunctionsUtopia;
    private AbstractUtilityFunctionsSet<S> utilityFunctionsNadir;
    private List<List<S>> rankedSubpopulations;
    private int numberOfRanks = 0;

    public GWASFGARanking(AbstractUtilityFunctionsSet<S> utilityFunctionsUtopia, AbstractUtilityFunctionsSet<S> utilityFunctionsNadir) {
        this.utilityFunctionsUtopia = utilityFunctionsUtopia;
        this.utilityFunctionsNadir = utilityFunctionsNadir;
    }

    @Override
    public Ranking<S> computeRanking(List<S> population) {

        this.numberOfRanks = (population.size() + 1) / (this.utilityFunctionsUtopia.getSize() + this.utilityFunctionsNadir.getSize());

        this.rankedSubpopulations = new ArrayList<>(this.numberOfRanks);

        for (int i = 0; i < this.numberOfRanks; i++) {
            this.rankedSubpopulations.add(new ArrayList<S>());
        }
        List<S> temporalList = new LinkedList<>();
        temporalList.addAll(population);


        for (int idx = 0; idx < this.numberOfRanks; idx++) {
            for (int weigth = 0; weigth < this.utilityFunctionsUtopia.getSize(); weigth++) {
                int toRemoveIdx = 0;
                double minimumValue = this.utilityFunctionsUtopia.evaluate(temporalList.get(0), weigth);
                for (int solutionIdx = 1; solutionIdx < temporalList.size(); solutionIdx++) {
                    double value = this.utilityFunctionsUtopia.evaluate(temporalList.get(solutionIdx), weigth);

                    if (value < minimumValue) {
                        minimumValue = value;
                        toRemoveIdx = solutionIdx;
                    }
                }

                S solutionToInsert = temporalList.remove(toRemoveIdx);
                setAttribute(solutionToInsert, idx);
                this.rankedSubpopulations.get(idx).add(solutionToInsert);
            }
            for (int weigth = 0; weigth < this.utilityFunctionsNadir.getSize(); weigth++) {
                int toRemoveIdx = 0;
                double minimumValue = this.utilityFunctionsNadir.evaluate(temporalList.get(0), weigth);
                for (int solutionIdx = 1; solutionIdx < temporalList.size(); solutionIdx++) {
                    double value = this.utilityFunctionsNadir.evaluate(temporalList.get(solutionIdx), weigth);

                    if (value < minimumValue) {
                        minimumValue = value;
                        toRemoveIdx = solutionIdx;
                    }
                }

                S solutionToInsert = temporalList.remove(toRemoveIdx);
                setAttribute(solutionToInsert, idx);
                this.rankedSubpopulations.get(idx).add(solutionToInsert);
            }

        }
        return this;
    }

    @Override
    public List<S> getSubfront(int rank) {
        return this.rankedSubpopulations.get(rank);
    }

    @Override
    public int getNumberOfSubfronts() {
        return this.rankedSubpopulations.size();
    }
  
  /*public AbstractUtilityFunctionsSet<S> getUtilityFunctions() {
    return this.utilityFunctions;
  }*/


}
