package fr.catsoft.prolog.spec.interf;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 05/10/13
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public interface IRegleManager {

    void ajouterFait(ITerme fait);

    void ajouterRegle(IRegle regle);

    List<IRegle> getReglesMatch(ITerme terme);
}
