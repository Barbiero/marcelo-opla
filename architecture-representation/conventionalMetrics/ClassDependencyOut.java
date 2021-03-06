package br.uem.din.metrics.conventionalMetrics;

import br.uem.din.architectureEvolution.representation.*;

import java.util.ArrayList;
import java.util.List;

public class ClassDependencyOut {

    /**
     * @param args
     */

    private Architecture architecture;
    private int results;

    public ClassDependencyOut(Architecture architecture) {

        this.architecture = architecture;
        this.results = 0;
        int depOut = 0;


        for (Component component : architecture.getComponents()) {

            for (Class cls : component.getClasses()) {
                depOut += searchClassDependencies(cls, component);
                //System.out.println("DepOut- Classe: "+ cls.getName() + " :" + depOut);
            }

            this.results += depOut; // somatorio de DepOut da arquitetura como um todo
            depOut = 0;
        }

    }

//----------------------------------------------------------------------------------

    private int searchClassDependencies(Class source, Component comp) {
        List<Class> depClasses = new ArrayList<Class>();

        for (Class c : comp.getClasses()) {
            List<InterClassRelationship> relationships = new ArrayList<InterClassRelationship>(source.getRelationships());
            if (relationships != null) {
                for (InterClassRelationship relationship : relationships) {

                    if (relationship instanceof DependencyInterClassRelationship) {
                        DependencyInterClassRelationship dependency = (DependencyInterClassRelationship) relationship;
                        if (dependency.getClient().equals(source.getName()) && (!(depClasses.contains(c)))) {
                            depClasses.add(c);
                        }
                    }
                }
            }
        }//end for classes

        return depClasses.size();
    }

    // ---------------------------------------------------------------------------------

    public int getResults() {
        return results;
    }

}
