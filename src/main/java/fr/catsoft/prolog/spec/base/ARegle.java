package fr.catsoft.prolog.spec.base;

import fr.catsoft.commons.common.exception.ApplicationException;
import fr.catsoft.commons.common.logger.Logger;
import fr.catsoft.prolog.spec.interf.IRegle;
import fr.catsoft.prolog.spec.interf.ITerme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 05/10/13
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public abstract class ARegle implements IRegle {

    private ITerme resutat;
    private List<ITerme> conditions;

    public ARegle(List<ITerme> conditions, ITerme resutat) {
        this.conditions = conditions;
        this.resutat = resutat;
        if (conditions == null) {
            this.conditions = new ArrayList<ITerme>();
        }
    }

    public List<ITerme> getConditions() {
        return conditions;
    }

    public ITerme getResutat() {
        return resutat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ARegle aRegle = (ARegle) o;

        if (conditions != null ? !conditions.equals(aRegle.conditions) : aRegle.conditions != null) return false;
        if (!resutat.equals(aRegle.resutat)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resutat.hashCode();
        result = 31 * result + (conditions != null ? conditions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String retour = getResutat() + " :- ";
        for (ITerme terme : getConditions()) {
            retour += terme;
        }
        return retour;
    }

    @Override
    public ARegle clone() {
        try {
            return (ARegle) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ApplicationException(e);
        }
    }
}
