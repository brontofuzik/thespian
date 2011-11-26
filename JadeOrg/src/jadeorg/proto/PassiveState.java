package jadeorg.proto;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;

/**
 * A passive protocol state.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class PassiveState extends State {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private boolean isDone;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PassiveState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public void setExitValue(Event event) {
        super.setExitValue(event);
        if (event == Event.SUCCESS || event == Event.FAILURE) {
            isDone = true;
        } else if (event == Event.LOOP) {
            isDone = false;
        }      
    }
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
   
    public boolean done() {
        return isDone;
    }
    
    /**
     * Receives a JadeOrg message.
     * @param messageClass the message class
     * @return the received message
     */
    public Message receive(Class messageClass, AID senderAID) {
        return getParty().receive(messageClass, senderAID);
    }
    
    // TAG DEBUGGING
    public ACLMessage receiveACLMessage() {
        return getParty().receiveACLMessage();
    }
    
    // </editor-fold>
}
