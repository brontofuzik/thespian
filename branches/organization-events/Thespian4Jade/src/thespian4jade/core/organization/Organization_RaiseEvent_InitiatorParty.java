package thespian4jade.core.organization;

import jade.core.AID;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;
import thespian4jade.proto.organizationprotocol.raiseeventprotocol.EventMessage;
import thespian4jade.proto.organizationprotocol.raiseeventprotocol.RaiseEventProtocol;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class Organization_RaiseEvent_InitiatorParty extends InitiatorParty<Organization> {
 
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The players listening for the event; more precisely their AIDs.
     */
    private AID[] players;
    
    private String event;
    
    private String argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Organization_RaiseEvent_InitiatorParty class.
     * @param protocol the 'Raise event' protocol.
     * @param event 
     */
    public Organization_RaiseEvent_InitiatorParty(String event, String argument) {
        super(RaiseEventProtocol.getInstance());
        // ----- Preconditions -----
        assert event != null && !event.isEmpty();
        // -------------------------
        
        this.event = event;
        this.argument = argument;
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        State initialize = new Initialize();
        State sendEvent = new SendEvent();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        registerState(sendEvent);
        registerLastState(end);
        
        // Register the transitions.
        initialize.registerDefaultTransition(sendEvent);
        sendEvent.registerDefaultTransition(end);
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
            // LOG
            getMyAgent().logInfo(String.format(
                "'Raise event' protocol (id = %1$s) initiator party started.",
                getProtocolId()));
            
            players = getMyOrganization().knowledgeBase.getAllPlayers()
                .toArray(new AID[0]);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send event' (single sender) state.
     * Sends the 'Event' message.
     */
    private class SendEvent extends SingleSenderState<EventMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return players;
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Sending event.");
        }
        
        @Override
        protected EventMessage prepareMessage() {
            EventMessage message = new EventMessage();
            message.setEvent(event);
            message.setArgument(argument);
            return message;
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Event sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'End' (one-shot) state.
     */
    private class End extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Raise event' protocol (id = %1$s) initiator party ended.",
                getProtocolId()));
        }
        
        // </editor-fold>  
    }
    
    // </editor-fold>
}