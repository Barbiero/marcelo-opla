package br.ufpr.inf.opla.patterns.strategies.scopeselection;

import arquitetura.representation.Architecture;
import arquitetura.representation.Patterns;
import br.ufpr.inf.opla.patterns.models.Scope;

public interface ScopeSelectionStrategy {

    Scope selectScope(Architecture architecture, Patterns designPattern);

}