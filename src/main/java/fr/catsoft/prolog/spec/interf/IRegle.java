package fr.catsoft.prolog.spec.interf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 05/10/13
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 */
public interface IRegle {

    List<ITerme> getConditions();

    ITerme getResutat();
}
