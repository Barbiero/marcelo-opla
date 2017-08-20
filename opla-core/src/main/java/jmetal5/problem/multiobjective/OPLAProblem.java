package jmetal5.problem.multiobjective;

import arquitetura.builders.ArchitectureBuilder;
import arquitetura.representation.Architecture;
import jmetal.experiments.ExperimentCommomConfigs;
import jmetal.metrics.MetricsEvaluation;
import jmetal5.problem.impl.AbstractGenericProblem;
import jmetal5.solution.impl.ArchitectureSolution;

import java.util.List;

/**
 * Implementação do problema de otimizaçao de PLA no jmetal5
 *
 * @see jmetal.problems.OPLA
 */
public class OPLAProblem extends AbstractGenericProblem<ArchitectureSolution> {

    public static int discardedSolutions = 0;
    int numberOfProblems;
    /**
     * Construir o problema a partir das entradas da GUI
     */
    private Architecture architecture_;
    private ExperimentCommomConfigs configs;


    public OPLAProblem(String xmiFilePath, ExperimentCommomConfigs oplaConfig) {
        setNumberOfObjectives(oplaConfig.getOplaConfigs().getNumberOfObjectives());
        setNumberOfConstraints(0);
        setNumberOfVariables(1);
        setName("OPLA");

        this.configs = oplaConfig;
        this.architecture_ = new ArchitectureBuilder().create(xmiFilePath);

    }

    public Architecture getArchitecture() {
        return architecture_;
    }

    @Override
    public void evaluate(ArchitectureSolution solution) {

        List<String> selectedMetrics = configs.getOplaConfigs().getSelectedObjectiveFunctions();
        for (int i = 0, selectedMetricsSize = selectedMetrics.size(); i < selectedMetricsSize; i++) {
            String metric = selectedMetrics.get(i);

            MetricsEvaluation evaluation = new MetricsEvaluation();
            Architecture arch = solution.getArchitecture();

            double result;
            switch (metric) {
                case "elegance":
                    result = evaluation.evaluateElegance(arch);
                    break;
                case "conventional":
                    result = evaluation.evaluateMACFitness(arch);
                    break;
                case "featureDriven":
                    result = evaluation.evaluateMSIFitness(arch);
                    break;
                case "PLAExtensibility":
                    result = evaluation.evaluatePLAExtensibility(arch);
                    break;
                //implementado por marcelo
                case "acomp":
                    result = evaluation.evaluateACOMP(arch);
                    break;
                case "aclass":
                    result = evaluation.evaluateACLASS(arch);
                    break;
                case "tam":
                    result = evaluation.evaluateTAM(arch);
                    break;
                case "coe":
                    result = evaluation.evaluateCOE(arch);
                    break;
                case "dc":
                    result = evaluation.evaluateDC(arch);
                    break;
                case "ec":
                    result = evaluation.evaluateEC(arch);
                    break;
                default:
                    result = 0.0;
                    break;
            }

            solution.setObjective(i, result);
        }
    }

    @Override
    public ArchitectureSolution createSolution() {
        return null;
    }
}
