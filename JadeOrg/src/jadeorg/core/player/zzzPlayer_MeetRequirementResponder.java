package jadeorg.core.player;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.proto_old.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto_old.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto_old.State;
import jadeorg.util.MessageTemplateBuilder;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

/**
 * A 'Meet requirement responder' (FSM) behaviour.
 * @author Lukáš Kúdela
 * @since 2011-11-11
 * @version %I% %G%
 */
public class zzzPlayer_MeetRequirementResponder extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "meet-requirement-responder";
    
    // </editor-fold>
     
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<String, Requirement> requirements = new Hashtable<String, Requirement>();
    
    private Requirement currentRequirement;
    
    private State receiveParam;
    
    private State sendRequirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public zzzPlayer_MeetRequirementResponder() {
        super(NAME);
        registerStatesAndTransitions();
    }
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        State sendArgumentRequest = new SendArgumentRequest();
        receiveParam = new ReceiveParam();
        sendRequirementResult = new SendRequirementResult();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(sendArgumentRequest, sendArgumentRequest.getName());
        registerState(receiveParam, receiveParam.getName());
        registerLastState(sendRequirementResult, sendRequirementResult.getName());
        registerLastState(end, end.getName());
        
        // Register the transitions.
        registerDefaultTransition(sendArgumentRequest.getName(), receiveParam.getName());
        registerTransition(receiveParam.getName(), end.getName(), 1);
        registerDefaultTransition(sendRequirementResult.getName(), end.getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        // TODO Implement.
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected void addRequirement(Requirement requirement) {
        requirements.put(requirement.getName(), requirement);
        
        // Register the state.
        registerState(requirement, requirement.getName());
        
        // Register the transitions.
        registerTransition(receiveParam.getName(), requirement.getName(), requirement.hashCode());
        registerDefaultTransition(requirement.getName(), sendRequirementResult.getName());
    }
    
    protected void invokeRequirement(String requirementName) {
        if (containsRequirement(requirementName)) {
            currentRequirement = getRequirement(requirementName);
            reset();
        }
    }
    
    // ---------- PRIVATE ----------
    
    private boolean containsRequirement(String requirementName) {
        return requirements.containsKey(requirementName);
    }
    
    private Requirement getRequirement(String requirementName) {
        return requirements.get(requirementName);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    class SendArgumentRequest extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-argument-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendArgumentRequest() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // TODO Rework.
            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
            aclMessage.setProtocol("requirement-protcol");
            aclMessage.setContent("param");
            aclMessage.addReceiver(null);
            
            // Send the ACL message.
            myAgent.send(aclMessage);
        }
        
        // </editor-fold>
    }
    
    class ReceiveParam extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-param";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveParam() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MessageTemplate messageTemplate = MessageTemplateBuilder.createMessageTemplate(
                    "requirement-protocol",
                    new int[] { ACLMessage.INFORM, ACLMessage.FAILURE },
                    null);

            // Receive the ACL message.
            ACLMessage aclMessage = myAgent.receive(messageTemplate);
            if (aclMessage != null) {
                switch (aclMessage.getPerformative()) {
                    case ACLMessage.INFORM:
                        try {
                            currentRequirement.setArgument(aclMessage.getContentObject());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        // TODO Rework.
                        //setExitValue(currentRequirement.hashCode());
                        break;
                    case ACLMessage.FAILURE:
                        // TODO Rework.
                        //setExitValue(currentRequirement.hashCode());
                        break;
                }
            } else {
                block();
            }
        }
        
        // </editor-fold>
    }
    
    class SendRequirementResult extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-requirement-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequirementResult() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // TODO Rework.    
            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
            aclMessage.setProtocol("requirement-protocol");
            aclMessage.addReceiver(null);
            try {
                aclMessage.setContentObject((Serializable)currentRequirement.getResult());
            } catch (Exception ex) {
                aclMessage.setPerformative(ACLMessage.FAILURE);
                aclMessage.setContent(ex.toString());
            }
            
            // Send the ACL message.
            myAgent.send(aclMessage);

            currentRequirement.reset();
            getParent().reset();      
        }
        
        // </editor-fold>
    }
    
    class End extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "end";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        End() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
