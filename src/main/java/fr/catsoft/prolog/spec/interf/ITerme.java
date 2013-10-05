package fr.catsoft.prolog.spec.interf;

import fr.catsoft.commons.common.modele.interfaces.Clonable;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 05/10/13
 * Time: 11:08
 * To change this template use File | Settings | File Templates.
 */
public interface ITerme extends Clonable<ITerme> {

    String getNom();

    void setNom(String nom);

    boolean isParametrable();

    boolean isParametrableNom();

    int getArite();

    List<ITerme> getArguments();
}
