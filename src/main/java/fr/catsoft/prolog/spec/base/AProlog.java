package fr.catsoft.prolog.spec.base;

import fr.catsoft.commons.common.exception.ApplicationException;
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
}
