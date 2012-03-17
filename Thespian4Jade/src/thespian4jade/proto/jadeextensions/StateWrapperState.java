package thespian4jade.proto.jadeextensions;

/**
 * A 'State wrapper' state.
 * @author Lukáš Kúdela
 * @since 2012-03-15
 * @version %I% %G%
 */
public abstract class StateWrapperState<TState extends State>
    extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The wrapped state.
     */
    private TState wrappedState;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new insatance of the StateWrapperState class.
     * @param state 
     */
    public StateWrapperState(TState state) {
        this.wrappedState = state;
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sets the wrapped state argument.
     * @param wrappedState the wrapped state 
     */
    protected abstract void setWrappedStateArgument(TState wrappedState);
    
    /**
     * Gets the wrapped state result.
     * @param wrappedState the wrapped state result
     */
    protected abstract void getWrappedStateResult(TState wrappedState);
    
    // ---------- PRIVATE ----------
    
    /**
     * Builds the FSM.
     */
    private void buildFSM() {
        // ----- States -----
        State setStateArgument = new SetWrappedStateArgument();
        State getStateResult = new GetWrappedStateResult();
        // ------------------
        
        // Register the states.
        registerFirstState(setStateArgument);    
        registerState(wrappedState);     
        registerLastState(getStateResult);
        
        // Register the transitions.
        setStateArgument.registerDefaultTransition(wrappedState);     
        wrappedState.registerDefaultTransition(getStateResult);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Set wrapped state argument' state.
     */
    private class SetWrappedStateArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setWrappedStateArgument(wrappedState);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Get wrapped state result' state.
     */
    private class GetWrappedStateResult extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getWrappedStateResult(wrappedState);
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}