package thespian4jade.core.player.responsibility;

import thespian4jade.core.player.Player;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import java.io.Serializable;

/**
 * A one-shot responsibility.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public abstract class OneShotResponsibility<TArgument extends Serializable,
    TResult extends Serializable> extends OneShotBehaviourState
    implements Responsibility<TArgument, TResult> {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private TArgument argument;
    
    private TResult result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
    public void setArgument(TArgument argument) {
        this.argument = argument;
    }
    
    public TResult getResult() {
        return result;
    }
    
    // ----- PROTECTED -----
    
    protected TArgument getArgument() {
        return argument;
    }
    
    protected void setResult(TResult result) {
        this.result = result;
    }
    
    protected Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
}