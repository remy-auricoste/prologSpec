package fr.catsoft.prolog.spec.interf;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: remy
 * Date: 05/10/13
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public interface IReponse {
    boolean isVrai();

    Iterable<ITerme> getFaitsSimples();

    Iterable<List<ITerme>> getFaitsMultiples();
}
