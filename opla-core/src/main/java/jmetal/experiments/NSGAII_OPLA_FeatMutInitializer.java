package jmetal.experiments;

import jmetal.util.JMException;

import java.io.IOException;

public class NSGAII_OPLA_FeatMutInitializer implements AlgorithmBase {

    private NSGAIIConfig config;

    public NSGAII_OPLA_FeatMutInitializer(NSGAIIConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        NSGAII_OPLA_FeatMut nsgaiiFeatMut = new NSGAII_OPLA_FeatMut();
        nsgaiiFeatMut.setConfigs(this.config);
        try {

            nsgaiiFeatMut.execute();
        } catch (ClassNotFoundException | IOException | JMException e) {
            e.printStackTrace();
        }
    }

}