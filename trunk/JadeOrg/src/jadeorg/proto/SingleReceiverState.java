package jadeorg.proto;

import jade.core.AID;

/**
 * A single receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class SingleReceiverState extends OuterReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    static final int SINGLE_RECEIVER = 0;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SingleReceiverState() {        
        addReceiver(new SingleReceiver());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID getSenderAID();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">   
    
    protected abstract int onSingleReceiver();   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SingleReceiver extends InnerReceiverState {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SingleReceiver() {
            super(RECEIVED);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return SingleReceiverState.this.getSenderAID();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setExitValue(onSingleReceiver());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}