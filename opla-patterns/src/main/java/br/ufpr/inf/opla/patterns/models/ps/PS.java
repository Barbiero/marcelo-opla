package br.ufpr.inf.opla.patterns.models.ps;

import arquitetura.representation.Element;
import br.ufpr.inf.opla.patterns.designpatterns.DesignPattern;

import java.util.List;

public interface PS {

    DesignPattern getPSOf();

    boolean isPSOf(DesignPattern designPattern);

    List<Element> getParticipants();

}
