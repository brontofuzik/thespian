package jadeorg.proto.roleprotocol.activateroleprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.Role_ActivateRoleResponder;
import jadeorg.core.player.Player_ActivateRoleInitiator;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;

/**
 * The 'Activate role' protocol.
 * Design pattern: Singleton, Role: Singleton
 * Design pattern: Abstract factory, Role: Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class ActivateRoleProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static ActivateRoleProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static ActivateRoleProtocol getInstance() {
        if (singleton == null) {
            singleton = new ActivateRoleProtocol();
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
        String roleName = (String)arguments[0];
        return new Player_ActivateRoleInitiator(roleName);
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Role_ActivateRoleResponder(message);
    }
    
    // </editor-fold>
}