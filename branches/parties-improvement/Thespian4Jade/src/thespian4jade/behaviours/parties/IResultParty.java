package thespian4jade.behaviours.parties;

import thespian4jade.asynchrony.Future;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-20
 * @version %I% %G%
 */
public interface IResultParty<TResult> {
        
    public TResult getResult();
    
    public Future getResultFuture();
}