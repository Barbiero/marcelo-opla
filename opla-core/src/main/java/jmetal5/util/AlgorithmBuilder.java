package jmetal5.util;

import jmetal5.algorithm.Algorithm;

/**
 * Interface representing algorithm builders
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface AlgorithmBuilder<A extends Algorithm<?>> {
    A build();
}
