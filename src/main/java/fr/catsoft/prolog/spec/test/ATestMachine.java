package fr.catsoft.prolog.spec.test;

import fr.catsoft.prolog.spec.interf.IProlog;
import fr.catsoft.prolog.spec.interf.IPrologHelper;
import fr.catsoft.prolog.spec.interf.IReponse;
import fr.catsoft.prolog.spec.interf.ITerme;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Commentaire
 *
 * @version $Revision$ $Date$
 */
public abstract class ATestMachine extends TestCase {

    private IPrologHelper helper = getHelper();


    protected abstract IProlog getImpl();

    protected abstract IPrologHelper getHelper();

    private int getSize(Iterable<?> iterable) {
        int cpt = 0;
        for (Object objet : iterable) {
            cpt++;
        }
        return cpt;
    }

    @Test
    public void testFaitSimple() {
        IProlog machine = getImpl();

        machine.ajouterFait(helper.creerTerme("parent(jean,charles)"));
        Assert.assertTrue(machine.questionner(helper.creerTerme("parent(jean,charles)")).isVrai());
        Assert.assertFalse(machine.questionner(helper.creerTerme("parent(jea,charles)")).isVrai());
    }

    @Test
    public void testFaitGenerique() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("parent(jean,X)"));
        Assert.assertTrue(machine.questionner(helper.creerTerme("parent(jean,Y)")).isVrai());
        Assert.assertFalse(machine.questionner(helper.creerTerme("parent(jea,Y)")).isVrai());
    }

    @Test
    public void testFaitDeduit() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("pere(jean,charles)"));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("parent(X,Y)"), helper.creerTerme("pere(X,Y)")));
        Assert.assertTrue(machine.questionner(helper.creerTerme("parent(jean,charles)")).isVrai());
        Assert.assertFalse(machine.questionner(helper.creerTerme("parent(jean,charle)")).isVrai());
        Assert.assertFalse(machine.questionner(helper.creerTerme("parent(jea,charles)")).isVrai());
    }

    @Test
    public void testQuestionGenerique() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("parent(jean,charles)"));
        machine.ajouterFait(helper.creerTerme("parent(jean,louis)"));
        IReponse reponse = machine.questionner(helper.creerTerme("parent(jean,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(getSize(reponse.getFaitsSimples()) == 2);
    }

    @Test
    public void testRegle() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("pere(jean,charles)"));
        machine.ajouterFait(helper.creerTerme("pere(jean,louis)"));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("parent(jean,charles)"), helper.creerTerme("pere(jean,charles)")));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("parent(jean,louis)"), helper.creerTerme("pere(jean,louis)")));
        IReponse reponse = machine.questionner(helper.creerTerme("parent(jean,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(reponse.getFaitsSimples() + "", getSize(reponse.getFaitsSimples()) == 2);
    }

    @Test
    public void testQuestionGeneriqueDeduite() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("pere(jean,charles)"));
        machine.ajouterFait(helper.creerTerme("pere(jean,louis)"));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("parent(X,Y)"), helper.creerTerme("pere(X,Y)")));
        IReponse reponse = machine.questionner(helper.creerTerme("parent(jean,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(reponse.getFaitsSimples() + "", getSize(reponse.getFaitsSimples()) == 2);
    }

    @Test
    public void testQuestionGeneriqueDeduiteComplexe() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("parent(jean,jules)"));
        machine.ajouterFait(helper.creerTerme("parent(jean,charles)"));
        machine.ajouterFait(helper.creerTerme("parent(jeanne,jules)"));
        machine.ajouterFait(helper.creerTerme("parent(jeanne,charles)"));
        machine.ajouterFait(helper.creerTerme("male(jean)"));
        machine.ajouterFait(helper.creerTerme("female(jeanne)"));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("pere(X,Y)"), helper.creerTerme("parent(X,Y)"), helper.creerTerme("male(X)")));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("mere(X,Y)"), helper.creerTerme("parent(X,Y)"), helper.creerTerme("female(X)")));
        IReponse reponse = machine.questionner(helper.creerTerme("pere(Y,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(reponse.getFaitsSimples() + "", getSize(reponse.getFaitsSimples()) == 2);

        reponse = machine.questionner(helper.creerTerme("pere(Y,Y)"));
        Assert.assertFalse(reponse.isVrai());

        reponse = machine.questionner(helper.creerTerme("mere(Y,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(reponse.getFaitsSimples() + "", getSize(reponse.getFaitsSimples()) == 2);
    }

    @Test
    public void testBoucle() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("carre(test)"));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("rond(X,Y)"), helper.creerTerme("rond(Y,X)")));

        IReponse reponse = machine.questionner(helper.creerTerme("rond(Y,X)"));
        Assert.assertTrue(!reponse.isVrai());
    }

    @Test
    public void testGenericiteFonction() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("sym(a,b)"));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("A(B,C)"), helper.creerTerme("A(C,B)")));

        IReponse reponse = machine.questionner(helper.creerTerme("sym(b,a)"));
        Assert.assertTrue(reponse.isVrai());
    }

    @Test
    public void testPerso() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("fonc(a,b)"));
        machine.ajouterFait(helper.creerTerme("fonc(a,c)"));
        // transitivite
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("F(A,C)"), helper.creerTerme("F(A,B)"), helper.creerTerme("F(B,C)")));
        // symetrie
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("F(A,C)"), helper.creerTerme("F(C,A)")));
        // reflexivite
        machine.ajouterFait(helper.creerTerme("F(A,A)"));
        IReponse reponse;

        reponse = machine.questionner(helper.creerTerme("fonc(b,c)"));
        Assert.assertTrue(reponse.isVrai());
        reponse = machine.questionner(helper.creerTerme("sym(a,a)"));
        Assert.assertTrue(reponse.isVrai());
    }

    @Test
    public void testFaitsMultiples() {
        IProlog prolog = getImpl();

        ITerme termeHomme = helper.creerTerme("homme(jean)");
        ITerme termeSportif = helper.creerTerme("sportif(jean)");

        prolog.ajouterFait(termeHomme);
        prolog.ajouterFait(termeSportif);
        IReponse reponse = prolog.questionner(Arrays.asList(helper.creerTerme("homme(X)"), helper.creerTerme("sportif(X)")));
        assertTrue(reponse.isVrai());
        assertTrue(getSize(reponse.getFaitsMultiples()) == 1);
        assertTrue(reponse.getFaitsMultiples().iterator().next().equals(Arrays.asList(termeHomme, termeSportif)));
    }

    @Test
    public void testInstructionNot() {
        IProlog machine = getImpl();
        machine.ajouterFait(helper.creerTerme("present(jean)"));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("absent(X)"), helper.creerTerme("!(present(X))")));
        machine.ajouterRegle(helper.creerRegle(helper.creerTerme("present(X)"), helper.creerTerme("!(absent(X))")));
        Assert.assertTrue(machine.questionner(helper.creerTerme("!(absent(jean))")).isVrai());
        Assert.assertTrue(machine.questionner(helper.creerTerme("absent(jeanne)")).isVrai());
    }
}
