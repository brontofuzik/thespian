package example2.protocols.dutchauctionprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;

/**
 * The 'Dutch auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Luk� K�dela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class DutchAuctionProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static DutchAuctionProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private DutchAuctionProtocol() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static DutchAuctionProtocol getInstance() {
        if (singleton == null) {
            singleton = new DutchAuctionProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // </editor-fold>
}