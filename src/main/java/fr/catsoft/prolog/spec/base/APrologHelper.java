package fr.catsoft.prolog.spec.base;

import fr.catsoft.commons.common.exception.ApplicationException;
import fr.catsoft.commons.common.outil.OutilChaine;
import fr.catsoft.prolog.spec.interf.IPrologHelper;
import fr.catsoft.prolog.spec.interf.ITerme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 03/11/13
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
public abstract class APrologHelper implements IPrologHelper {

    protected abstract ITerme newTerme(String nom, List<ITerme> args);

    public ITerme creerTerme(String chaine) {
        int index = chaine.indexOf('(');
        if (index != -1) {
            if (!chaine.endsWith(")")) {
                throw new ApplicationException("La chaine " + chaine + " contient ( mais ne finit pas par )");
            }
            String nom = chaine.substring(0, index);
            String contenu = chaine.substring(index + 1, chaine.length() - 1);
            List<ITerme> args = new ArrayList<ITerme>();
            List<String> split = OutilChaine.splitSpecial(contenu, ',', '(', ')');
            for (String arg : split) {
                args.add(creerTerme(arg));
            }
            return newTerme(nom, args);
        } else {
            return newTerme(chaine, null);
        }
    }
}
