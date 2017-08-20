/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.opla.patterns.factory;

import br.ufpr.inf.opla.patterns.operator.impl.*;
import jmetal.operators.mutation.Mutation;
import jmetal5.operator.MutationOperator;
import jmetal5.operator.impl.mutation.NullMutation;
import jmetal5.solution.impl.ArchitectureSolution;

import java.util.HashMap;

/**
 * @author giovaniguizzo
 */
public class MutationOperatorFactory {

    public static Mutation create(String operator, HashMap<String, Object> parameters) {
        switch (operator) {
            case "DesignPatternsMutationOperator":
                return new DesignPatternMutationOperator(parameters, null, null);
            case "DesignPatternsAndPLAMutationOperator":
                return new DesignPatternsAndPLAMutationOperator(parameters, null, null);
            case "PLAMutation":
                return new PLAMutation(parameters);
            case "PLAMutationThenDesignPatternsMutationOperator":
                return new PLAMutationThenDesignPatternsMutationOperator(parameters, null, null);
            default:
                return null;
        }
    }

    public static MutationOperator<ArchitectureSolution> create(String operator, double probability) {
        if (probability == 0.0) {
            return new NullMutation<>();
        }

        switch (operator) {
            case "PLAMutation":
                return new PLAMutation_JMetal5(probability);
            default:
                return new NullMutation<>();
        }
    }

}
