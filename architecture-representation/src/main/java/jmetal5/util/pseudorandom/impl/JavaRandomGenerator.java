package jmetal5.util.pseudorandom.impl;

import jmetal5.util.pseudorandom.PseudoRandomGenerator;

import java.util.Random;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class JavaRandomGenerator implements PseudoRandomGenerator {
    private static final String name = "JavaRandomGenerator";
    private Random rnd;
    private long seed;

    /**
     * Constructor
     */
    public JavaRandomGenerator() {
        this(System.currentTimeMillis());
    }

    /**
     * Constructor
     */
    public JavaRandomGenerator(long seed) {
        this.seed = seed;
        rnd = new Random(seed);
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
        rnd.setSeed(seed);
    }

    @Override
    public int nextInt(int lowerBound, int upperBound) {
        return lowerBound + rnd.nextInt((upperBound - lowerBound + 1));
    }

    @Override
    public double nextDouble(double lowerBound, double upperBound) {
        return lowerBound + rnd.nextDouble() * (upperBound - lowerBound);
    }

    @Override
    public double nextDouble() {
        return nextDouble(0.0, 1.0);
    }

    @Override
    public String getName() {
        return name;
    }
}
