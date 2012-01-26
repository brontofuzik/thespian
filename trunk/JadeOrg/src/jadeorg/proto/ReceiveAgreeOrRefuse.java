package jadeorg.proto;

import jade.core.AID;
import jadeorg.lang.SimpleMessage;

/**
 * A 'Receive AGREE or REFUSE' (multi-receiver) state.
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public abstract class ReceiveAgreeOrRefuse extends OuterReceiverState {
 
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int AGREE = 1;
    public static final int REFUSE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ReceiveAgreeOrRefuse class.
     */
    protected ReceiveAgreeOrRefuse() {
        addReceiver(this.new MyReceiveAgree());
        addReceiver(this.new MyReceiveRefuse());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the senders; more precisely, their AIDs.
     * @return the senders; more precisely, their AIDs.
     */
    protected abstract AID[] getSenders();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Handles the received AGREE message.
     * @param message the received AGREE message
     */
    protected void handleAgreeMessage(SimpleMessage message) {
        // Do nothing.
    }
    
    /**
     * Handles the received REFUSE message.
     * @param message the received REFUSE message
     */
    protected void handleRefuseMessage(SimpleMessage message) {
        // Do nothing.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * My 'Receive AGREE' state. 
     */
    private class MyReceiveAgree extends OuterReceiverState.ReceiveAgree {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the MyReceiveAgree class.
         */
        MyReceiveAgree() {
            super(AGREE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the senders; more precisely, their AIDs.
         * @return the senders; more precisely, their AIDs.
         */
        @Override
        protected AID[] getSenders() {
            return ReceiveAgreeOrRefuse.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the received message.
         * @param message the received message
         */
        @Override
        protected void handleMessage(SimpleMessage message) {
            handleAgreeMessage(message);
        }

        // </editor-fold>    
    }
    
    /**
     * My 'Receive REFUSE' state.
     */
    private class MyReceiveRefuse extends OuterReceiverState.ReceiveRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the MyReceiveRefuse class.
         */
        MyReceiveRefuse() {
            super(REFUSE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Handles the received message
         * @param message the received message
         */
        @Override
        protected AID[] getSenders() {
            return ReceiveAgreeOrRefuse.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the received message.
         * @param message the received message
         */
        @Override
        protected void handleMessage(SimpleMessage message) {
            handleRefuseMessage(message);
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
