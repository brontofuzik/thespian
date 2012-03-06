package jadeorg.core.organization.power;

import jadeorg.core.organization.Role;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import java.io.Serializable;

/**
 * A one-shot power.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public abstract class OneShotPower<TArgument extends Serializable, TResult extends Serializable>
    extends OneShotBehaviourState implements Power<TArgument, TResult> {
    
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
    
    public void setResult(TResult result) {
        this.result = result;
    }
    
    protected Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold> 
}