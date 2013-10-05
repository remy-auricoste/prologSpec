package fr.catsoft.prolog.spec.base;

import fr.catsoft.prolog.spec.interf.IReponse;
import fr.catsoft.prolog.spec.interf.ITerme;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 05/10/13
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class AReponse implements IReponse {

    private Set<ITerme> faits;

    public AReponse() {
        faits = new HashSet<ITerme>();
    }

    public boolean isVrai() {
        return !getFaits().isEmpty();
    }

    public Set<ITerme> getFaits() {
        return faits;
    }
}
