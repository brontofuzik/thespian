package example3.organizations.expressionevaluation.evaluator;

import example3.protocols.EvaluateReplyMessage;
import example3.protocols.EvaluateRequestMessage;
import jade.core.AID;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.SingleReceiverState;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

/**
 * @author Luk� K�dela
 * @since 2012-03-14
 * @version %I% %G%
 */
public abstract class EvaluateBinaryOperation_InitiatorParty extends InitiatorParty<Evaluator_Role> {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID binaryEvaluatorAID;
    
    private String operand1;
    
    private String operand2;
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected EvaluateBinaryOperation_InitiatorParty(Protocol protocol) {
        super(protocol);
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    void setOperand1(String operand1) {
        this.operand1 = operand1;
    }
    
    void setOperand2(String operand2) {
        this.operand2 = operand2;
    }
    
    int getResult() {
        return result;
    }
    
    // ----- PROTECTED -----
    
    protected abstract String getBinaryEvaluatorRoleName();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    static EvaluateBinaryOperation_InitiatorParty createInitiatorParty(Operation operation) {
        switch (operation) {
            case ADDITION:
                return new EvaluateAddition_InitiatorParty();
            case SUBTRACTION:
                return new EvaluateSubtraction_InitiatorParty();
            case MULTIPLICATION:
                return new EvaluateMultiplication_InitiatorParty();
            case DIVISION:
                return new EvaluateDivision_InitiatorParty();
            default:
                throw new IllegalArgumentException();
        }
    }
    
    // ----- PRIVATE -----
    
    private void buildFSM() {
        // ----- States -----
        State initialize = new Initialize();
        State sendEvaluteRequest = new SendEvaluateRequest();
        State receiveEvaluateReply = new ReceiveEvaluateReply();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendEvaluteRequest);
        
        registerLastState(receiveEvaluateReply);
        
        // Register the transitions.
        initialize.registerDefaultTransition(sendEvaluteRequest);
        
        sendEvaluteRequest.registerDefaultTransition(receiveEvaluateReply);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Invoke function' protocol (id = %1$s)",
                getProtocolId()));
            
            binaryEvaluatorAID = getMyAgent().getMyOrganization()
                .getRoleInstance(getBinaryEvaluatorRoleName());
        }
        
        // </editor-fold>
    }
    
    private class SendEvaluateRequest extends SingleSenderState<EvaluateRequestMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { binaryEvaluatorAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending evaluate request.");
        }
        
        @Override
        protected EvaluateRequestMessage prepareMessage() {
            EvaluateRequestMessage message = new EvaluateRequestMessage();
            message.setOperand1(operand1);
            message.setOperand2(operand2);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Evaluate request received.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveEvaluateReply extends SingleReceiverState<EvaluateReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveEvaluateReply() {
            super(new EvaluateReplyMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { binaryEvaluatorAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving evaluate reply.");
        }
        
        @Override
        protected void handleMessage(EvaluateReplyMessage message) {
            result = message.getResult();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Evaluate reply received.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
