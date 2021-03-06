package jmetal5.qualityindicator;

import jmetal5.util.naming.DescribedEntity;

import java.io.Serializable;

/**
 * @param <Evaluate> Entity to runAlgorithm
 * @param <Result>   Result of the evaluation
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface QualityIndicator<Evaluate, Result> extends DescribedEntity, Serializable {
    Result evaluate(Evaluate evaluate);
}
