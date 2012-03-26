package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.behaviours.ExitValueState;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.receiverstate.ReceiveSuccessOrFailure;
import thespian4jade.protocols.organization.enactrole.EnactRequestMessage;
import thespian4jade.protocols.organization.enactrole.ResponsibilitiesInformMessage;
import thespian4jade.protocols.organization.enactrole.RoleAIDMessage;
import thespian4jade.behaviours.jadeextensions.IState;
import thespian4jade.behaviours.receiverstate.SingleReceiverState;
import thespian4jade.behaviours.senderstates.SingleSenderState;
import thespian4jade.behaviours.senderstates.SendAgreeOrRefuse;
import thespian4jade.behaviours.jadeextensions.OneShotBehaviourState;

/**
 * An 'Enact role' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public class Player_EnactRole_InitiatorParty extends InitiatorParty<Player> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The organization in which to enact the role; more precisely its AID.
     * The responder party.
     */
    private AID organization;
    
    /**
     * The name of the organization in which to enact the role.
     */
    private String organizationName;
    
    /**
     * The name of the role to enact.
     */
    private String roleName;
    
    /**
     * The responsibilitties of the role.
     */
    private String[] responsibilities;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Player_EnactRole_InitiatorParty(String organizationName, String roleName) {
        super(ProtocolRegistry.getProtocol(Protocols.ENACT_ROLE_PROTOCOL));
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
        IState initialize = new Initialize();
        IState sendEnactRequest = new SendEnactRequest();
        IState receiveResponsibilitiesInform = new ReceiveResponsibilitiesInform();
        IState sendResponsibilitiesReply = new SendResponsibilitiesReply();
        IState receiveRoleAID = new ReceiveRoleAID();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(initialize);     
        registerState(sendEnactRequest);
        registerState(receiveResponsibilitiesInform);
        registerState(sendResponsibilitiesReply);
        registerState(receiveRoleAID);       
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendEnactRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);       
        sendEnactRequest.registerDefaultTransition(receiveResponsibilitiesInform);
        receiveResponsibilitiesInform.registerTransition(ReceiveResponsibilitiesInform.SUCCESS, sendResponsibilitiesReply);
        receiveResponsibilitiesInform.registerTransition(ReceiveResponsibilitiesInform.FAILURE, failureEnd);      
        sendResponsibilitiesReply.registerTransition(SendResponsibilitiesReply.AGREE, receiveRoleAID);
        sendResponsibilitiesReply.registerTransition(SendResponsibilitiesReply.REFUSE, failureEnd);
        receiveRoleAID.registerDefaultTransition(successEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'My initialize' (initialize) state.
     */
    private class Initialize extends ExitValueState {
    
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int OK = 1;
        public static final int FAIL = 2;
        // -----------------------
        
        // </editor-fold>    
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int doAction() {
            getMyAgent().logInfo(String.format(
                "'Enact role' protocol (id = %1$s) initiator party started.",
                getProtocolId()));
            
//            // TAG YELLOW-PAGES
//            DFAgentDescription organization = YellowPages
//                .searchOrganizationWithRole(this, organizationName, roleName);
            
            // Check if the organization exists.
            organization = new AID(organizationName, AID.ISLOCALNAME);
            if (organization != null) {
                // The organization exists.
                return OK;
            } else {
                // The organization does not exist.
                String message = String.format(
                    "Error enacting a role. The organization '%1$s' does not exist.",
                    organizationName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send enact request' active state.
     * A state in which the 'Enact' request is sent.
     */
    private class SendEnactRequest extends SingleSenderState<EnactRequestMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organization };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Sending enact request.");
        }
        
        @Override
        protected EnactRequestMessage prepareMessage() {
            // Create the 'Enact request' message.
            EnactRequestMessage message = new EnactRequestMessage();
            message.setRoleName(roleName);
            return message;
        }
        
        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Enact request sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive responsibilities info' (multi receiver) state.
     * A state in which the 'Responsibilities' info is received.
     */
    private class ReceiveResponsibilitiesInform extends
        ReceiveSuccessOrFailure<ResponsibilitiesInformMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveResponsibilitiesInform() {
            super(new ResponsibilitiesInformMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organization };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving responsibilities info.");
        }

        @Override
        protected void handleSuccessMessage(ResponsibilitiesInformMessage message) {
            responsibilities = message.getResponsibilities();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibilities info received.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Send responsibilities reply' (multi sender) state.
     * A state in which the 'Agree' or 'Refuse' message is sent.
     */
    private class SendResponsibilitiesReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organization };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending responsibilities reply.");
        }
        
        @Override
        protected int onManager() {
            if (getMyAgent().evaluateResponsibilities(responsibilities)) {
                // The player meets the responsibilities.
                return AGREE;
            } else {
                // The player does not meet the responsibilities.
                return REFUSE;
            }
        }
        
        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibilities reply sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive role AID' passive state.
     * A state in which the 'Role AID' message is received.
     */
    private class ReceiveRoleAID extends SingleReceiverState<RoleAIDMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveRoleAID() {
            super(new RoleAIDMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organization };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Receiving role AID.");
        }
        
        @Override
        protected void handleMessage(RoleAIDMessage message) {
            AID roleAID = message.getRoleAID();
            getMyAgent().knowledgeBase.update().enactRole(roleName, roleAID,
                organization.getLocalName(), organization);
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Role AID received.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'SuccessEnd successEnd' state.
     * A state in which the 'Enact' protocol initiator party ends.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Enact role initiator party succeeded.");
        }

        // </editor-fold>
    }

    /**
     * The 'Fail successEnd' state.
     * A state in which the 'Enact' protocol initiator party ends.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Enact role initiator party failed.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
