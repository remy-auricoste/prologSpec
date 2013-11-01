package fr.catsoft.prolog.spec.test;

import fr.catsoft.prolog.spec.interf.IProlog;
import fr.catsoft.prolog.spec.interf.IReponse;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Commentaire
 *
 * @version $Revision$ $Date$
 */
public abstract class ATestMachine extends TestCase {

    protected abstract IProlog getImpl();

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
        machine.ajouterFait(machine.creerTerme("parent(jean,charles)"));
        Assert.assertTrue(machine.questionner(machine.creerTerme("parent(jean,charles)")).isVrai());
        Assert.assertFalse(machine.questionner(machine.creerTerme("parent(jea,charles)")).isVrai());
    }

    @Test
    public void testFaitGenerique() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("parent(jean,X)"));
        Assert.assertTrue(machine.questionner(machine.creerTerme("parent(jean,Y)")).isVrai());
        Assert.assertFalse(machine.questionner(machine.creerTerme("parent(jea,Y)")).isVrai());
    }

    @Test
    public void testFaitDeduit() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("pere(jean,charles)"));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("parent(X,Y)"), machine.creerTerme("pere(X,Y)")));
        Assert.assertTrue(machine.questionner(machine.creerTerme("parent(jean,charles)")).isVrai());
        Assert.assertFalse(machine.questionner(machine.creerTerme("parent(jean,charle)")).isVrai());
        Assert.assertFalse(machine.questionner(machine.creerTerme("parent(jea,charles)")).isVrai());
    }

    @Test
    public void testQuestionGenerique() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("parent(jean,charles)"));
        machine.ajouterFait(machine.creerTerme("parent(jean,louis)"));
        IReponse reponse = machine.questionner(machine.creerTerme("parent(jean,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(getSize(reponse.getFaitsSimples()) == 2);
    }

    @Test
    public void testRegle() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("pere(jean,charles)"));
        machine.ajouterFait(machine.creerTerme("pere(jean,louis)"));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("parent(jean,charles)"), machine.creerTerme("pere(jean,charles)")));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("parent(jean,louis)"), machine.creerTerme("pere(jean,louis)")));
        IReponse reponse = machine.questionner(machine.creerTerme("parent(jean,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(reponse.getFaitsSimples() + "", getSize(reponse.getFaitsSimples()) == 2);
    }

    @Test
    public void testQuestionGeneriqueDeduite() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("pere(jean,charles)"));
        machine.ajouterFait(machine.creerTerme("pere(jean,louis)"));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("parent(X,Y)"), machine.creerTerme("pere(X,Y)")));
        IReponse reponse = machine.questionner(machine.creerTerme("parent(jean,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(reponse.getFaitsSimples() + "", getSize(reponse.getFaitsSimples()) == 2);
    }

    @Test
    public void testQuestionGeneriqueDeduiteComplexe() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("parent(jean,jules)"));
        machine.ajouterFait(machine.creerTerme("parent(jean,charles)"));
        machine.ajouterFait(machine.creerTerme("parent(jeanne,jules)"));
        machine.ajouterFait(machine.creerTerme("parent(jeanne,charles)"));
        machine.ajouterFait(machine.creerTerme("male(jean)"));
        machine.ajouterFait(machine.creerTerme("female(jeanne)"));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("pere(X,Y)"), machine.creerTerme("parent(X,Y)"), machine.creerTerme("male(X)")));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("mere(X,Y)"), machine.creerTerme("parent(X,Y)"), machine.creerTerme("female(X)")));
        IReponse reponse = machine.questionner(machine.creerTerme("pere(Y,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(reponse.getFaitsSimples() + "", getSize(reponse.getFaitsSimples()) == 2);

        reponse = machine.questionner(machine.creerTerme("pere(Y,Y)"));
        Assert.assertFalse(reponse.isVrai());

        reponse = machine.questionner(machine.creerTerme("mere(Y,X)"));
        Assert.assertTrue(reponse.isVrai());
        Assert.assertTrue(reponse.getFaitsSimples() + "", getSize(reponse.getFaitsSimples()) == 2);
    }

    @Test
    public void testBoucle() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("carre(test)"));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("rond(X,Y)"), machine.creerTerme("rond(Y,X)")));

        IReponse reponse = machine.questionner(machine.creerTerme("rond(Y,X)"));
        Assert.assertTrue(!reponse.isVrai());
    }

    @Test
    public void testGenericiteFonction() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("sym(a,b)"));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("A(B,C)"), machine.creerTerme("A(C,B)")));

        IReponse reponse = machine.questionner(machine.creerTerme("sym(b,a)"));
        Assert.assertTrue(reponse.isVrai());
    }

    @Test
    public void testPerso() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("fonc(a,b)"));
        machine.ajouterFait(machine.creerTerme("fonc(a,c)"));
        // transitivite
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("F(A,C)"), machine.creerTerme("F(A,B)"), machine.creerTerme("F(B,C)")));
        // symetrie
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("F(A,C)"), machine.creerTerme("F(C,A)")));
        // reflexivite
        machine.ajouterFait(machine.creerTerme("F(A,A)"));
        IReponse reponse;

        reponse = machine.questionner(machine.creerTerme("fonc(b,c)"));
        Assert.assertTrue(reponse.isVrai());
        reponse = machine.questionner(machine.creerTerme("sym(a,a)"));
        Assert.assertTrue(reponse.isVrai());
    }

    @Test
    public void testInstructionNot() {
        IProlog machine = getImpl();
        machine.ajouterFait(machine.creerTerme("present(jean)"));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("absent(X)"), machine.creerTerme("!(present(X))")));
        machine.ajouterRegle(machine.creerRegle(machine.creerTerme("present(X)"), machine.creerTerme("!(absent(X))")));
        Assert.assertTrue(machine.questionner(machine.creerTerme("!(absent(jean))")).isVrai());
        Assert.assertTrue(machine.questionner(machine.creerTerme("absent(jeanne)")).isVrai());
    }
}
