package thespian4jade.protocols;

import java.io.Serializable;
import thespian4jade.core.player.Player_InvokeCompetence_InitiatorParty;
import thespian4jade.protocols.jadeextensions.StateWrapperState;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */  
public abstract class InvokeCompetenceState
    <TArgument extends Serializable, TResult extends Serializable>
    extends StateWrapperState<Player_InvokeCompetence_InitiatorParty> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeCompetenceState class.
     * @param competenceName the name of the competence
     * @param competenceArgument the competence argument
     */
    public InvokeCompetenceState(String competenceName, TArgument competenceArgument) {
        super(new Player_InvokeCompetence_InitiatorParty(competenceName, competenceArgument));
    }
    
    /**
     * Initializes a new instance of the InvokeCompetenceState class.
     * @param competenceName the name of the competence
     */
    public InvokeCompetenceState(String competenceName) {
        super(new Player_InvokeCompetence_InitiatorParty(competenceName));
    }
    
    /**
     * Initializes a new instance of the InvokeCompetenceState class.
     */
    public InvokeCompetenceState() {
        super(new Player_InvokeCompetence_InitiatorParty());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the competence.
     * @return the name of the competence
     */
    protected String getCompetenceName() {
        return getWrappedState().getCometenceName();
    }
    
    /**
     * Gets the competence argument.
     * @return the competence argument
     */
    protected abstract TArgument getCompetenceArgument();
    
    /**
     * Sets the competence result.
     * @param competenceResult the competence result
     */
    protected abstract void setCompetenceResult(TResult competenceResult);
    
    // </editor-fold>   
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected final void setWrappedStateArgument(Player_InvokeCompetence_InitiatorParty wrappedState) {
        wrappedState.setCompetenceName(getCompetenceName());
        wrappedState.setCompetenceArgument(getCompetenceArgument());
    }
    
    @Override
    protected final void getWrappedStateResult(Player_InvokeCompetence_InitiatorParty wrappedState) {
        setCompetenceResult((TResult)wrappedState.getCompetenceResult());
    }
    
    // </editor-fold>
}