package jadeorg.proto.roleprotocol.meetrequirementprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.TextMessage;

/**
 * A 'Requirement request' message.
 * A 'Requirement request' message is a message sent from a role to a player
 * containing ... TODO Finish.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class RequirementRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String requirement;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RequirementRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getRequirement() {
        return requirement;
    }
    
    public RequirementRequestMessage setRequirement(String requirement) {
        this.requirement = requirement;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void parseContent(String content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
}