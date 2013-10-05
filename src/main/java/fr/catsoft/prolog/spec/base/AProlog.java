package fr.catsoft.prolog.spec.base;

import fr.catsoft.commons.common.outil.OutilChaine;
import fr.catsoft.prolog.spec.interf.IProlog;
import fr.catsoft.prolog.spec.interf.IRegle;
import fr.catsoft.prolog.spec.interf.IRegleManager;
import fr.catsoft.prolog.spec.interf.ITerme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 05/10/13
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public abstract class AProlog implements IProlog {

    protected IRegleManager regleManager;

    public AProlog(IRegleManager regleManager) {
        this.regleManager = regleManager;
    }

    @Override
    public void setRegleManager(IRegleManager regleManager) {
        this.regleManager = regleManager;
    }

    @Override
    public void ajouterFait(ITerme fait) {
        regleManager.ajouterFait(fait);
    }

    @Override
    public void ajouterRegle(IRegle regle) {
        regleManager.ajouterRegle(regle);
    }

    protected abstract ITerme newTerme(String nom, List<ITerme> args);

    public ITerme creerTerme(String chaine) {
        int index = chaine.indexOf('(');
        if (index != -1) {
            if (!chaine.endsWith(")")) {
                throw new RuntimeException();
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
