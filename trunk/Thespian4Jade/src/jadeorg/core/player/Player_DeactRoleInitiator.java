package jadeorg.core.player;

import jade.core.AID;
import jadeorg.lang.Message;
import jadeorg.lang.SimpleMessage;
import jadeorg.proto.Initialize;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.ReceiveAgreeOrRefuse;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A 'Deact role' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_DeactRoleInitiator extends InitiatorParty<Player> {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The organization name. */
    private String organizationName;
    
    /** The organization AID */
    private AID organizationAID;

    /** The role name */
    private String roleName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_DeactRoleInitiator(String organizationName, String roleName) {
        super(DeactRoleProtocol.getInstance());
        // ----- Preconditions -----
        assert organizationName != null && !organizationName.isEmpty();
        assert roleName != null && !roleName.isEmpty();
        // -------------------------

        this.organizationName = organizationName;
        this.roleName = roleName;

        buildFSM();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
        State sendDeactRequest = new SendDeactRequest();
        State receiveDeactReply = new ReceiveDeactReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendDeactRequest);
        registerState(receiveDeactReply);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendDeactRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);
        
        sendDeactRequest.registerDefaultTransition(receiveDeactReply);
            
        receiveDeactReply.registerTransition(ReceiveDeactReply.AGREE, successEnd);
        receiveDeactReply.registerTransition(ReceiveDeactReply.REFUSE, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Deact role' (%1$s.%2$s) protocol.",
                organizationName, roleName));

//            // TAG YellowPages
//            DFAgentDescription organization = YellowPages
//                .searchOrganizationWithRole(this, organizationName, roleName);

            organizationAID = new AID(organizationName, AID.ISLOCALNAME);
            if (organizationAID != null) {
                // The organizaiton exists.
                return OK;
            } else {
                // The organization does not exist.
                String message = String.format(
                    "Error deacting a role. The organization '%1$s' does not exist.",
                    organizationName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send deact request' (single sender) state.
     * A state in which the 'Deact request' message is sent.
     */
    private class SendDeactRequest extends SingleSenderState<DeactRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending deact request.");
        }
        
        @Override
        protected DeactRequestMessage prepareMessage() {
            DeactRequestMessage message = new DeactRequestMessage();
            message.setRoleName(roleName);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deact request sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive deact reply' (multi receiver) state.
     * A state in which the 'Deact reply' message is received.
     */
    private class ReceiveDeactReply extends ReceiveAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving deact reply.");
        }
        
        /**
         * Handles the received AGREE message
         * @param message the received AGREE message
         */
        @Override
        protected void handleAgreeMessage(SimpleMessage message) {
            getMyAgent().knowledgeBase.deactRole(roleName);
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deact reply received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Deact role' initiator party succeedes.
     */
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("Deact role initiator party succeeded.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Deact role' initiator party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("Deact role initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}