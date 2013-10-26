package fr.catsoft.prolog.spec.interf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 05/10/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public interface IProlog {

    void setRegleManager(IRegleManager regleManager);

    IRegle creerRegle(ITerme resultat, ITerme... resultats);

    ITerme creerTerme(String chaine);

    void ajouterFait(ITerme fait);

    void ajouterRegle(IRegle regle);

    IReponse questionner(ITerme terme);

    IReponse questionner(List<ITerme> termes);
}
