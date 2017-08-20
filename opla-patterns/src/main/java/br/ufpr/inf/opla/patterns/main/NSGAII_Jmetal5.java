package br.ufpr.inf.opla.patterns.main;

import arquitetura.io.ReaderConfig;
import br.ufpr.inf.opla.patterns.factory.MutationOperatorFactory;
import br.ufpr.inf.opla.patterns.indicadores.Hypervolume;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.Mutation;
import jmetal.util.JMException;
import jmetal5.algorithm.multiobjective.nsgaii.NSGAII;
import jmetal5.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import jmetal5.operator.CrossoverOperator;
import jmetal5.operator.MutationOperator;
import jmetal5.problem.multiobjective.OPLAProblem;
import jmetal5.solution.impl.ArchitectureSolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Usando os pacotes do JMetal5, tenta imitar o funcionamento da classe NSGAII_OPLA
 */
public class NSGAII_Jmetal5 {

    private static int populationSize_;
    private static int maxEvaluations_;
    private static double mutationProbability_;
    private static double crossoverProbability_;


    static private List<ArchitectureSolution> removeDominadas(List<ArchitectureSolution> solutions) {
        for (int i = 0; i < solutions.size() - 1; i++) {
            ArchitectureSolution first = solutions.get(i);

            for (int j = i + 1; j < solutions.size(); j++) {
                ArchitectureSolution second = solutions.get(j);

                boolean dominador = true;
                boolean dominado = true;

                for (int obj = 0; obj < first.getNumberOfObjectives(); obj++) {
                    double valor1 = first.getObjective(obj);
                    double valor2 = second.getObjective(obj);

                    if (valor1 > valor2) {
                        dominador = false;
                    }

                    if (valor2 > valor1) {
                        dominado = false;
                    }
                }

                if (dominador) {
                    solutions.remove(j);
                    j = j - 1;
                } else if (dominado) {
                    solutions.remove(i);
                    j = i;
                }
            }
        }
        return solutions;
    }

    static private List<ArchitectureSolution> removeRepetidas(List<ArchitectureSolution> solutions) {
        return solutions.stream().distinct().collect(Collectors.toList());
    }


    public static void main(String... args) throws ClassNotFoundException, IOException, JMException {
        //versao com args próprios

        String[] myArgs = {
                /*population size*/"30",
                /*max evaluations*/"3000",
                /*Mutation probability*/"0.9",
                /*PLA path*/"/home/barbiero/TCC/PLAs/banking/banking.uml",
                /*Context*/"teste1",
                /*Mutation operator*/"PLAMutation",
                /*print variables?*/"true"
        };

        runNSGAII_OPLA(myArgs);
    }

    //--  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --
    private static void runNSGAII_OPLA(String[] args) throws IOException, JMException, ClassNotFoundException {

//        args = new String[]{"1", "1", "0.0", ArchitectureRepository.BET, "Teste", "PLAMutation", "false"};
        if (args.length < 7) {
            System.out.println("You need to inform the following parameters:");
            System.out.println("\t1 - Population Size (Integer);"
                    + "\n\t2 - Max Evaluations (Integer);"
                    + "\n\t3 - Mutation Probability (Double);"
                    + "\n\t4 - PLA path;"
                    + "\n\t5 - Context;"
                    + "\n\t6 - Mutation Operator class simple name;"
                    + "\n\t7 - If you want to write the variables (Boolean).");
            System.exit(0);
        }

        int runsNumber = 2; //30;
        if (args[0] == null || args[0].trim().equals("")) {
            System.out.println("Missing population size argument.");
            System.exit(1);
        }
        try {
            populationSize_ = Integer.valueOf(args[0]); //100;
        } catch (NumberFormatException ex) {
            System.out.println("Population size argument not integer.");
            System.exit(1);
        }
        if (args[1] == null || args[1].trim().equals("")) {
            System.out.println("Missing max evaluations argument.");
            System.exit(1);
        }
        try {
            maxEvaluations_ = Integer.valueOf(args[1]); //300 geraçõeshttp://loggr.net/
        } catch (NumberFormatException ex) {
            System.out.println("Max evaluations argument not integer.");
            System.exit(1);
        }
        crossoverProbability_ = 0.0;
        if (args[2] == null || args[2].trim().equals("")) {
            System.out.println("Missing mutation probability argument.");
            System.exit(1);
        }
        try {
            mutationProbability_ = Double.valueOf(args[2]);
        } catch (NumberFormatException ex) {
            System.out.println("Mutation probability argument not double.");
            System.exit(1);
        }

        HashMap parameters; // Operator parameters
        parameters = new HashMap();
        parameters.put("probability", mutationProbability_);

        if (args[3] == null || args[3].trim().equals("")) {
            System.out.println("Missing PLA Path argument.");
            System.exit(1);
        }
        String pla = args[3];

        if (args[4] == null || args[4].trim().equals("")) {
            System.out.println("Missing context argument.");
            System.exit(1);
        }
        String context = args[4];

        if (args[5] == null || args[5].trim().equals("")) {
            System.out.println("Missing mutation operator argument.");
            System.exit(1);
        }
        Mutation mutation = MutationOperatorFactory.create(args[5], parameters);

        if (args[6] == null || args[6].trim().equals("")) {
            System.out.println("Missing print variables argument.");
            System.exit(1);
        }
        boolean shouldPrintVariables = Boolean.valueOf(args[6]);

        String plaName = getPlaName(pla);

        Path rootDir = Paths.get("experiment", plaName, context);
        Path manipulationDir = rootDir.resolve("manipulation");
        Path outputDir = rootDir.resolve("output");

        Files.createDirectories(manipulationDir);
        Files.createDirectories(outputDir);

        ReaderConfig.setDirTarget(manipulationDir.toString() + "/");
        ReaderConfig.setDirExportTarget(outputDir.toString() + "/");

        String plaDirectory = Paths.get(pla).getParent().toString() + "/";

        ReaderConfig.setPathToTemplateModelsDirectory(plaDirectory);
        ReaderConfig.setPathToProfileSMarty(plaDirectory + "smarty.profile.uml");
        ReaderConfig.setPathToProfileConcerns(plaDirectory + "concerns.profile.uml");
        ReaderConfig.setPathProfileRelationship(plaDirectory + "relationships.profile.uml");
        ReaderConfig.setPathToProfilePatterns(plaDirectory + "patterns.profile.uml");

        //executar FeatureDriven antes de Conventional é cerca de 17% mais rápido
        String[] objectives = {"featureDriven", "conventional"};
        OPLAProblem oplaProblem = new OPLAProblem(pla, objectives);

        CrossoverOperator<ArchitectureSolution> plaOperator = CrossoverFactory.getPLACrossoverOperator(crossoverProbability_);
        MutationOperator<ArchitectureSolution> mutationOperator = MutationOperatorFactory.create(args[5], mutationProbability_);

        NSGAIIBuilder<ArchitectureSolution> nsgaiiBuilder = new NSGAIIBuilder<>(oplaProblem, plaOperator, mutationOperator)
                .setMaxEvaluations(maxEvaluations_).setPopulationSize(populationSize_);


        System.out.println("\n================ NSGAII ================");
        System.out.println("Context: " + context);
        System.out.println("PLA: " + pla);
        System.out.println("Params:");
        System.out.println("\tPop -> " + populationSize_);
        System.out.println("\tMaxEva -> " + maxEvaluations_);
        System.out.println("\tCross -> " + crossoverProbability_);
        System.out.println("\tMuta -> " + mutationProbability_);

        long heapSize = Runtime.getRuntime().totalMemory();
        heapSize = (heapSize / 1024) / 1024;
        System.out.println("Heap Size: " + heapSize + "Mb\n");

        Hypervolume.clearFile(rootDir.toString() + "/HYPERVOLUME.txt");

        List<List<ArchitectureSolution>> allSolutions = new ArrayList<>(runsNumber);

        long initTotal = System.currentTimeMillis();
        System.out.println("!!!RODANDO ALGORITMOS COM JMETAL5!!!");
        for (int run = 0; run < runsNumber; run++) {
            System.out.printf("Algoritmo %d ", run);

            NSGAII<ArchitectureSolution> nsgaii = nsgaiiBuilder.build();
            long initTime = System.currentTimeMillis();
            nsgaii.run();
            long estimatedTime = System.currentTimeMillis() - initTime;
            System.out.println(" executado em " + estimatedTime + "ms");


            List<ArchitectureSolution> result = nsgaii.getResult();

            System.out.printf("Resultado inicial tem %d soluções.%n", result.size());

            result = removeRepetidas(result);

            System.out.printf("Resultado final tem %d soluções.%n", result.size());
            System.out.println();

            allSolutions.add(run, result);
        }
        long endTotal = System.currentTimeMillis();

        List<ArchitectureSolution> finalList = allSolutions.stream().flatMap(List::stream).collect(Collectors.toList());
        System.out.printf("Solução final tem %d soluções. %n", finalList.size());
        finalList = finalList.stream().distinct().collect(Collectors.toList());
        System.out.printf("Solução finais tem %d soluções distintas. %n", finalList.size());

        System.out.printf("Tempo total %dms%n", (endTotal - initTotal));

    }

    private static String getPlaName(String pla) {
        int beginIndex = pla.lastIndexOf('/') + 1;
        int endIndex = pla.length() - 4;
        return pla.substring(beginIndex, endIndex);
    }
}
