package example2.protocols.evaluatebinaryoperation;

import example2.organizations.expressionevaluation.EvaluateBinaryOperation_ResponderParty;
import example2.organizations.expressionevaluation.evaluator.EvaluateBinaryOperation_InitiatorParty;
import example2.organizations.expressionevaluation.evaluator.Operation;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Evaluate binary operation' protocol. 
 * @author Luk� K�dela
 * @since 2012-03-24
 * @version %I% %G%
 */  
public class EvaluateBinaryOperationProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateBinaryOperationProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        Operation operation = (Operation)arguments[0];
        return new EvaluateBinaryOperation_InitiatorParty(operation);
    }

    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EvaluateBinaryOperation_ResponderParty(message);
    }
    
    // </editor-fold>
}
