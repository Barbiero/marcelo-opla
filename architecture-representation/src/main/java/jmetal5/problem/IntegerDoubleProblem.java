package jmetal5.problem;

/**
 * Interface representing problems having integer and double variables
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface IntegerDoubleProblem<S> extends Problem<S> {
    Number getLowerBound(int index);

    Number getUpperBound(int index);

    int getNumberOfIntegerVariables();

    int getNumberOfDoubleVariables();
}
