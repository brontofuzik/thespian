/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thespian4jade.protocols.roleprotocol.invokeresponsibilityprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Role_InvokeResponsibility_InitiatorParty;
import thespian4jade.core.player.Player_InvokeResponsibility_ResponderParty;
import thespian4jade.protocols.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.protocols.ResponderParty;
import java.io.Serializable;

/**
 * The 'Invoke responsibility' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2011-11-16
 * @version %I% %G%
 */
public class InvokeResponsibilityProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String responsibilityName = (String)arguments[0];
        Serializable argument = (Serializable)arguments[1];
        return new Role_InvokeResponsibility_InitiatorParty(responsibilityName, argument);
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Player_InvokeResponsibility_ResponderParty(message);
    }
    
    // </editor-fold>   
}