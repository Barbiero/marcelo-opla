package jmetal5.operator.impl.selection;

import jmetal5.solution.Solution;
import jmetal5.util.comparator.DominanceComparator;

import java.util.Comparator;

/**
 * Applies a binary tournament selection to return the best solution between two that have been
 * chosen at random from a solution list.
 * Modified by Juanjo in 13.03.2015. A binary tournament is now a TournamenteSelection with 2
 * tournaments
 *
 * @author Antonio J. Nebro, Juan J. Durillo
 */
@SuppressWarnings("serial")
public class BinaryTournamentSelection<S extends Solution<?>> extends TournamentSelection<S> {
    /**
     * Constructor
     */
    public BinaryTournamentSelection() {
        super(new DominanceComparator<S>(), 2);
    }

    /**
     * Constructor
     */
    public BinaryTournamentSelection(Comparator<S> comparator) {
        super(comparator, 2);
    }
}
