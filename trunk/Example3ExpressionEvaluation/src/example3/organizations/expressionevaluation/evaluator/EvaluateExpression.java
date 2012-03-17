package example3.organizations.expressionevaluation.evaluator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.proto.jadeextensions.FSMBehaviourState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

/**
 * The 'Evaluate expression' (FSM) behaviour.
 * @author Luk� K�dela
 * @since 2012-04-15
 * @version %I% %G%
 */
public class EvaluateExpression extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    // ----- States -----
    private State setInitiatorArgument;
    private EvaluateBinaryOperation_InitiatorParty evaluateBinaryOperationInitiator;
    private State getInitiatorResult;
    // ------------------
    
    private String expression;
    
    private String operand1;
    
    private String operand2;
    
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateExpression class.
     */
    public EvaluateExpression() {        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    void setExpression(String expression) {
        this.expression = expression;
    }
    
    int getValue() {
        return value;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----     
        State process = new Process();
        setInitiatorArgument = new SetInitiatorArgument();
        getInitiatorResult = new GetInitiatorResult();
        State end = new End();
        // ------------------
        
        // Register the states.        
        registerFirstState(process);      
        registerState(setInitiatorArgument);
        registerState(getInitiatorResult);     
        registerLastState(end);
        
        // Register the transitions.        
        process.registerTransition(Process.NUMBER, end);
        process.registerTransition(Process.BINARY_OPERATION, setInitiatorArgument);      
        getInitiatorResult.registerDefaultTransition(end);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Process' (one-shot) state.
     */
    private class Process extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        // ----- Exit values -----
        static final int NUMBER = 1;
        static final int UNARY_OPERATION = 2;
        static final int BINARY_OPERATION = 3;
        // -----------------------
        
        private int exitValue;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            ExpressionParser parser = new ExpressionParser();
            parser.parse(expression);
            if (parser.getOperation() != Operation.NONE) {
                // Binary operation
                operand1 = parser.getOperand1();
                operand2 = parser.getOperand2();
                evaluateBinaryOperationInitiator = EvaluateBinaryOperation_InitiatorParty
                    .createInitiatorParty(parser.getOperation());
                        
                registerState(evaluateBinaryOperationInitiator);
                setInitiatorArgument.registerDefaultTransition(evaluateBinaryOperationInitiator);
                evaluateBinaryOperationInitiator.registerDefaultTransition(getInitiatorResult);
                
                exitValue = BINARY_OPERATION;
            } else {
                // Number
                value = parser.getNumber();
                
                exitValue = NUMBER;
            }
        }

        @Override
        public int onEnd() {
            return exitValue;
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Set initiator argument' (one-shot) state.
     */
    private class SetInitiatorArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            evaluateBinaryOperationInitiator.setOperand1(operand1);
            evaluateBinaryOperationInitiator.setOperand2(operand2);
        }
    
        // </editor-fold>
    }
    
    /**
     * The 'Get initiator result' (one-shot) state.
     */
    private class GetInitiatorResult extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            value = evaluateBinaryOperationInitiator.getResult();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'End' (one-shot) state.
     */
    private class End extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }   
        
        // </editor-fold>
    }
    
    /**
     * Expression parser.
     */
    private class ExpressionParser {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /**
         * The operation.
         */
        private Operation operation;
        
        /**
         * The first operand.
         */
        private String operand1;
        
        /**
         * The second operand.
         */
        private String operand2;
        
        /**
         * The number.
         */
        private int number;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        void parse(String expression) {
            // Initialization
            int additionOperatorPosition = -1;
            int multiplicationOperatorPosition = -1;
            int parentheses = 0;
            
            for (int i = 0; i < expression.length(); i++) {
                switch (expression.charAt(i)) {
                    case '(':
                        parentheses++;
                        break;
                    case ')':
                        parentheses--;
                        break;
                    case '+':
                    case '-':
                        if (parentheses == 0) {
                            additionOperatorPosition = i;
                        }
                        break;
                    case '*':
                    case '/':
                        if (parentheses == 0) {
                            multiplicationOperatorPosition = i;
                        }
                        break;
                }
            }
            
            if (additionOperatorPosition > -1 || multiplicationOperatorPosition > -1) {
                // Binary operation
                int operatorPosition = (additionOperatorPosition > -1) ?
                    additionOperatorPosition :
                    multiplicationOperatorPosition;
                switch (expression.charAt(operatorPosition)) {
                    case '+':
                        operation = Operation.ADDITION;
                        break;
                    case '-':
                        operation = Operation.SUBTRACTION;
                        break;
                    case '*':
                        operation = Operation.MULTIPLICATION;
                        break;
                    case '/':
                        operation = Operation.DIVISION;
                        break;
                }
                operand1 = expression.substring(0, operatorPosition);
                operand2 = expression.substring(operatorPosition + 1);
            } else if (expression.charAt(0) != '(') {
                // Number
                operation = Operation.NONE;
                number = new Integer(expression).intValue();
            } else {
                // Expression in parentheses.
                parse(expression.substring(1, expression.length() - 1));
            }
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        Operation getOperation() {
            return operation;
        }
        
        int getNumber() {
            return number;
        }
        
        String getOperand1() {
            return operand1;
        }
        
        String getOperand2() {
            return operand2;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>  
}