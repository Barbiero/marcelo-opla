package jmetal5.util.experiment.component;

import jmetal5.problem.Problem;
import jmetal5.util.JMetalLogger;
import jmetal5.util.archive.impl.NonDominatedSolutionListArchive;
import jmetal5.util.experiment.Experiment;
import jmetal5.util.experiment.ExperimentComponent;
import jmetal5.util.experiment.util.ExperimentAlgorithm;
import jmetal5.util.experiment.util.ExperimentProblem;
import jmetal5.util.fileoutput.SolutionListOutput;
import jmetal5.util.front.Front;
import jmetal5.util.front.imp.ArrayFront;
import jmetal5.util.front.util.FrontUtils;
import jmetal5.util.point.util.PointSolution;
import jmetal5.util.solutionattribute.impl.GenericSolutionAttribute;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class computes a reference Pareto front from a set of files. Once the algorithms of an
 * experiment have been executed through running an instance of class {@link ExecuteAlgorithms},
 * all the obtained fronts of all the algorithms are gathered per problem; then, the dominated solutions
 * are removed and the final result is a file per problem containing the reference Pareto front.
 * <p>
 * By default, the files are stored in a directory called "referenceFront", which is located in the
 * experiment base directory. Each front is named following the scheme "problemName.rf".
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class GenerateReferenceParetoFront implements ExperimentComponent {
    private final Experiment<?, ?> experiment;

    public GenerateReferenceParetoFront(Experiment<?, ?> experimentConfiguration) {
        this.experiment = experimentConfiguration;

        experiment.removeDuplicatedAlgorithms();
    }

    /**
     * The run() method creates de output directory and compute the fronts
     */
    @Override
    public void run() throws IOException {
        String outputDirectoryName = experiment.getReferenceFrontDirectory();

        createOutputDirectory(outputDirectoryName);

        List<String> referenceFrontFileNames = new LinkedList<>();
        for (ExperimentProblem<?> problem : experiment.getProblemList()) {
            NonDominatedSolutionListArchive<PointSolution> nonDominatedSolutionArchive =
                    new NonDominatedSolutionListArchive<PointSolution>();

            for (ExperimentAlgorithm<?, ?> algorithm : experiment.getAlgorithmList()) {
                String problemDirectory = experiment.getExperimentBaseDirectory() + "/data/" +
                        algorithm.getAlgorithmTag() + "/" + problem.getTag();

                for (int i = 0; i < experiment.getIndependentRuns(); i++) {
                    String frontFileName = problemDirectory + "/" + experiment.getOutputParetoFrontFileName() +
                            i + ".tsv";
                    Front front = new ArrayFront(frontFileName);
                    List<PointSolution> solutionList = FrontUtils.convertFrontToSolutionList(front);
                    GenericSolutionAttribute<PointSolution, String> solutionAttribute = new GenericSolutionAttribute<PointSolution, String>();

                    for (PointSolution solution : solutionList) {
                        solutionAttribute.setAttribute(solution, algorithm.getAlgorithmTag());
                        nonDominatedSolutionArchive.add(solution);
                    }
                }
            }
            String referenceSetFileName = outputDirectoryName + "/" + problem.getTag() + ".rf";
            referenceFrontFileNames.add(problem.getTag() + ".rf");
            new SolutionListOutput(nonDominatedSolutionArchive.getSolutionList())
                    .printObjectivesToFile(referenceSetFileName);

            writeFilesWithTheSolutionsContributedByEachAlgorithm(outputDirectoryName, problem.getProblem(),
                    nonDominatedSolutionArchive.getSolutionList());
        }

        experiment.setReferenceFrontFileNames(referenceFrontFileNames);
    }

    private File createOutputDirectory(String outputDirectoryName) {
        File outputDirectory;
        outputDirectory = new File(outputDirectoryName);
        if (!outputDirectory.exists()) {
            boolean result = new File(outputDirectoryName).mkdir();
            JMetalLogger.logger.info("Creating " + outputDirectoryName + ". Status = " + result);
        }

        return outputDirectory;
    }

    private void writeFilesWithTheSolutionsContributedByEachAlgorithm(
            String outputDirectoryName, Problem<?> problem,
            List<PointSolution> nonDominatedSolutions) throws IOException {
        GenericSolutionAttribute<PointSolution, String> solutionAttribute = new GenericSolutionAttribute<PointSolution, String>();

        for (ExperimentAlgorithm<?, ?> algorithm : experiment.getAlgorithmList()) {
            List<PointSolution> solutionsPerAlgorithm = new ArrayList<>();
            for (PointSolution solution : nonDominatedSolutions) {
                if (algorithm.getAlgorithmTag().equals(solutionAttribute.getAttribute(solution))) {
                    solutionsPerAlgorithm.add(solution);
                }
            }

            new SolutionListOutput(solutionsPerAlgorithm)
                    .printObjectivesToFile(
                            outputDirectoryName + "/" + problem.getName() + "." +
                                    algorithm.getAlgorithmTag() + ".rf"
                    );
        }
    }
}
