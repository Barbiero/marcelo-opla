package jmetal5.solution.impl;

import arquitetura.representation.Architecture;
import jmetal5.problem.multiobjective.OPLAProblem;

/**
 * Implementação no jmetal5 da ArchitectureSolutionType
 *
 * @see jmetal.encodings.solutionType.ArchitectureSolutionType
 */
public class ArchitectureSolution extends AbstractGenericSolution<Architecture, OPLAProblem> {


    private Architecture architecture_;

    public ArchitectureSolution(OPLAProblem problem) {
        this(problem, problem.getArchitecture());
    }

    public ArchitectureSolution(OPLAProblem problem, Architecture copyArch) {
        super(problem);
        this.architecture_ = copyArch.deepClone();
        this.setVariableValue(0, this.architecture_);
    }

    public Architecture getArchitecture() {
        return architecture_;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArchitectureSolution)) {
            return false;
        }

        ArchitectureSolution other = (ArchitectureSolution) o;
        return this.getArchitecture().toString().equals(other.getArchitecture().toString());
    }

    @Override
    public String getVariableValueString(int index) {
        return getVariableValue(index).toString();
    }

    @Override
    public ArchitectureSolution copy() {
        return new ArchitectureSolution(this.problem, this.architecture_);
    }

}
