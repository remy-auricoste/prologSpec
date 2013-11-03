package fr.catsoft.prolog.spec.interf;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 03/11/13
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
public interface IPrologHelper {

    IRegle creerRegle(ITerme resultat, ITerme... resultats);

    ITerme creerTerme(String chaine);
}
