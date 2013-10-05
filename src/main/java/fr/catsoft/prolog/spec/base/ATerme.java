package fr.catsoft.prolog.spec.base;

import fr.catsoft.prolog.spec.interf.ITerme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 05/10/13
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */
public abstract class ATerme implements ITerme {

    private String nom;
    private List<ITerme> arguments;

    public ATerme(String nom) {
        this(nom, null);
    }

    public ATerme(String nom, List<ITerme> arguments) {
        setNom(nom);
        setArguments(arguments);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom == null || nom.length() == 0) {
            throw new RuntimeException("Le nom ne peut pas etre vide");
        }
        this.nom = nom;
    }

    public List<ITerme> getArguments() {
        return arguments;
    }

    protected void setArguments(List<ITerme> arguments) {
        if (arguments == null) {
            this.arguments = new ArrayList<ITerme>();
        } else {
            this.arguments = arguments;
        }
    }

    @Override
    public int getArite() {
        return getArguments().size();
    }

    public boolean isParametrableNom() {
        String lettre1 = getNom().substring(0, 1);
        return lettre1.toUpperCase().equals(lettre1);
    }

    public boolean isParametrable() {
        if (isParametrableNom()) {
            return true;
        }
        for (ITerme terme : getArguments()) {
            if (terme.isParametrable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ATerme aTerme = (ATerme) o;

        if (arguments != null ? !arguments.equals(aTerme.arguments) : aTerme.arguments != null) return false;
        if (!nom.equals(aTerme.nom)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nom.hashCode();
        result = 31 * result + (arguments != null ? arguments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (getArguments().isEmpty()) {
            return getNom();
        }
        String retour = getNom() + "/" + getArite() + "(";
        for (ITerme arg : getArguments()) {
            retour += arg.toString() + ",";
        }
        return retour + ")";
    }
}
