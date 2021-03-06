package jmetal5.solution.impl;

import jmetal5.problem.Problem;
import jmetal5.solution.Solution;
import jmetal5.util.pseudorandom.JMetalRandom;

import java.util.*;

/**
 * Abstract class representing a generic solution
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public abstract class AbstractGenericSolution<T, P extends Problem<?>> implements Solution<T> {
    protected final JMetalRandom randomGenerator;
    protected P problem;
    protected Map<Object, Object> attributes;
    private double[] objectives;
    private List<T> variables;

    /**
     * Constructor
     */
    protected AbstractGenericSolution(P problem) {
        this.problem = problem;
        attributes = new HashMap<>();
        randomGenerator = JMetalRandom.getInstance();

        objectives = new double[problem.getNumberOfObjectives()];
        variables = new ArrayList<>(problem.getNumberOfVariables());
        for (int i = 0; i < problem.getNumberOfVariables(); i++) {
            variables.add(i, null);
        }
    }

    @Override
    public void setAttribute(Object id, Object value) {
        attributes.put(id, value);
    }

    @Override
    public Object getAttribute(Object id) {
        return attributes.get(id);
    }

    @Override
    public void setObjective(int index, double value) {
        objectives[index] = value;
    }

    @Override
    public double getObjective(int index) {
        return objectives[index];
    }

    @Override
    public T getVariableValue(int index) {
        return variables.get(index);
    }

    @Override
    public void setVariableValue(int index, T value) {
        variables.set(index, value);
    }

    @Override
    public int getNumberOfVariables() {
        return variables.size();
    }

    @Override
    public int getNumberOfObjectives() {
        return objectives.length;
    }

    protected void initializeObjectiveValues() {
        for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
            objectives[i] = 0.0;
        }
    }

    @Override
    public String toString() {
        String result = "Variables: ";
        for (T var : variables) {
            result += "" + var + " ";
        }
        result += "Objectives: ";
        for (Double obj : objectives) {
            result += "" + obj + " ";
        }
        result += "\t";
        result += "AlgorithmAttributes: " + attributes + "\n";

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AbstractGenericSolution<?, ?> that = (AbstractGenericSolution<?, ?>) o;

        if (!attributes.equals(that.attributes))
            return false;
        if (!Arrays.equals(objectives, that.objectives))
            return false;
        return variables.equals(that.variables);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(objectives);
        result = 31 * result + variables.hashCode();
        result = 31 * result + attributes.hashCode();
        return result;
    }
}
