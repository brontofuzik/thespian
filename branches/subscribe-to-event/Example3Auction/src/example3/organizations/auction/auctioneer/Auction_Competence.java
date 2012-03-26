package example3.organizations.auction.auctioneer;

import example3.organizations.auction.auctioneer.auction.AuctionArgument;
import example3.organizations.auction.auctioneer.auction.AuctionResult;
import example3.organizations.auction.auctioneer.auction.AuctionType;
import thespian4jade.core.organization.competence.SynchronousCompetence;
import thespian4jade.behaviours.jadeextensions.OneShotBehaviourState;
import thespian4jade.behaviours.jadeextensions.IState;
import thespian4jade.behaviours.StateWrapperState;

/**
 * The 'Auction' (synchronous) competence.
 * @author Luk� K�dela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Auction_Competence extends SynchronousCompetence<AuctionArgument, AuctionResult> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The 'Initialize' state.
     */
    private IState initialize;
    
    /**
     * The 'End' state.
     */
    private IState end;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Auction_Competence class.
     */
    public Auction_Competence() {       
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the competence FSM.
     */
    private void buildFSM() {
        // ----- States -----
        initialize = new Initialize();
        end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize); 
        registerLastState(end);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' (one-shot) state.
     */
    private class Initialize extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            AuctionType auctionType = getArgument().getAuctionType();
            Auction_InitiatorParty auctionInitiator = Auction_InitiatorParty.createAuctionInitiator(auctionType);
            AuctionInitiatorWrapper auctionInitiatorWrapper = new AuctionInitiatorWrapper(auctionInitiator);
            
            // Register the auction initiator related states.
            registerState(auctionInitiatorWrapper);
            
            // Register the auction initiator related transitions.
            initialize.registerDefaultTransition(auctionInitiatorWrapper);
            auctionInitiatorWrapper.registerDefaultTransition(end);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Auction initiator party' (state wrapper) state.
     */
    private class AuctionInitiatorWrapper
        extends StateWrapperState<Auction_InitiatorParty> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initliazes a new instance of the AuctionInitiatorWrapper class.
         * @param auctionInitiator the auction initiator party
         */
        AuctionInitiatorWrapper(Auction_InitiatorParty auctionInitiator) {
            super(auctionInitiator);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void setWrappedStateArgument(Auction_InitiatorParty wrappedState) {
            wrappedState.setAuctionArgument(getArgument());
        }

        @Override
        protected void getWrappedStateResult(Auction_InitiatorParty wrappedState) {
            setResult(wrappedState.getAuctionResult());
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'End' (one-shot) state.
     */
    private static class End extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}