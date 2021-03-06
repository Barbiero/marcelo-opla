package jmetal5.util.pseudorandom;

import jmetal5.util.pseudorandom.impl.JavaRandomGenerator;

import java.io.Serializable;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class JMetalRandom implements Serializable {
    private static JMetalRandom instance;
    private PseudoRandomGenerator randomGenerator;

    private JMetalRandom() {
        randomGenerator = new JavaRandomGenerator();
    }

    public static JMetalRandom getInstance() {
        if (instance == null) {
            instance = new JMetalRandom();
        }
        return instance;
    }

    public PseudoRandomGenerator getRandomGenerator() {
        return randomGenerator;
    }

    public void setRandomGenerator(PseudoRandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public int nextInt(int lowerBound, int upperBound) {
        return randomGenerator.nextInt(lowerBound, upperBound);
    }

    public double nextDouble() {
        return randomGenerator.nextDouble();
    }

    public double nextDouble(double lowerBound, double upperBound) {
        return randomGenerator.nextDouble(lowerBound, upperBound);
    }

    public long getSeed() {
        return randomGenerator.getSeed();
    }

    public void setSeed(long seed) {
        randomGenerator.setSeed(seed);
    }

    public String getGeneratorName() {
        return randomGenerator.getName();
    }
}
