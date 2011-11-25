package jadeorg.proto;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.core.organization.Organization;
import jadeorg.lang.Message;
import jadeorg.proto.State.Event;

/**
 * A protocol party.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class Party extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Party(String name) {
        setBehaviourName(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return getBehaviourName();
    }
    
    protected abstract Protocol getProtocol();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void registerState(State state) {
        registerState((Behaviour)state, state.getName());
    }  
    
    public void registerFirstState(State state) {
        registerFirstState((Behaviour)state, state.getName());
    }
    
    public void registerLastState(State state) {
        registerLastState((Behaviour)state, state.getName());
    }
    
    public void registerTransition(State fromState, State toState, Event event) {
        registerTransition(fromState.getName(), toState.getName(), event.getCode());
    }
    
    public void registerDefaultTransition(State fromState, State toState) {
        registerDefaultTransition(fromState.getName(), toState.getName());
    }
    
    /**
     * Sends a JadeOrg message.
     * @param messageClass the message class
     * @param message the message
     */
    public void send(Class messageClass, Message message) {
        // Generate the ACL message.
        ACLMessage aclMessage = getProtocol().generate(messageClass, message);
                
        // Send the JadeOrg message.
        myAgent.send(aclMessage);
    }
    
    /**
     * Receives a JadeOrg message.
     * @param messageClass the message class
     * @return the received message
     */
    public Message receive(Class messageClass, AID senderAID) {
        MessageTemplate messageTemplate = null;
        
        // Constrain the message class if specified.
        if (messageClass != null) {
            messageTemplate = getProtocol().getTemplate(messageClass);
        }
        
        // Constrain the sender if specified.
        if (senderAID != null) {
            MessageTemplate senderMessageTemplate = MessageTemplate.MatchSender(senderAID);
            if (messageTemplate != null) {
                messageTemplate = MessageTemplate.and(messageTemplate, senderMessageTemplate);
            } else {
                messageTemplate = senderMessageTemplate;
            }
        }
        
        // Receive the ACL message.
        ACLMessage aclMessage = myAgent.receive(messageTemplate);
        
        // Parse the ACL message.
        return getProtocol().parse(messageClass, aclMessage);
    }
    
    // </editor-fold>
}