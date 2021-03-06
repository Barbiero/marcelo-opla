package jmetal5.problem.impl;

import jmetal5.problem.Problem;

@SuppressWarnings("serial")
public abstract class AbstractGenericProblem<S> implements Problem<S> {
    private int numberOfVariables = 0;
    private int numberOfObjectives = 0;
    private int numberOfConstraints = 0;
    private String name = null;

    /* Getters */
    @Override
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    /* Setters */
    protected void setNumberOfVariables(int numberOfVariables) {
        this.numberOfVariables = numberOfVariables;
    }

    @Override
    public int getNumberOfObjectives() {
        return numberOfObjectives;
    }

    protected void setNumberOfObjectives(int numberOfObjectives) {
        this.numberOfObjectives = numberOfObjectives;
    }

    @Override
    public int getNumberOfConstraints() {
        return numberOfConstraints;
    }

    protected void setNumberOfConstraints(int numberOfConstraints) {
        this.numberOfConstraints = numberOfConstraints;
    }

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
