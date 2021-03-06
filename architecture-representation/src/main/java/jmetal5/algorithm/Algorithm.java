package jmetal5.algorithm;

import jmetal5.util.naming.DescribedEntity;

import java.io.Serializable;

/**
 * Interface representing an algorithm
 *
 * @param <Result> Result
 * @author Antonio J. Nebro
 * @version 0.1
 */
public interface Algorithm<Result> extends Runnable, Serializable, DescribedEntity {
    void run();

    Result getResult();
}
