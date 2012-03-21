package example3.organizations.auction.bidder;

import example3.players.bid.BidArgument;
import example3.players.bid.BidResult;
import example3.protocols.Protocols;
import example3.protocols.envelopeauction.AuctionCFPMessage;
import example3.protocols.envelopeauction.BidMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.InvokeResponsibilityState;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.ReceiveAcceptOrRejectProposal;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.IState;

/**
 * The 'Envelope auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Luk� K�dela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class EnvelopeAuction_ResponderParty extends ResponderParty<Bidder_Role> {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The aucitoneer. More precisely, its AID.
     */
    private AID auctioneer;
    
    /**
     * The name of the item.
     */
    private String itemName;
    
    /**
     * The flag indicating whether the bid has been made.
     */
    private boolean bidMade;
    
    /**
     * The bid.
     */
    private double bid;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Bidder_EnvelopeAuctionResponder class.
     * @param message the ACL message
     */
    public EnvelopeAuction_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.ENVELOPE_AUCTION_PROTOCOL), message);
        
        // TODO (priority: low) Consider moving this initialization to the 'MyInitialize' state.
        auctioneer = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the finite state machine, i. e. registers the states and transitions.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new MyInitialize();
        IState receiveAuctionCFP = new ReceiveAuctionCFP();
        IState invokeResponsibility_Bid = new InvokeResponsibility_Bid();
        IState sendBid = new SendBid();
        IState receiveAuctionResult = new ReceiveAuctionResult();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);       
        registerState(receiveAuctionCFP);
        registerState(invokeResponsibility_Bid);
        registerState(sendBid);
        registerState(receiveAuctionResult);     
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerTransition(Initialize.OK, receiveAuctionCFP);
        initialize.registerTransition(Initialize.FAIL, failureEnd);
        receiveAuctionCFP.registerDefaultTransition(invokeResponsibility_Bid);       
        invokeResponsibility_Bid.registerDefaultTransition(sendBid);        
        sendBid.registerDefaultTransition(receiveAuctionResult);        
        receiveAuctionResult.registerTransition(ReceiveAuctionResult.ACCEPT_PROPOSAL, successEnd);
        receiveAuctionResult.registerTransition(ReceiveAuctionResult.REJECT_PROPOSAL, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' state.
     * An (initial) state in which the party is initialized and begins.
     */
    private class MyInitialize extends Initialize {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int initialize() {
            getMyAgent().logInfo(String.format(
                "Responding to the 'Envelope auction' protocol (id = %1$s)",
                getProtocolId()));
            
            return OK;
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive auction CFP' (single receiver) state.
     * A state in which the auciton call for proposals (CFP) is received
     * from the auctioneer.
     */
    private class ReceiveAuctionCFP extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Receiving auction CFP.");
            AuctionCFPMessage message = new AuctionCFPMessage();
            message.parseACLMessage(getACLMessage());
            
            itemName = message.getItemName();
            getMyAgent().logInfo("Auction CFP received.");
        }
       
        // </editor-fold>
    }
    
    /**
     * The 'Invoke responsibility - Bid' state.
     * A state in which the 'Bid' responsibility is invoked.
     */
    private class InvokeResponsibility_Bid
        extends InvokeResponsibilityState<BidArgument, BidResult> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the InvokeResponsibility_Bid class.
         */
        InvokeResponsibility_Bid() {
            super("Bid_Responsibility");
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">

        /**
         * Gets the 'Bid' responsibility argument.
         * @return the 'Bid' responsibility argument
         */
        @Override
        protected BidArgument getResponsibilityArgument() {
            return BidArgument.createEnvelopeBidArgument(itemName);
        }

        /**
         * Sets the 'Bid' responsibility result.
         * @param responsibilityResult the 'Bid' responsibility result
         */
        @Override
        protected void setResponsibilityResult(BidResult responsibilityResult) {
            bidMade = responsibilityResult.isBidMade();
            if (bidMade) {
                bid = responsibilityResult.getBid();
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send bid' (single-sender) state.
     * A state in which the bid is sent to the auctioneer.
     */
    private class SendBid extends SingleSenderState<BidMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { auctioneer };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending bid.");
        }
        
        /**
         * Prepares the 'Bid' message.
         * @return the 'Bid' message
         */
        @Override
        protected BidMessage prepareMessage() {
            BidMessage message = new BidMessage();
            // TODO (priority: low) Also consider the situation when no bid is made.
            message.setBid(bid);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Bid sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive ACCEPT_PROPOSAL or REJECT_PROPOSAL' (multi-receiver) state.
     * A state in which the auction result (ACCEPT_PROPOSAL or REJECT_PROPOSAL) is received
     * from the auctioneer.
     */
    private class ReceiveAuctionResult extends ReceiveAcceptOrRejectProposal {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { auctioneer };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving auction result.");
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Auction result received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (one-shot) state.
     * A (final) state in which the party succeeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">     
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Envelope auction' responder party succeeded.");
        }
        
        // </editor-fold> 
    }
    
    /**
     * The 'Failure end' (one-shot) state.
     * A (final) state in which the party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">     
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Envelope auction' responder party failed.");
        }
        
        // </editor-fold> 
    }
    
    // </editor-fold>
}
