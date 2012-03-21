package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Protocols;
import thespian4jade.proto.ResponderParty;

/**
 * The 'Subscribe to event' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Organization_SubscribeToEvent_ResponderParty
    extends ResponderParty<Organization> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The subscribing player; more precisely its AID.
     * The initiator party of the protocol.
     */
    private AID player;
    
    /**
     * The event to subscribe to.
     */
    private String event;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Organization_SubscribeToEvent_ResponderParty class.
     * Creates a new 'Subscribe to event' protocol responder party.
     * @param message 
     */
    public Organization_SubscribeToEvent_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.SUBSCRIBE_TO_EVENT_PROTOCOL), message);
        
        buildFSM();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // </editor-fold>
}