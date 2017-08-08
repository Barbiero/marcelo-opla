package br.ufpr.inf.opla.patterns.models.ps;

import br.ufpr.inf.opla.patterns.designpatterns.DesignPattern;

public interface PSPLA extends PS {

    DesignPattern getPSPLAOf();

    boolean isPSPLAOf(DesignPattern designPattern);

}
